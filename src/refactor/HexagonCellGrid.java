package refactor;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class HexagonCellGrid extends CellGrid {

	public HexagonCellGrid(Cell[][] arr) {
		super(arr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Cell> getNeighborsWrapping(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Cell> getNeighborsNoWrapping(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

}
