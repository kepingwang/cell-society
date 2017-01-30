package cellsociety;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Society {
	private final int FRAMES_PER_SECOND = 60;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	
	private Scene scene;
	private Group root;
	private Timeline timeline;
	
	private int rows;
	private int cols;
	private Cell[][] cells;
	
	// TODO: constructor
	public Society(Config config) {
		// set up scene
		setUpScene();
		// set up HUD
		setUpHUD();
		// initialize cells
		cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// TODO: initialize cell state
				cells[i][j] = new RectCell();
				root.getChildren().add(cells[i][j]);
			}
		}
	}
	
	/**
	 * Method to begin simulation
	 * Must be called in order to start a timeline
	 */
	public void startSimulation(){
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update());
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}
	
	private void updateCells() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell[] neighbors = getNeighbors(i,j);
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
	
	/**
	 * Create the scene for simulator
	 */
	// TODO: scene setup
	private void setUpScene(){
		
	}
	
	/**
	 * Create the HUD for the simulator
	 */
	// TODO: HUD setup
	private void setUpHUD(){
		
	}
	
	/**
	 * Generate list of neighbors surrounding target cell
	 * List indices correlate to neighbors as follows:
	 * 0 1 2
	 * 3 X 4
	 * 5 6 7
	 */
	private Cell[] getNeighbors(int x, int y){
		Cell[] retArray = new Cell[8];
		if(x > 0){
			retArray[1] = cells[x-1][y];
			if(y > 0){
				retArray[0] = cells[x-1][y-1];
			}
			if(y < cols - 1){
				retArray[2] = cells[x-1][y+1];
			}
		}
		if(x < rows - 1){
			retArray[6] = cells[x+1][y];
			if(y > 0){
				retArray[5] = cells[x+1][y-1];
			}
			if(y < cols - 1){
				retArray[7] = cells[x+1][y+1];
			}
		}
		if(y > 0){
			retArray[3] = cells[x][y-1];
		}
		if(y < cols - 1){
			retArray[4] = cells[x][y+1];
		}
		return retArray;
	}
	
}
