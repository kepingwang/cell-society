package cellsociety;

import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Society {
	private SceneController controller;
	private SettingsScreen settings;
	private Scene scene;
	
	private Button buttonMain;
	private Button buttonSettings;
	private Button buttonPlay;
	
	
	private int rows;
	private int cols;
	private Cell[][] cells;
	
	
	public Society(SceneController controller, SettingsScreen settings) {
		this.controller = controller;
		this.settings = settings;
		Group root = new Group();
		scene = new Scene(root, SceneController.WIDTH, SceneController.HEIGHT);
		HBox hBox = new HBox();
		root.getChildren().add(hBox);
		buttonMain = new Button("Back to Main");
		buttonSettings = new Button("Back to Settings");
		buttonPlay = new Button("PLAY");
		hBox.getChildren().addAll(buttonMain, buttonSettings, buttonPlay);
		initEventHandlers();
	}
 	
	private void initEventHandlers() {
		buttonMain.setOnMouseClicked(e -> controller.backToMain());
		buttonSettings.setOnMouseClicked(e -> settings.show());
		buttonPlay.setOnMouseClicked(e -> play());
	}
	
	// TODO: constructor
	public Society(SettingsScreen settings) {
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

	public void play() {
		// TODO
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void show() {
		controller.setScene(scene);
	}

	
}
