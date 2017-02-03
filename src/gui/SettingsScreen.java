package gui;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utils.SocietyXMLParser;

/**
 * The screen where users can make some settings.
 * @author keping
 *
 */
public class SettingsScreen {

	private App controller;
	private Scene scene;
	
	// configuration variables
	// To be displayed to the user
	private int rows = 10;
	private int cols = 10;
	private double width = 100;
	private double height = 100;


	// Buttons and texts for display
	Button buttonMain;
	Button buttonPlay;
	
	public SettingsScreen(App controller) {
		this.controller = controller;
		initScene();
	}
	
	private void initScene() {
		// TODO: add buttons and text fields for settings
		Group root = new Group();
		scene = new Scene(root, App.WIDTH, App.HEIGHT);
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
		SocietyXMLParser parser = new SocietyXMLParser();
		try {
			SocietyScreen societyScreen;
			societyScreen = new SocietyScreen(
					controller, this, parser.parse("data/saved-cell-society.xml")
			);
			societyScreen.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void show() {
		controller.setScene(scene);
	}
	
}
