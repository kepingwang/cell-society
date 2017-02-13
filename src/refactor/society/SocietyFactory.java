package refactor.society;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import refactor.cell.games.FireCell;
import refactor.cell.games.ForagingAntCell;
import refactor.cell.games.GameOfLifeCell;
import refactor.cell.games.SegregationCell;
import refactor.cell.games.SugarScapeCell;
import refactor.cell.games.WatorCell;
import refactor.config.GridConfig;
import refactor.config.SizeConfig;
import refactor.grid.GridType;
import refactor.grid.NeighborsType;

public class SocietyFactory {
	public static final double SIZE = 360;

	public SocietyFactory() { }

	public Society<?> genSociety(String fileName) throws Exception {
		// TODO: parse xml file and gen Society
		if (fileName.length() < 4 || !fileName.substring(fileName.length()-4).equals(".xml")) {
			throw new Exception("Cannot parse xml!");
		} 		
		return sampleSugarScape();
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

	public Society<FireCell> sampleFire() {
		boolean wrapping = false;
		double chanceOfFire = .5;

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
				
		Color[] colors = new Color[] { Color.WHITE, Color.RED, Color.BLUE};
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
				
		Color[] colors = new Color[] { Color.BLUE, Color.GREEN, Color.GOLD};
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
	
	public Society<SugarScapeCell> sampleSugarScape() {
		boolean wrapping = false;
		double sugarGrow = 1;
		double vision = 4;
		double sugarMetabolism = 4;
		double initSugar = 20;

		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_4, wrapping);
		String cellShapeType = GridType.SQUARE;
		SizeConfig sizeConfig = new SizeConfig(5, 5, SIZE, SIZE);
		int[][] layout = new int[][] { { 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2 }, { 3, 3, 3, 3, 3 }, { 4, 4, 4, 4, 4},
				{ 0, 0, 0, 1, 1 } };
				
		Color[] colors = new Color[] { Color.WHITE, Color.LIGHTGREY, Color.LIGHTBLUE, Color.BLUE, Color.PURPLE, Color.RED};
		SugarScapeCell[][] cells = new SugarScapeCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		params.add(sugarGrow);
		params.add(vision);
		params.add(sugarMetabolism);
		params.add(initSugar);
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				double temp = new Random().nextDouble();
				if(temp < .1){
					temp = 1;
				}
				else { temp = 0;}
				params.add(temp);
				cells[i][j] = new SugarScapeCell(cellShapeType, colors, params, layout[i][j]);
				params.remove(4);
			}
		}
		return new Society<SugarScapeCell>(gridConfig, sizeConfig, cells);
	}
}
