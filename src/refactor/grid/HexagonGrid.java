package refactor.grid;

import java.util.List;

import refactor.cell.Cell;
import refactor.cell.CellConfig;

public class HexagonGrid<C extends Cell, T extends CellConfig> extends Grid<C> {

	public HexagonGrid(T[][] arr) {
		C haha = new Cell();
	}

	public List<C> getNeighbors(int i, int j, HexagonNeighbors neighborsType, boolean wrapping) {
		// TODO
		return null;
	}
	
}
