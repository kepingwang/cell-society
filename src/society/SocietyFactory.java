package society;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cell.CellFactory;
import cell.games.FireCell;
import cell.games.ForagingAntCell;
import cell.games.SegregationCell;
import cell.games.SlimeCell;
import cell.games.WatorCell;
import config.GameConfig;
import config.GridConfig;
import config.SizeConfig;
import grid.GridType;
import grid.NeighborsType;
import javafx.scene.paint.Color;
import utils.SocietyXMLParser;

public class SocietyFactory {
	public static final double SIZE = 360;
	private GameConfig gameConfig;
	private Map<String, String> configMap = new HashMap<String, String>();
	private int[][] layout;

	public SocietyFactory() {
	}

	public Society<?> genSociety(String fileName) throws Exception {
		// TODO: parse xml file and gen Society
		SocietyXMLParser parser = new SocietyXMLParser();
		gameConfig = parser.parse(fileName);
		configMap = gameConfig.getConfigMap();
		layout = gameConfig.getLayout();
		return genSociety();
	}

	private <T extends Cell> T[][] genCells(String gameType, String cellShapeType, Color[] colors, List<Double> params,
			int[][] layout) throws Exception {
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
		if (configMap.get("gridType").equals("SQUARE_GRID")) {
			gridType = GridType.SQUARE_GRID;
		} else if (configMap.get("gridType").equals("TRIANGLE_GRID")) {
			gridType = GridType.TRIANGLE_GRID;
		} else if (configMap.get("gridType").equals("HEXAGON_GRID")) {
			gridType = GridType.HEXAGON_GRID;
		} else {
			gridType = GridType.SQUARE_GRID;
			wrongGridType();
		}

		NeighborsType neighborsType;
		if (configMap.get("neighborsType").equals("HEXAGON_6")) {
			neighborsType = NeighborsType.HEXAGON_6;
		} else if (configMap.get("neighborsType").equals("SQUARE_4")) {
			neighborsType = NeighborsType.SQUARE_4;
		} else if (configMap.get("neighborsType").equals("SQUARE_8")) {
			neighborsType = NeighborsType.SQUARE_8;
		} else if (configMap.get("neighborsType").equals("TRIANGLE_12")) {
			neighborsType = NeighborsType.TRIANGLE_12;
		} else {
			neighborsType = NeighborsType.SQUARE_4;
			wrongNeighborsType();
		}

		String cellShapeType = "";
		if (configMap.get("cellShape").equals("SQUARE")) {
			cellShapeType = GridType.SQUARE;
		} else if (configMap.get("cellShape").equals("TRIANGLE")) {
			cellShapeType = GridType.TRIANGLE;
		} else if (configMap.get("cellShape").equals("HEXAGON")) {
			cellShapeType = GridType.HEXAGON;
		} else {
			wrongCellShape();
		}

		boolean wrapping;
		if (configMap.get("wrapping").equals("true") || configMap.get("wrapping").equals("false")) {
			wrapping = Boolean.parseBoolean(configMap.get("wrapping"));
		} else {
			wrapping = false;
			wrongWrapping();
		}
		GridConfig gridConfig = new GridConfig(gridType, neighborsType, wrapping);
		SizeConfig sizeConfig = new SizeConfig(Integer.parseInt(configMap.get("rows")),
				Integer.parseInt(configMap.get("cols")), Double.parseDouble(configMap.get("width")),
				Double.parseDouble(configMap.get("height")));

		Color[] colors = getColorArray(configMap.get("colors"));
		List<Double> params = makeDoubleList(configMap.get("params"));
		T[][] cells = genCells(configMap.get("name"), cellShapeType, colors, params, layout);
		return new Society<T>(gridConfig, sizeConfig, cells);
	}

	private List<Double> makeDoubleList(String concat) {
		List<Double> res = new ArrayList<Double>();
		String[] pList = concat.trim().split(",");
		for (String s : pList) {
			if (!s.equals("")) {
				res.add(Double.parseDouble(s));
			}
		}
		return res;
	}

	public Society<FireCell> sampleFire() {
		boolean wrapping = false;
		double chanceOfFire = 2.5;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_4, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 2, 1, 1, 1 }, { 1, 1, 2, 1, 1 }, { 1, 1, 1, 2, 1 },
				{ 1, 1, 1, 1, 1 } };

		Color[] colors = new Color[] { Color.GOLD, Color.GREEN, Color.RED };
		FireCell[][] cells = new FireCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		params.add(chanceOfFire);
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = new FireCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		return new Society<FireCell>(gridConfig, sizeConfig, cells);
	}

	public Society<ForagingAntCell> sampleForagingAnt() {
		boolean wrapping = false;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 2 } };

		Color[] colors = new Color[] { Color.WHITE, Color.BROWN, Color.GOLD, Color.RED };
		ForagingAntCell[][] cells = new ForagingAntCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = new ForagingAntCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		return new Society<ForagingAntCell>(gridConfig, sizeConfig, cells);
	}

	public Society<SegregationCell> sampleSegregation() {
		boolean wrapping = false;
		double satisfaction = .5;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 1, 2, 2, 2, 1 }, { 0, 0, 0, 0, 0 }, { 2, 1, 1, 1, 2 }, { 0, 0, 0, 0, 0 },
				{ 1, 2, 2, 2, 1 } };

		Color[] colors = new Color[] { Color.WHITE, Color.RED, Color.BLUE };
		SegregationCell[][] cells = new SegregationCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		params.add(satisfaction);
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = new SegregationCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		return new Society<SegregationCell>(gridConfig, sizeConfig, cells);
	}

	public Society<WatorCell> sampleWator() {
		boolean wrapping = true;
		double fishBirth = 4;
		double sharkBirth = 6;
		double sharkDeath = -4;
		double eatEnergy = 2;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_4, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1 } };

		Color[] colors = new Color[] { Color.BLUE, Color.GREEN, Color.GOLD };
		WatorCell[][] cells = new WatorCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		params.add(fishBirth);
		params.add(sharkBirth);
		params.add(sharkDeath);
		params.add(eatEnergy);
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = new WatorCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		return new Society<WatorCell>(gridConfig, sizeConfig, cells);
	}

	public Society<SlimeCell> sampleSlime() {
		boolean wrapping = false;
		double wiggleBias = 0;
		double wiggleAngle = 40;
		double sniffThreshold = 1.0;
		double sniffAngle = 45;
		double cAMPStrength = 2;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1 } };

		Color[] colors = new Color[] { Color.BLACK, Color.RED, Color.GREENYELLOW };
		SlimeCell[][] cells = new SlimeCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		params.add(wiggleBias);
		params.add(wiggleAngle);
		params.add(sniffThreshold);
		params.add(sniffAngle);
		params.add(cAMPStrength);
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				cells[i][j] = new SlimeCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		return new Society<SlimeCell>(gridConfig, sizeConfig, cells);
	}

	private Color[] getColorArray(String concat) throws Exception {
		String[] cList = concat.trim().split(",");
		Color[] res = new Color[cList.length];
		for (int i = 0; i < cList.length; i++) {
			if (!cList[i].equals("")) {
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
		if (s.length() != 7) {
			wrongColor();
		}
		if (s.charAt(0) != '#') {
			wrongColor();
		}
		int r = Integer.parseInt(s.substring(1, 3), 16);
		int g = Integer.parseInt(s.substring(3, 5), 16);
		int b = Integer.parseInt(s.substring(5, 7), 16);
		if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
			wrongColor();
		}
		return new Color(r / 255.0, g / 255.0, b / 255.0, 1);
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
