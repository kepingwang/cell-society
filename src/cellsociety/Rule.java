package cellsociety;

import java.util.List;

public interface Rule {

	/**
	 * Return the new state.
	 * @return
	 */
	public int update(Cell cell, Cell[] neighbors);
	
}
