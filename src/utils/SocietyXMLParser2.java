package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.Cell;
import core.Society;

public class SocietyXMLParser2 {
	static final String outputEncoding = "UTF-8";

	private String name = null;
	private String id = null;
	private double width = -1;
	private double height = -1;
	private int rows = -1;
	private int cols = -1;
	private int[][] layout = null;
	private List<Double> probs;
	private List<Double> params;
	
	private Map<String, String> societyMap = new HashMap<String, String>();
	private Map<String, int[][]> layoutMap = new HashMap<String, int[][]>();
	
	public SocietyXMLParser2() { }
	
	// Parse XML to Cell[][]
	private List<Integer> getLayoutRow(Node rowNode) {
		List<Integer> rowList = new ArrayList<>();
		NodeList list = rowNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeName().equals("col")) {
				rowList.add(Integer.parseInt(val(node)));
			}
		}
		return rowList;
	}
	private int[][] getLayout(String rows, String cols, Node layoutNode) throws Exception {
		List<List<Integer>> mat = new ArrayList<>();
		NodeList list = layoutNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeName().equals("row")) {
				mat.add(getLayoutRow(node));
			}
		}
		int r = Integer.parseInt(rows);
		int c = Integer.parseInt(cols);
		if (mat.size() != r) {
			throw new Exception("Wrong row number in xml!");
		}
		int[][] ans = new int[r][c];
		for (int i = 0; i < r; i++) {
			if (mat.get(i).size() != c) {
				throw new Exception("Wrong col number in xml!");
			}
			for (int j = 0; j < c; j++) {
				ans[i][j] = mat.get(i).get(j);
			}
		}
		return ans;
	}	
	/**
	 * String value of the node. To be used when the node is 
	 * an ELEMENT_NODE and contains only text.
	 */
	private static String val(Node node) {
		return node.getFirstChild().getNodeValue();
	}
	private int[][] getLayout() {
		if (layout != null) {
			return layout;
		} else {
			return LayoutGenerator.generateRandomLayout(rows, cols, probs);
		}
	}
	private List<String> getDoubleValues(Node node) {
		List<String> res = new ArrayList<>();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node subNode = list.item(i);
			if (subNode.getNodeName().equals("val")) {
				res.add(val(subNode));
			}
		}
		return res;
	}
	/**
	 * Parse a given xml file to {@link Cell}[][]
	 * @param filename
	 * @return the {@link Society}
	 * @throws Exception
	 */
	public Society parse(String filename) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(filename));
		NodeList rootList = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < rootList.getLength(); i++) {
			Node node = rootList.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				String nodeName = node.getNodeName();
				if(!nodeName.equals("layout")){
					if(nodeName.equals("probs") || nodeName.equals("params")){
						List<String> vals = getDoubleValues(node);
						String concatenatedVals = "";
						for(String v: vals){
							concatenatedVals=concatenatedVals + v;
						}
					societyMap.put(nodeName, concatenatedVals);
					}
					else{
						societyMap.put(nodeName, val(node));
					}
				}
				else{
					//for layout
					layoutMap.put(nodeName, getLayout(societyMap.get("rows"), societyMap.get("cols"), node));
				}
				
			}
			/*if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (node.getNodeName().equals("name")) {
					name = val(node);
				} else if (node.getNodeName().equals("id")) {
					id = val(node);
				} else if (node.getNodeName().equals("width")) {
					width = Double.parseDouble(val(node));
				} else if (node.getNodeName().equals("height")) {
					height = Double.parseDouble(val(node));
				} else if (node.getNodeName().equals("rows")) {
					rows = Integer.parseInt(val(node));
				} else if (node.getNodeName().equals("cols")) {
					cols = Integer.parseInt(val(node));
				} else if (node.getNodeName().equals("layout")) {
					layout = getLayout(rows, cols, node);
					probs = null;
				} else if (node.getNodeName().equals("probs")) {
					probs = getDoubleValues(node);
					layout = null;
				} else if (node.getNodeName().equals("params")) {
					params = getDoubleValues(node);
				} else { }
			}*/
			name = societyMap.get("name");
			id = societyMap.get("id");
			width = Double.parseDouble(societyMap.get("width"));
			height = Double.parseDouble(societyMap.get("height"));
			rows = Integer.parseInt(societyMap.get("rows"));
			cols = Integer.parseInt(societyMap.get("cols"));
			
		}
		String paramsConcatenated = societyMap.get("params");
		String probsConcatenated = societyMap.get("probs");
		String[] paramsList = paramsConcatenated.split(",");
		String[] probsList = probsConcatenated.split(",");
		
		for(String s: paramsList){
			params.add(Double.parseDouble(s));
		}
		for(String s: probsList){
			probs.add(Double.parseDouble(s));
		}
		
		return new Society(name, width, height, params, getLayout());
	}
	
	
	// Save Cell[][] to XML
	private void readSociety(Society society) {
		name = society.getGameName();
		width = society.getWidth();
		height = society.getHeight();
		rows = society.getRows();
		cols = society.getCols();
		layout = society.getLayout();
		params = society.getParams();
	}
	private void add(Document doc, Node parent, String elemName, String elemValue) {
		Element elem = doc.createElement(elemName);
		elem.appendChild(doc.createTextNode(elemValue));
		parent.appendChild(elem);
		parent.appendChild(doc.createTextNode("\n"));
	}	
	private Element rowElem(Document doc, int i) {
		Element rowElem = doc.createElement("row");
		for (int j = 0; j < layout[i].length; j++) {
			add(doc, rowElem, "col", Integer.toString(layout[i][j]));
		}
		return rowElem;
	}
	private Element layoutElem(Document doc) {
		Element layoutElem = doc.createElement("layout");
		for (int i = 0; i < layout.length; i++) {
			layoutElem.appendChild(rowElem(doc, i));
		}
		return layoutElem;
	}

	private <T> Element elemList(Document doc, String tag, String childTag, List<T> list) {
		Element listElem = doc.createElement(tag);
		for (Object child : list) {
			add(doc, listElem, childTag, child.toString());
		}
		return listElem;
	}
	/**
	 * Save {@link Cell}[][] to an xml file.
	 * @param cells
	 * @param filename the file to save to.
	 * @param id an identifier of the configuration.
	 * @throws Exception 
	 */
	public void saveAsXML(Society society, String filename, String id) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		readSociety(society);
		
		Document doc = db.newDocument();
		Element root = doc.createElement("config");
		doc.appendChild(root);
		
		add(doc, root, "name", name);
		add(doc, root, "id", id);
		add(doc, root, "width", Double.toString(width));
		add(doc, root, "height", Double.toString(height));
		add(doc, root, "rows", Integer.toString(rows));
		add(doc, root, "cols", Integer.toString(cols));
		root.appendChild(layoutElem(doc));
		root.appendChild(elemList(doc, "params", "val", params));
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filename));
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "cell-society.dtd");
		transformer.transform(source, result);
		System.out.println("Society saved to XML: "+filename);
	}
	
	
	@Override
	public String toString() {
		return "configuration: " + id;
	}
	
	/**
	 * For testing
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		SocietyXMLParser parser = new SocietyXMLParser();
		Society society = parser.parse("data/game_of_life1.xml");
		parser.saveAsXML(society, "data/saved-from-society.xml", "saved-file");
		
	}

	
}
