package cellsociety;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Society {
	private Scene scene;
	
	private int rows;
	private int cols;
	private Cell[][] cells;
	
	// TODO: constructor
	public Society(Config config) {
		// TODO: init cells
		cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				cells[i][j] = new RectCell();
			}
		}
		// put cells on the scene
	}
	
	private void updateCells() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				List<Cell> neighbors = null;
				// TODO: find its neighbors!
				cells[i][j].update(neighbors);
			}
		}
	}
	private void syncCells() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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

	public Scene getScene() {
		return scene;
	}
	
	Button button;
	
}
