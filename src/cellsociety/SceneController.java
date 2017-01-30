package cellsociety;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneController extends Application {
	public static final double WIDTH = 400;
	public static final double HEIGHT = 500;
	public static final int SOCIETY_NUM = 4;
	
	
	private Stage stage;
	private Scene scene;
	private Button[] buttons;
	
	
	public void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	/**
	 * Go back to this main screen
	 */
	public void backToMain() {
		stage.setScene(scene);
	}
	
	private void initScene() {
		Group root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		// TODO: Add buttons text for the main Screen
		VBox vBox = new VBox();
		buttons = new Button[SOCIETY_NUM];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button("Society " + i);
			buttons[i].setId("society-"+i); // like the css id
			buttons[i].setOnMouseClicked(
					e -> goToSociety(((Control) e.getSource()).getId())
			);
		}
		root.getChildren().add(vBox);
		vBox.getChildren().addAll(buttons);
	}
	
	/**
	 * Go to the setting screen of society #societyID.
	 * @param societyID
	 */
	private void goToSociety(String societyID) {
		SettingsScreen settings = new SettingsScreen(this, societyID);
		settings.show();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		initScene();
		stage.setScene(scene);
		stage.show();
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
