package gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainMenu {
	private static final int SIZE = 500;
	private static final int BTNXLOC = 5;
	private static final int BTNYLOC = 5;
	private static final int VGAP=50;
	private static final int HGAP=5;
	private Scene scene;
	private SceneController controller;
	private ArrayList<Button> button_list = new ArrayList<Button>();
	
	
	public MainMenu(SceneController controller) {
		this.controller = controller;
		setUpMainMenu();
	}
	
	private void setUpMainMenu(){
		BorderPane root = new BorderPane();
		scene = new Scene(root, SIZE, SIZE);
		
		GridPane grid = new GridPane();
		grid.setVgap(VGAP);
		grid.setHgap(HGAP);
		
		Text welcome = new Text("Welcome to the Cell Society simulator,\n"
				+ "please select a simulation.");
		welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		GridPane.setConstraints(welcome, 5, 5);
		
		button_list.add(makeButton("Game of Life", e -> playOptionsScreen("game-of-life")));
		button_list.add(makeButton("Spreading of Fire", e -> playOptionsScreen("fire")));
		button_list.add(makeButton("Segregation", e -> playOptionsScreen("segregation")));
		button_list.add(makeButton("WaTor World", e -> playOptionsScreen("wator")));
		
		placeButtons();
		
		grid.getChildren().add(welcome);
		grid.getChildren().addAll(button_list);
		
		root.setCenter(grid);
		
	}

	private void placeButtons() {
		int i = BTNYLOC;
		for(Button btn: button_list){
			GridPane.setConstraints(btn, BTNXLOC, BTNYLOC + i);
			i++;
		}
		
	}

	private void playOptionsScreen(String gameName) {
		(new OptionsScreen(controller, gameName)).show();
	}

	private Button makeButton(String name, EventHandler<ActionEvent> handler){
		Button btn = new Button(name);
		return btn;
	}

	
	public void show() {
		controller.setScene(scene);
	}
	
}
