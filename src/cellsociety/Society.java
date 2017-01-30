package cellsociety;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class Society {
	private final int FRAMES_PER_SECOND = 60;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private SceneController controller;
	private SettingsScreen settings;
	private Scene scene;
	private Group root;
	private Timeline timeline;
	private Rule rule;

	private Button buttonMain;
	private Button buttonSettings;
	private Button buttonPlay;

	private int rows;
	private int cols;
	private Cell[][] cells;
	private int[][] states;

	public Society(SceneController controller, SettingsScreen settings) {
		this.controller = controller;
		this.settings = settings;
		setUpScene();
		setUpHUD();
		setUpCells();
		initEventHandlers();
	}

	private void initEventHandlers() {
		buttonMain.setOnMouseClicked(e -> controller.backToMain());
		buttonSettings.setOnMouseClicked(e -> settings.show());
		buttonPlay.setOnMouseClicked(e -> play());
	}

	public void setUpCells() {
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
	
	public void setUpCells(int[][] states){
		cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// TODO: initialize cell state
				cells[i][j] = new Cell(i*50, j*50, 50, 50, states[i][j], rule);
				root.getChildren().add(cells[i][j]);
			}
		}
	}
	
	/**
	 * testing constructor
	 * @param statesIn
	 */
	public void Society(int[][] statesIn){
		states = statesIn;
	}
	
	/**
	 * testing method to update states due to neighbors
	 */
	public void updateStates(){
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int[] neighbors = getStateNeighbors(i, j);
				updateState(i,j,neighbors);
			}
		}
	}
	
	/**
	 * testing method to get current states
	 * @return
	 */
	public int[][] getStates(){
		return states;
	}
	
	private int[] getStateNeighbors(int x, int y){
		int [] retArray = new int[8];
		if (x > 0) {
			retArray[1] = states[x - 1][y];
			if (y > 0) {
				retArray[0] = states[x - 1][y - 1];
			}
			if (y < cols - 1) {
				retArray[2] = states[x - 1][y + 1];
			}
		}
		if (x < rows - 1) {
			retArray[6] = states[x + 1][y];
			if (y > 0) {
				retArray[5] = states[x + 1][y - 1];
			}
			if (y < cols - 1) {
				retArray[7] = states[x + 1][y + 1];
			}
		}
		if (y > 0) {
			retArray[3] = states[x][y - 1];
		}
		if (y < cols - 1) {
			retArray[4] = states[x][y + 1];
		}
		return retArray;
	}
	
	
	/**
	 * testing method to update states based on game of life
	 * @param x
	 * @param y
	 * @param neighbors
	 */
	private void updateState(int x, int y, int[] neighbors){
		int living = 0;
		for(int i = 0; i < neighbors.length; i++){
			living += neighbors[i];
		}
		boolean alive = states[x][y] == 1;
		if(alive && living < 2){
			alive = false;
		}
		else if(alive && living > 3){
			alive = false;
		}
		else if(!alive && living == 3){
			alive = true;
		}
		if(alive){
			states[x][y] = 1;
		}
		else{
			states[x][y] = 0;
		}
	}

	/**
	 * Method to begin simulation Must be called in order to start a timeline
	 */
	public void startSimulation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update());
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

	private void updateCells() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell[] neighbors = getNeighbors(i, j);
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

	public void play() {
		// TODO
		
	}

	public Scene getScene() {
		return scene;
	}

	/**
	 * Create the scene for simulator
	 */
	// TODO: scene setup
	private void setUpScene() {
		Group root = new Group();
		scene = new Scene(root, SceneController.WIDTH, SceneController.HEIGHT);
		HBox hBox = new HBox();
		root.getChildren().add(hBox);
		buttonMain = new Button("Back to Main");
		buttonSettings = new Button("Back to Settings");
		buttonPlay = new Button("PLAY");
		hBox.getChildren().addAll(buttonMain, buttonSettings, buttonPlay);
	}

	/**
	 * Create the HUD for the simulator
	 */
	// TODO: HUD setup
	private void setUpHUD() {

	}

	/**
	 * Generate list of neighbors surrounding target cell List indices correlate
	 * to neighbors as follows:
	 * 0 1 2
	 * 3 X 4
	 * 5 6 7
	 */
	private Cell[] getNeighbors(int x, int y) {
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

	public void show() {
		controller.setScene(scene);
	}
}
