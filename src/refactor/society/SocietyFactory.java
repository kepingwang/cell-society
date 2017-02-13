package refactor.society;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import refactor.cell.Cell;
import refactor.cell.CellFactory;


import refactor.config.GameConfig;
import refactor.config.GridConfig;
import refactor.config.SizeConfig;
import refactor.grid.GridType;
import refactor.grid.NeighborsType;
import utils.SocietyXMLParser;

public class SocietyFactory {
	public static final double SIZE = 360;
	private GameConfig gameConfig;
	private Map<String, String> configMap = new HashMap<String, String>();
	private int[][] layout;

	public SocietyFactory() { }

	public Society<?> genSociety(String fileName) throws Exception {
		// TODO: parse xml file and gen Society
		SocietyXMLParser parser = new SocietyXMLParser();
		gameConfig = parser.parse(fileName);
		configMap = gameConfig.getConfigMap();
		layout = gameConfig.getLayout();
		return genSociety();

		/*if (fileName.length() < 4 || !fileName.substring(fileName.length()-4).equals(".xml")) {
			throw new Exception("Cannot parse xml!");
		} 	*/	
	}
	
	private <T extends Cell> T[][] genCells(String gameType, 
			String cellShapeType, Color[] colors, 
			List<Double> params, int[][] layout) {
		@SuppressWarnings("unchecked")
		T[][] cells = (T[][]) new Cell[layout.length][layout[0].length];
		CellFactory cellFactory = new CellFactory();
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = cellFactory.genCell(gameType, cellShapeType, colors, params, layout[i][j]);
			}
		}
		return cells;
	}
	
	public <T extends Cell> Society<T> genSociety() throws Exception {
		GridType gridType;
		if(configMap.get("gridType").equals("SQUARE_GRID")){
			gridType = GridType.SQUARE_GRID;
		}
		else if(configMap.get("gridType").equals("TRIANGLE_GRID")){
			gridType = GridType.TRIANGLE_GRID;
		}
		else if(configMap.get("gridType").equals("HEXAGON_GRID")){
			gridType = GridType.HEXAGON_GRID;
		}
		else{
			gridType=GridType.SQUARE_GRID;
			wrongGridType();
		}
		
		NeighborsType neighborsType;
		if(configMap.get("neighborsType").equals("HEXAGON_6")){
			neighborsType = NeighborsType.HEXAGON_6;
		}
		else if(configMap.get("neighborsType").equals("SQUARE_4")){
			neighborsType = NeighborsType.SQUARE_4;
		}
		else if(configMap.get("neighborsType").equals("SQUARE_8")){
			neighborsType = NeighborsType.SQUARE_8;
		}
		else if(configMap.get("neighborsType").equals("TRIANGLE_12")){
			neighborsType = NeighborsType.TRIANGLE_12;
		}
		else{
			neighborsType = NeighborsType.SQUARE_4;
			wrongNeighborsType();
		}
		
		String cellShapeType="";
		if(configMap.get("cellShape").equals("SQUARE")){
			cellShapeType = GridType.SQUARE;
		}
		else if(configMap.get("cellShape").equals("TRIANGLE")){
			cellShapeType = GridType.TRIANGLE;
		}
		else if(configMap.get("cellShape").equals("HEXAGON")){
			cellShapeType = GridType.HEXAGON;
		}
		else{
			wrongCellShape();
		}
		
		boolean wrapping;
		if(configMap.get("wrapping").equals("true") || configMap.get("wrapping").equals("false")){
			wrapping = Boolean.parseBoolean(configMap.get("wrapping"));
		}
		else{
			wrapping=false;
			wrongWrapping();
		}
		GridConfig gridConfig = new GridConfig(gridType, neighborsType, wrapping);
		SizeConfig sizeConfig = new SizeConfig(Integer.parseInt(configMap.get("rows")), Integer.parseInt(configMap.get("cols")), 
				Double.parseDouble(configMap.get("width")), Double.parseDouble(configMap.get("height")));
		
		Color[] colors = getColorArray(configMap.get("colors"));
		List<Double> params = makeDoubleList(configMap.get("params"));
		T[][] cells = genCells(configMap.get("name"), cellShapeType, colors, params, layout);
		return new Society<T>(gridConfig, sizeConfig, cells);
	}
	
	private List<Double> makeDoubleList(String concat){
		List<Double> res = new ArrayList<Double>();
		String[] pList = concat.trim().split(",");
		for(String s: pList){
			if(!s.equals("")){
				res.add(Double.parseDouble(s));
			}
		}
		return res;
	}
	
	private Color[] getColorArray(String concat) throws Exception{
		String[] cList = concat.trim().split(",");
		Color[] res = new Color[cList.length];
		for(int i=0; i<cList.length; i++){
			if(!cList[i].equals("")){
				res[i] = strToColor(cList[i].trim());
			}
		}
		return res;
	}
	
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
		

		private void wrongCellShape() throws Exception {
			// check for wrong cell shape
				throw new Exception("Incorrect cell shape provided.");
		}

		private void wrongGridType() throws Exception {
			// check for wrong grid type
				throw new Exception("Incorrect grid type provided.");
		}

		private void wrongNeighborsType() throws Exception {
			// check for wrong neighbors type
				throw new Exception("Incorrect neighbors type provided.");
		}

		private void wrongWrapping() throws Exception {
			// check for wrong wrapping
				throw new Exception("Wrapping must be either 'true' or 'false'.");
		}
}
