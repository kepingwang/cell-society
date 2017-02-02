package cellsociety;



import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class Society {
	private final int FRAMES_PER_SECOND = 2;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private SceneController controller;
	private OptionsScreen options;
	private Scene scene;
	private Group root;
	private Timeline timeline;
	private KeyFrame frame;
	private Rule rule;

	private double speedMultiplier;
	private Button buttonMain;
	private Button buttonSettings;
	private Button buttonPlay;
	private Button buttonPause;
	private Button buttonResume;
	private Button buttonFastForward;
	private Button buttonSlowForward;
	private Button buttonAdvanceFrame;

	private Cell[][] cells;
	private int[][] states;

	public Scene getScene() {
		return scene;
	}
	
	public Society(SceneController controller, OptionsScreen options, Cell[][] cells) {
		this.controller = controller;
		this.options = options;
		this.cells = cells;
		setUpScene();
		setUpHUD();
		initEventHandlers();
	}
	
	private void initEventHandlers() {
		buttonMain.setOnMouseClicked(e -> controller.backToMain());
		buttonSettings.setOnMouseClicked(e -> options.show());
		buttonPlay.setOnMouseClicked(e -> startSimulation());
		buttonPause.setOnMouseClicked(e -> this.pauseSimulation());
		buttonResume.setOnMouseClicked(e -> this.resumeSimulation());
		buttonFastForward.setOnMouseClicked(e -> this.fastForward());
		buttonSlowForward.setOnMouseClicked(e -> this.slowForward());
		buttonAdvanceFrame.setOnMouseClicked(e -> this.advanceFrame());
	}

	private void updateCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				Cell[] neighbors = getNeighbors(i, j);
				cells[i][j].update(neighbors);
			}
		}
	}

	private void syncCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].syncState();
			}
		}
	}

	/**
	 * Update the cell states in one round.
	 */
	public void update() {
		updateCells(); // TODO
		syncCells();
	}
	
	/**
	 * Create the scene for simulator
	 */
	// TODO: scene setup
	private void setUpScene() {
		Group root = new Group();
		scene = new Scene(root, SceneController.WIDTH, SceneController.HEIGHT);
		VBox vBox = new VBox();
		root.getChildren().add(vBox);
		buttonMain = new Button("Back to Main");
		buttonSettings = new Button("Back to Settings");
		buttonPlay = new Button("PLAY");
		buttonPause = new Button("Pause");
		buttonPause.setDisable(true);
		buttonResume = new Button("Resume");
		buttonResume.setDisable(true);
		buttonResume = new Button("Resume");
		buttonFastForward = new Button("Speed Up");
		buttonSlowForward = new Button("Slow Down");
		buttonAdvanceFrame = new Button("Forward Frame");
		vBox.getChildren().addAll(buttonMain, buttonSettings, buttonPlay, buttonPause, buttonResume, buttonFastForward, buttonSlowForward, buttonAdvanceFrame);
		Group cellGroup = new Group();
		for (Cell[] cellRow : cells) {
			for (Cell cell : cellRow) {
				cellGroup.getChildren().add(cell);
			}
		}
		vBox.getChildren().add(cellGroup);
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
		int rows = cells.length;
		int cols = cells[0].length;
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
	
	/**
	 * Method to begin simulation Must be called in order to start a timeline
	 */
	public void startSimulation() {
		speedMultiplier = 1;
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update());
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		buttonPause.setDisable(false);
		buttonResume.setDisable(false);
		timeline.play();
	}
	
	/**
	 * Method to pause simulation
	 */
	private void pauseSimulation(){
		timeline.pause();
	}
	
	/**
	 * Method to resume simulation
	 */
	private void resumeSimulation(){
		timeline.play();
	}
	
	/**
	 * Method to fast forward simulation
	 */
	private void fastForward(){
		timeline.stop();
		if(speedMultiplier >= .25){
			speedMultiplier -= .25;
		}
		timeline.getKeyFrames().clear();
		frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY * speedMultiplier), e -> update());
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}
	
	/**
	 * Method to slow down simulation
	 */
	private void slowForward(){
		timeline.stop();
		speedMultiplier += .25;
		timeline.getKeyFrames().clear();
		frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY * speedMultiplier), e -> update());
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}
	
	/**
	 * Method to step forward a frame, will automatically pause timeline if not pause already
	 */
	private void advanceFrame(){
		timeline.pause();
		this.update();
	}
	
	/**
	 * For testing
	 * @param states
	 */
	public void setUpCells(int[][] states){
		cells = new Cell[states.length][states[0].length];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				// TODO: initialize cell state
				cells[i][j] = new GameOfLifeCell(i*50, j*50, 50, 50, states[i][j], rule);
				root.getChildren().add(cells[i][j]);
			}
		}
	}
	
	/**
	 * testing constructor
	 * @param statesIn
	 */
	
	/**
	 * testing method to update states due to neighbors
	 */
	public void updateStates(){
		for (int i = 0; i < states.length; i++) {
			for (int j = 0; j < states[0].length; j++) {
				int[] neighbors = getStateNeighbors(i, j);
				updateState(i,j,neighbors);
			}
		}
	}
	
	Button button;
	/**
	 * testing method to get current states
	 * @return
	 */
	public int[][] getStates(){
		return states;
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
	/**
	 * testing method to get current states
	 * @return
	 */
	
	private int[] getStateNeighbors(int x, int y){
		int rows = states.length;
		int cols = states[0].length;
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
}
