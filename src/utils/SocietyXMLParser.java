package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import refactor.config.GameConfig;

public class SocietyXMLParser {
	static final String outputEncoding = "UTF-8";

	private Map<String, String> configMap = new HashMap<String, String>();
	private int[][] layout;

	public SocietyXMLParser() {
	}

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
	 * String value of the node. To be used when the node is an ELEMENT_NODE and
	 * contains only text.
	 */
	private static String val(Node node) {
		return node.getFirstChild().getNodeValue();
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

	private String getConcatenatedVals(Node node) {
		List<String> vals = getDoubleValues(node);
		String concatenatedVals = "";
		for (String v : vals) {
			concatenatedVals = concatenatedVals + v +",";
		}
		return concatenatedVals;
	}

	private List<Double> makeDoubleList(String concat) {
		List<Double> res = new ArrayList<Double>();
		String[] pList = concat.trim().split(",");
		System.out.println(concat);
		for (String s : pList) {
			if (!s.equals("")) {
				res.add(Double.parseDouble(s));
			}
		}
		return res;
	}

	/**
	 * Parse a given xml file to {@link Cell}[][]
	 * 
	 * @param filename
	 * @return the {@link Society}
	 * @throws Exception
	 */
	public GameConfig parse(String filename) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputStream is = new FileInputStream(new File(filename));
		Document doc = db.parse(is);
		NodeList rootList = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < rootList.getLength(); i++) {
			Node node = rootList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = node.getNodeName();
				if (!nodeName.equals("layout")) {
					if (nodeName.equals("probs") || nodeName.equals("params") || nodeName.equals("colors")) {
						configMap.put(nodeName, getConcatenatedVals(node));
					} else {
						configMap.put(nodeName, val(node));
					}
				} else {
					layout = getLayout(configMap.get("rows"), configMap.get("cols"), node);
				}

			}

		}

		handleExceptions();
		if (configMap.containsKey("probs")) {
			layout = LayoutGenerator.generateRandomLayout(Integer.parseInt(configMap.get("rows")),
					Integer.parseInt(configMap.get("cols")), makeDoubleList(configMap.get("probs")));
		}
		return new GameConfig(configMap, layout);
	}

	// Save Cell[][] to XML
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
	 * 
	 * @param cells
	 * @param filename
	 *            the file to save to.
	 * @param id
	 *            an identifier of the configuration.
	 * @throws Exception
	 */
	public void saveAsXML(Society society, String filename, String id) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.newDocument();
		Element root = doc.createElement("config");
		doc.appendChild(root);

		add(doc, root, "name", configMap.get("name"));
		add(doc, root, "cellShape", configMap.get("cellShape"));
		add(doc, root, "gridType", configMap.get("gridType"));
		add(doc, root, "neighborsType", configMap.get("neighborsType"));
		add(doc, root, "wrapping", configMap.get("wrapping"));
		add(doc, root, "colors", configMap.get("colors"));
		add(doc, root, "width", configMap.get("width"));
		add(doc, root, "height", configMap.get("height"));
		add(doc, root, "rows", configMap.get("rows"));
		add(doc, root, "cols", configMap.get("cols"));
		root.appendChild(layoutElem(doc));
		root.appendChild(elemList(doc, "params", "val", makeDoubleList(configMap.get("params"))));

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filename));
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "cell-society.dtd");
		transformer.transform(source, result);
		System.out.println("Society saved to XML: " + filename);
	}

	@Override
	public String toString() {
		return "configuration: " + configMap.get("id");
	}

	private void wrongCellShape() throws Exception {
		// check for wrong cell shape
		String cellShape = configMap.get("cellShape");
		if (!(cellShape.equals("SQUARE") || cellShape.equals("HEXAGON") || cellShape.equals("TRIANGLE"))) {
			throw new Exception("Incorrect cell shape provided.");
		}
	}

	private void wrongGridType() throws Exception {
		// check for wrong grid type
		String gridType = configMap.get("gridType");
		if (!(gridType.equals("SQUARE_GRID") || gridType.equals("HEXAGON_GRID") || gridType.equals("TRIANGLE_GRID"))) {
			throw new Exception("Incorrect grid type provided.");
		}
	}

	private void wrongNeighborsType() throws Exception {
		// check for wrong neighbors type
		String neighborsType = configMap.get("neighborsType");
		if (!(neighborsType.equals("SQUARE_4") || neighborsType.equals("SQUARE_8") || neighborsType.equals("HEXAGON_6")
				|| neighborsType.equals("TRIANGLE_12"))) {
			throw new Exception("Incorrect neighbors type provided.");
		}
	}

	private void wrongWrapping() throws Exception {
		// check for wrong wrapping
		String wrapping = configMap.get("wrapping");
		if (!(wrapping.equals("true") || wrapping.equals("false"))) {
			throw new Exception("Wrapping must be either 'true' or 'false'.");
		}
	}

	private void handleExceptions() throws Exception {
		wrongCellShape();
		wrongGridType();
		wrongNeighborsType();
		wrongWrapping();
	}
}
