package gui;


import core.Cell;
import core.Society;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utils.CellsParser;

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
		CellsParser parser = new CellsParser();
		try {
			SocietyScreen societyScreen;
			societyScreen = new SocietyScreen(
					controller, this, new Society(parser.parse("data/game_of_life1.xml"))
			);
			societyScreen.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Only for testing.
	 * @return
	 */
	private Cell[][] cells() {
		Cell[][] cells = new Cell[rows][cols];
		double wCell = width / cols;
		double hCell = height / rows;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				cells[i][j] = new Cell(
						"game-of-life", new Color[] {Color.WHITE, Color.BLACK},
						j*wCell, i*hCell, wCell*0.95, hCell*0.95, i % 2
				);
			}
		}
		return cells;
	}
	
	public void show() {
		controller.setScene(scene);
	}
	
}
