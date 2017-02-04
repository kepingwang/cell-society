package core.rules;

import java.util.List;

import core.Cell;

public interface Rule {
		
	/**
	 * Return the nextState of the cell.
	 * The neighbors array passed in shall always contain 8 elements.
	 * With "*" representing the current cell, the indices of its neighbors
	 * should be like:
	 * <pre>
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 * </pre>
	 * @param cell
	 * @param neighbors
	 * @return nextState
	 */
	public int update(Cell cell, List<Cell> neighbors);
	
}
