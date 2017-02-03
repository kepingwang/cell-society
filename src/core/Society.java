package core;

import core.rules.Rule;
import core.rules.RuleGenerator;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * A JavaFX {@link Group} of Society, containing a 2d array of {@link Cell}s (Nodes).
 * Responsible for the update and visualization of the cells.
 * @author keping, Gordon
 *
 */
public class Society extends Group {
	private String gameName;
	private Rule rule;
	/**
	 * colors array contain the hex color string of the corresponding state.
	 */
	private Color[] colors;
	private Cell[][] cells;

	public Society(String gameName, Color[] colors, Cell[][] cells) {
		super();
		this.gameName = gameName;
		this.rule = RuleGenerator.genRule(this.gameName);
		this.colors = colors;
		this.cells = cells;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				getChildren().add(cells[i][j]);
			}
		}
		syncColors();
	}

	public String getGameName() {
		return gameName; 
	}
	
	/**
	 * Get a deep copy of the colors array.
	 * @return
	 */
	public Color[] getColors() {
		Color[] res = new Color[colors.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = colors[i];
		}
		return res;
	}
	
	public double getWidth() {
		return cells[0][0].getWidth() * cells[0].length;
	}
	public double getHeight() {
		return cells[0][0].getHeight() * cells.length;
	}
	public int getRows() { 
		return cells.length;
	}
	public int getCols() {
		return cells[0].length;
	}
	public int[][] getLayout() {
		int[][] res = new int[getRows()][getCols()];
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				res[i][j] = cells[i][j].getState();
			}
		}
		return res;
	}
	
	/**
	 * Generate array of neighbors surrounding target cell. Indices correspond
	 * to neighbors as follows:
	 * <pre>
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 * </pre>
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
	private void updateNextStates() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				Cell[] neighbors = getNeighbors(i, j);
				cells[i][j].updateNextState(rule, neighbors);
			}
		}
	}
	private void syncStates() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].syncState();
			}
		}
	}
	private void syncColors() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].syncColor(colors);
			}
		}		
	}
	/**
	 * Update the cell states in one round.
	 */
	public void update() {
		updateNextStates();
		syncStates();
		syncColors();
	}

}
