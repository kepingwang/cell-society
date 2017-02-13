package config;

import grid.GridType;
import grid.NeighborsType;

public class GridConfig {
	private GridType gridType;
	private NeighborsType neighborsType;
	private boolean wrapping;

	public GridConfig(GridType gridType, NeighborsType neighborsType, boolean wrapping) {
		this.gridType = gridType;
		this.neighborsType = neighborsType;
		this.wrapping = wrapping;
	}

	public GridType gridType() {
		return gridType;
	}
	public NeighborsType neighborsType() {
		return neighborsType;
	}
	public boolean isWrapping() {
		return wrapping;
	}

}
