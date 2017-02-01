package core;

import javafx.scene.Group;

/**
 * A JavaFX {@link Group} of Society, containing a 2d array of {@link Cell}s (Nodes).
 * Responsible for the update and visualization of the cells.
 * @author keping
 *
 */
public class Society extends Group {
	private Cell[][] cells;

	public Society(Cell[][] cells) {
		super();
		this.cells = cells;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				getChildren().add(cells[i][j]);
			}
		}
	}

	/**
	 * Generate array of neighbors surrounding target cell. Indices correspond
	 * to neighbors as follows:
	 * 
	 * <pre>
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 * </pre>
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Cell[] getNeighbors(int x, int y) {
		int rows = cells.length;
		int cols = cells[0].length;
		Cell[] retArray = new Cell[8];
		if (x > 0) {
			retArray[1] = cells[x - 1][y];
			if (y > 0) {
				retArray[0] = cells[x - 1][y - 1];
			}
			if (y < cols - 1) {
				retArray[2] = cells[x - 1][y + 1];
			}
		}
		if (x < rows - 1) {
			retArray[6] = cells[x + 1][y];
			if (y > 0) {
				retArray[5] = cells[x + 1][y - 1];
			}
			if (y < cols - 1) {
				retArray[7] = cells[x + 1][y + 1];
			}
		}
		if (y > 0) {
			retArray[3] = cells[x][y - 1];
		}
		if (y < cols - 1) {
			retArray[4] = cells[x][y + 1];
		}
		return retArray;
	}

	private void updateCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				Cell[] neighbors = getNeighbors(i, j);
				cells[i][j].updateNextState(neighbors);
			}
		}
	}

	private void syncCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].syncState();
			}
		}
	}

	/**
	 * Update the cell states in one round.
	 */
	public void update() {
		updateCells();
		syncCells();
	}

}
