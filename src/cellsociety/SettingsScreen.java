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
	private String societyID;
	private int rows;
	private int cols;
	private double width;
	private double height;
	private double stepDelay;
	
	public String getSocietyID() { return societyID; }
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	public double getStepDelay() { return stepDelay; }
	

	// Buttons and texts for display
	Button buttonMain;
	Button buttonPlay;
	
	public SettingsScreen(SceneController controller, String societyID) {
		this.controller = controller;
		this.societyID = societyID;
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
		buttonPlay.setOnMouseClicked(e -> {
			Society society = new Society(controller, this);
			society.show();
		});
	}

	public void show() {
		controller.setScene(scene);
	}
	
}
