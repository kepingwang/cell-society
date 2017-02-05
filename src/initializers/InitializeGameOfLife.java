package initializers;

import core.Cell;
import core.Society;
import core.rules.GameOfLifeRule;
import core.rules.Rule;

public class InitializeGameOfLife extends InitializeSociety {
	private int rows;
	private int cols;
	private int[][] states;

	public InitializeGameOfLife(int rowsIn, int colsIn, int[][] statesIn){
		rows = rowsIn;
		cols = colsIn;
		states = statesIn;
	}
	
	@Override
	public Rule createRule() {
		// TODO Auto-generated method stub
		return new GameOfLifeRule();
	}

	@Override
	public Cell[][] generateCells() {
		this.calculateCellSize(rows, cols);
		Cell[][] retArray = new Cell[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < rows; j++){
				retArray[i][j] = new Cell(i*cellWidth, j*cellHeight, cellWidth, cellHeight, states[i][j]);
			}
		}
		return retArray;
	}

	@Override
	public Society getSociety() {
		return new Society(createRule(), generateCells());
	}

}
