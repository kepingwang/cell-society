package refactor.society;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import refactor.cell.games.GameOfLifeCell;
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

}
