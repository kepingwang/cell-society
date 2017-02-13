package refactor.society;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Cell;
import core.WaTorCell;
import javafx.scene.paint.Color;
import refactor.cell.games.FireCell;
import refactor.cell.games.GameOfLifeCell;
import refactor.cell.games.SegregationCell;
import refactor.cell.games.SimpleCell;
import refactor.config.GameConfig;
import refactor.config.GridConfig;
import refactor.config.SizeConfig;
import refactor.grid.GridType;
import refactor.grid.NeighborsType;
import utils.SocietyXMLParser2;

public class SocietyFactory {
	public static final double SIZE = 360;
	private GameConfig gameConfig;
	private Map<String, String> configMap = new HashMap<String, String>();
	private int[][] layout;

	public SocietyFactory() {
	}

	public Society<?> genSociety(String fileName) throws Exception {
		// TODO: parse xml file and gen Society
		SocietyXMLParser2 parser = new SocietyXMLParser2();
		gameConfig = parser.parse(fileName);
		configMap = gameConfig.getConfigMap();
		layout = gameConfig.getLayout();
		return sampleGameOfLifeSociety();
	}
	
	public Society<GameOfLifeCell> sampleGameOfLifeSociety() {
		boolean wrapping = false;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 0, 1, 0, 0, 1 }, { 1, 1, 0, 1, 1 }, { 1, 0, 1, 0, 0 }, { 1, 1, 0, 0, 1 },
				{ 0, 0, 1, 1, 1 } };
				
		Color[] colors = new Color[] { Color.ALICEBLUE, Color.BLACK };
		GameOfLifeCell[][] cells = new GameOfLifeCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = new GameOfLifeCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		return new Society<GameOfLifeCell>(gridConfig, sizeConfig, cells);
	}
	
	public Society<FireCell> fireSociety(String fileName){
		boolean wrapping = false;
		
		//read for gridConfig
		
		
		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		String cellShapeType = GridType.SQUARE; //read for cell shape
		SizeConfig sizeConfig = new SizeConfig(Integer.parseInt(configMap.get("rows")), Integer.parseInt(configMap.get("cols")), Double.parseDouble(configMap.get("width")), Double.parseDouble(configMap.get("height")));
		
		
		Color[] colors = new Color[] {Color.BROWN, Color.GREEN, Color.RED};
		FireCell[][] cells = new FireCell[layout.length][layout[0].length];
		
		cells = placeCells(cellShapeType, colors, makeDoubleList(configMap.get("params")), layout);
		
		return new Society<FireCell>(gridConfig, sizeConfig, cells);
	}

	public Society<GameOfLifeCell> gameOfLifeSociety(String fileName){
		boolean wrapping = false;
		
		Color[] colors = new Color[] {Color.ALICEBLUE, Color.BLACK};
		FireCell[][] cells = new FireCell[layout.length][layout[0].length];
		
		//for loop method
		
		return new Society<GameOfLifeCell>(gridConfig, sizeConfig, cells);
	}
	
	public Society<SegregationCell> segregationSociety(String fileName){
		boolean wrapping = false;
		
		Color[] colors = new Color[] {Color.ALICEBLUE, Color.BLACK};
		FireCell[][] cells = new FireCell[layout.length][layout[0].length];

		//for loop method
		
		return new Society<SegregationCell>(gridConfig, sizeConfig, cells);
	}
	
	public Society<WaTorCell> waTorSociety(String fileName){
		boolean wrapping = false;
		
		Color[] colors = new Color[] {Color.ALICEBLUE, Color.BLACK};
		WaTorCell[][] cells = new WaTorCell[layout.length][layout[0].length];
		
		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		
		cells = placeCells(cellShapeType, colors, makeDoubleList(configMap.get("params")), layout);
		
		return new Society<WaTorCell>(gridConfig, sizeConfig, cells);
	}
	
	private SimpleCell[][] placeCells(String cellShapeType, Color[] colors, List<Double> params, int[][] layout){
		List<Double> parameters = makeDoubleList(configMap.get("params"));
		SimpleCell[][] cells = new SimpleCell[layout.length][layout[0].length];
		for(int i = 0; i < layout.length; i++){
			for(int j = 0; j < layout[0].length; j++){
				cells[i][j] = new SimpleCell(cellShapeType, colors, parameters, layout[i][j]);
			}
		}
		return cells;
	}
	
	private List<Double> makeDoubleList(String concat){
		List<Double> res = null;
		String[] pList = concat.split(",");
		for(String s: pList){
			res.add(Double.parseDouble(s));
		}
		return res;
	}
}
