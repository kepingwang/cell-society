package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * The screen where users can make some settings.
 * @author keping
 *
 */
public class SettingsScreen {

	private SceneController controller;
	private Scene scene;
	
	// configuration variables
	// To be displayed to the user
	private int rows = 10;
	private int cols = 10;
	private double width = 100;
	private double height = 100;
	private double stepDelay; // TODO: add it to society

	// Buttons and texts for display
	Button buttonMain;
	Button buttonPlay;
	
	public SettingsScreen(SceneController controller) {
		this.controller = controller;
		initScene();
	}
	
	private void initScene() {
		// TODO: add buttons and text fields for settings
		Group root = new Group();
		scene = new Scene(root, SceneController.WIDTH, SceneController.HEIGHT);
		VBox vBox = new VBox();
		root.getChildren().add(vBox);
		buttonMain = new Button("Back");
		buttonPlay = new Button("Play");
		vBox.getChildren().addAll(buttonMain, buttonPlay);
		initEventHandlers();
	}

	private void initEventHandlers() {
		buttonMain.setOnMouseClicked(e -> controller.backToMain());
		buttonPlay.setOnMouseClicked(e -> playSociety());
	}

	private void playSociety() {
		Society society = new Society(controller, this, cells());
		society.show();
	}
	
	private Cell[][] cells() {
		Cell[][] cells = new Cell[rows][cols];
		Rule rule = new GameOfLifeRules();
		double wCell = width / cols;
		double hCell = height / rows;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				cells[i][j] = new Cell(
						j*wCell, i*hCell, wCell*0.95, hCell*0.95, i % 2, rule
				);
			}
		}
		return cells;
	}
	
	public void show() {
		controller.setScene(scene);
	}
	
}
