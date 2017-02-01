package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Cell;
import core.rules.RuleGenerator;
import javafx.scene.paint.Color;

/**
 * An XML Parser that (1) parse an XML config file and to a 2d array
 * of {@link Cell}s, or (2) save the config of a 2d array of 
 * {@link Cell}s as an XML file.
 * @author keping
 *
 */
public class CellsParser {
	static final String outputEncoding = "UTF-8";
	private String name = null;
	private String id = null; // TODO: unique name for config
	private Color[] colors = null;
	private double width = -1;
	private double height = -1;
	private int rows = -1;
	private int cols = -1;
	private int[][] layout = null;
	
	public CellsParser() { }
	
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
				cells[i][j] = new Cell(name, colors, w*j, h*i, w, h, layout[i][j]);
			}
		}
		return cells;
	}
	private void checkGameName(String name) throws Exception {
		if (RuleGenerator.genRule(name) == null) {
			throw new Exception("I don't know this game name :P");
		}
	}
	/**
	 * Parse a given xml file to {@link Cell}[][]
	 * @param filename
	 * @return 2d array of {@link Cell}s
	 * @throws Exception
	 */
	public Cell[][] parse(String filename) throws Exception {
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
				} else { }
			}
		}
		return createCells();
	}
	
	
	// Save Cell[][] to XML
	/**
	 * Save {@link Cell}[][] to an xml file.
	 * @param cells
	 * @param filename the file to save to.
	 * @param id an identifier of the configuration.
	 */
	public void saveAsXML(Cell[][] cells, String filename, String id) {
		// TODO;
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
		CellsParser parser = new CellsParser();
		Cell[][] cells = parser.parse("data/game_of_life1.xml");
		System.out.println(cells[2][2]);
	}

	
}
