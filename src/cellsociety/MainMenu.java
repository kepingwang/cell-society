package cellsociety;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {
	private int SIZE=500;
	private Paint BACKGROUND = Color.WHITE;
	private String sim_type;
	private Stage primaryStage;
	BorderPane root = new BorderPane();
	Stage theStage;
	
	
	public static void main(String[] args){
		Application.launch(args);
	}
	
	
	
	@Override
	public void start(Stage primaryStage) {
		theStage=primaryStage;
		
		Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(5);
		
		Text welcome = new Text("Welcome to the Cell Society simulator,\n"
				+ "please select a simulation.");
		welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		GridPane.setConstraints(welcome, 5, 5);
		
		Button game_of_life = new Button("Game of Life");
		GridPane.setConstraints(game_of_life, 5, 10);
		
		Button spreading_of_fire = new Button("Spreading of Fire");
		GridPane.setConstraints(spreading_of_fire, 5, 15);
		
		Button segregation = new Button("Segregation");
		GridPane.setConstraints(segregation, 5, 20);
		
		Button predator_prey = new Button("Predator-Prey");
		GridPane.setConstraints(predator_prey, 5, 25);
		
		game_of_life.setOnAction(e->game_of_lifeClicked(e));
		spreading_of_fire.setOnAction(e->spreading_of_fireClicked(e));
		segregation.setOnAction(e->segregationClicked(e));
		predator_prey.setOnAction(e->predator_preyClicked(e));
		
		
		grid.getChildren().add(welcome);
		grid.getChildren().addAll(game_of_life, spreading_of_fire, segregation, predator_prey);
		
		root.setCenter(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}



	private void predator_preyClicked(ActionEvent e) {
		sim_type="Predator-Prey";
		//ADVANCE
	}



	private void segregationClicked(ActionEvent e) {
		sim_type="Segregation";
		//ADVANCE
	}



	private void spreading_of_fireClicked(ActionEvent e) {
		sim_type="Spreading of Fire";
		//ADVANCE
	}



	private void game_of_lifeClicked(ActionEvent e) {
		sim_type="Game of Life";
		//ADVANCE
	}
}
