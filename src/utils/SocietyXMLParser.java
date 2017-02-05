package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Cell;
import core.Society;
import core.rules.RuleGenerator;
import javafx.scene.paint.Color;

/**
 * An XML Parser that (1) parse an XML config file and to a 2d array
 * of {@link Cell}s, or (2) save the config of a 2d array of 
 * {@link Cell}s as an XML file.
 * @author keping
 *
 */
public class SocietyXMLParser {
	static final String outputEncoding = "UTF-8";

	private String name = null;
	private String id = null;
	private Color[] colors = null;
	private double width = -1;
	private double height = -1;
	private int rows = -1;
	private int cols = -1;
	private int[][] layout = null;
	private List<Double> probs;
	private List<Double> params;
	//ADDITIONS
	private int pctprimary=-1;
	private int pctempty=-1;
	//ADDITIONS
	
	public SocietyXMLParser() { }
	
	// Parse XML to Cell[][]
	private void wrongColor() throws Exception {
		throw new Exception("The color hex string should be like #0f0f0f");
	}
	private Color strToColor(String s) throws Exception {
		if (s.length() != 7) { wrongColor(); }
		if (s.charAt(0) != '#') { wrongColor(); }
		int r = Integer.parseInt(s.substring(1, 3), 16);
		int g = Integer.parseInt(s.substring(3, 5), 16);
		int b = Integer.parseInt(s.substring(5, 7), 16);
		if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
			wrongColor();
		}
		return new Color(r/255.0, g/255.0, b/255.0, 1);
	}
	private Color[] getColors(Node colorNode) throws Exception {
		List<String> colorList = new ArrayList<>();
		NodeList list = colorNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeName().equals("val")) {
				colorList.add(val(node));
			}
		}
		Color[] colorArr = new Color[colorList.size()];
		for (int i = 0; i < colorArr.length; i++) {
			colorArr[i] = strToColor(colorList.get(i));
		}
		return colorArr;
	}
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
	private int[][] getLayout(int rows, int cols, Node layoutNode) throws Exception {
		List<List<Integer>> mat = new ArrayList<>();
		NodeList list = layoutNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeName().equals("row")) {
				mat.add(getLayoutRow(node));
			}
		}
		if (mat.size() != rows) {
			throw new Exception("Wrong row number in xml!");
		}
		int[][] ans = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			if (mat.get(i).size() != cols) {
				throw new Exception("Wrong col number in xml!");
			}
			for (int j = 0; j < cols; j++) {
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
	private Cell[][] createCells() {
		double w = width / cols;
		double h = height / rows;
		Cell[][] cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				cells[i][j] = new Cell(w*j, h*i, w, h, layout[i][j]);
			}
		}
		return cells;
	}
	private void checkGameName(String name) throws Exception {
		if (RuleGenerator.genRule(name) == null) {
			throw new Exception("I don't know this game name :P");
		}
	}
	private List<Double> getDoubleValues(Node node) {
		List<Double> res = new ArrayList<>();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node subNode = list.item(i);
			if (subNode.getNodeName().equals("val")) {
				res.add(Double.parseDouble(val(subNode)));
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
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (node.getNodeName().equals("name")) {
					name = val(node);
					checkGameName(name);
				} else if (node.getNodeName().equals("id")) {
					id = val(node);
				} else if (node.getNodeName().equals("color")) {
					colors = getColors(node);
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
			}
		}
		return new Society(name, colors, createCells());
	}
	
	
	// Save Cell[][] to XML
	private String colorToString(Color color) {
		return "#" + color.toString().substring(2, 8);
	}
	private void readSociety(Society society) {
		name = society.getGameName();
		colors = society.getColors();
		width = society.getWidth();
		height = society.getHeight();
		rows = society.getRows();
		cols = society.getCols();
		layout = society.getLayout();
	}
	private void add(Document doc, Node parent, String elemName, String elemValue) {
		Element elem = doc.createElement(elemName);
		elem.appendChild(doc.createTextNode(elemValue));
		parent.appendChild(elem);
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
		Element colorElem = doc.createElement("color");
		for (Color color : colors) {
			add(doc, colorElem, "val", colorToString(color));
		}
		root.appendChild(colorElem);
		add(doc, root, "width", Double.toString(width));
		add(doc, root, "height", Double.toString(height));
		add(doc, root, "rows", Integer.toString(rows));
		add(doc, root, "cols", Integer.toString(cols));
		//Additions
		add(doc, root, "pctprimary", Integer.toString(pctprimary));
		add(doc, root, "pctempty", Integer.toString(pctempty));
		//Additions
		root.appendChild(layoutElem(doc));
		
		// TODO: save params
		
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
