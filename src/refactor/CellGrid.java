package refactor;

import javafx.scene.canvas.GraphicsContext;

public abstract class CellGrid extends Grid<Cell> {

	public CellGrid(Cell[][] arr) {
		super(arr);
		// TODO Auto-generated constructor stub
	}

	public abstract void render(GraphicsContext gc);
	
}
