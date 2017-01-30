package cellsociety;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneController extends Application {
	public static final double WIDTH = 400;
	public static final double HEIGHT = 500;
	
	private Stage stage;
	private Scene scene;
	private List<Button> buttons;
	private VBox vBox;
	
	
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
		vBox = new VBox();
		root.getChildren().add(vBox);
		initButtons();
	}
	
	private void playSociety1() {
		(new SettingsScreen(this)).show();
	}
	
	private void playSociety2() {
		// TODO
	}
	
	/**
	 * To be changed if we want to add new cell society games.
	 */
	private void initButtons() {
		buttons = new ArrayList<Button>();
		
		Button button1 = new Button("Society 1");
		button1.setOnMouseClicked(e -> playSociety1());
		buttons.add(button1);
		
		Button button2 = new Button("Society 2");
		button2.setOnMouseClicked(e -> playSociety2());
		buttons.add(button2);
		
		vBox.getChildren().addAll(buttons);
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
