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
		
		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, Boolean.parseBoolean(configMap.get("wrapping")));
		String cellShapeType = GridType.SQUARE;
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
}
