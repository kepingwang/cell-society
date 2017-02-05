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
	private Rule rule;
	private Grid<Cell> cells;

	public Society(Rule ruleIn, Cell[][] cellsArr) {
		super();
		rule = ruleIn;
		this.cells = new Grid<Cell>(cellsArr);
		for (int i = 0; i < cells.rows(); i++) {
			for (int j = 0; j < cells.cols(); j++) {
				getChildren().add(cells.get(i, j));
			}
		}
		syncColors();
	}
	
	public double getWidth() {
		return cells.get(0,0).getWidth() * cells.cols();
	}
	public double getHeight() {
		return cells.get(0,0).getHeight() * cells.rows();
	}
	public int getRows() { 
		return cells.rows();
	}
	public int getCols() {
		return cells.cols();
	}
	public int[][] getLayout() {
		int[][] res = new int[getRows()][getCols()];
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				res[i][j] = cells.get(i, j).getState();
			}
		}
		return res;
	}

	private void updateNextStates() {
		for (int i = 0; i < cells.rows(); i++) {
			for (int j = 0; j < cells.cols(); j++) {
				cells.get(i, j).updateNextState(rule, cells.getNeighbors(i, j));
			}
		}
	}
	private void syncStates() {
		for (Cell cell : cells) { cell.syncState(); }
	}
	private void syncColors() {
		for (Cell cell : cells) { cell.syncColor(rule.getColor()); }
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
