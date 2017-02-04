
package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import cellsociety.Cell;
import cellsociety.GameOfLifeCell;
import cellsociety.GameOfLifeRules;
import core.Society;
import core.rules.Rule;


/**
 * The screen where users can make some settings.
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
				cells[i][j] = new GameOfLifeCell(j*wCell, i*hCell, wCell*0.95, hCell*0.95, i % 2, rule);
			}
		}
		return cells;
	}
	
	public void show() {
		controller.setScene(scene);

public class OptionsScreen extends Application {
	private int SIZE=500;
	private Paint BACKGROUND = Color.WHITE;
	BorderPane root = new BorderPane();
	private String sim_type;
	private int agent_amount;
	private int empty_amount;
	private int grid_size;
	private int sim_delay;
	private Text error_agent_amount=new Text("");
	private Text error_empty_amount=new Text("");
	private Text error_grid_size=new Text("");
	private Text error_sim_delay=new Text("");
	private TextField ap;
	private TextField ec;
	private TextField nc;
	private TextField del;
	private boolean allValid=false;
	private boolean valid_agent=false;
	private boolean valid_empty=false;
	private boolean valid_size=false; 
	private boolean valid_delay=false;
	
	public static void main(String[] args){
		//for testing
		Application.launch(args);
	}
	
	public void start(Stage primaryStage){
		Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(5);
		
		Text simulation = new Text("Simulation: " + sim_type);
		simulation.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		GridPane.setConstraints(simulation, 5, 5);
		
		
		//adjust later
		Label agent_proportion = new Label("Percent primary agent: ");
		GridPane.setConstraints(agent_proportion, 5, 8);
		ap = new TextField();
		GridPane.setConstraints(ap, 6, 8);
		ap.setPromptText("0-100");
		
		
		Label empty_cells = new Label("Percent empty cells: ");
		GridPane.setConstraints(empty_cells, 5, 11);
		ec = new TextField();
		GridPane.setConstraints(ec, 6, 11);
		ec.setPromptText("0-100");
		
		
		
		Label num_cells = new Label("Size (NxN simulation): ");
		GridPane.setConstraints(num_cells, 5, 14);
		nc = new TextField();
		GridPane.setConstraints(nc, 6, 14);
		nc.setPromptText("Enter N; N<50");
		
		
		
		Label delay = new Label("Delay: ");
		GridPane.setConstraints(delay, 5, 17);
		del = new TextField();
		GridPane.setConstraints(del, 6, 17);
		del.setPromptText("0-3000");
		
		
		
		Button start = new Button("START");
		GridPane.setConstraints(start, 7, 20);
		
		Button clear = new Button("CLEAR");
		GridPane.setConstraints(clear, 6, 20);
		
		start.setOnAction(e->startClicked(e));
		clear.setOnAction(e->clearClicked(e));
		
		//STUFF
		grid.getChildren().addAll(simulation, agent_proportion, empty_cells, num_cells, delay);
		grid.getChildren().addAll(ap, ec, nc, del);
		grid.getChildren().addAll(error_agent_amount, error_empty_amount, error_grid_size, error_sim_delay);
		grid.getChildren().addAll(start, clear);
		
		root.setCenter(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void handleBadInput(){
		if(agent_amount<0 || agent_amount>100){
			error_agent_amount.setText("Input valid percent primary agent value.");
			error_agent_amount.setFill(Color.RED);
			GridPane.setConstraints(error_agent_amount, 5, 23);
		}
		else{
			error_agent_amount.setText("");
			valid_agent=true;
		}
		if(empty_amount<0 || empty_amount>100){
			error_empty_amount.setText("Input valid percent empty cells value.");
			error_empty_amount.setFill(Color.RED);
			GridPane.setConstraints(error_empty_amount, 5, 24);
		}
		else{
			error_empty_amount.setText("");
			valid_empty=true;
		}
		if(grid_size<10 || grid_size>50){
			error_grid_size.setText("Input valid size value.");
			error_grid_size.setFill(Color.RED);
			GridPane.setConstraints(error_grid_size, 5, 25);
		}
		else{
			error_grid_size.setText("");
			valid_size=true;
		}
		if(sim_delay<0 || sim_delay>3000){
			error_sim_delay.setText("Input valid delay value.");
			error_sim_delay.setFill(Color.RED);
			GridPane.setConstraints(error_sim_delay, 5, 26);
		}
		else{
			error_sim_delay.setText("");
			valid_delay=true;
		}
		if(valid_agent && valid_empty && valid_size && valid_delay){
			allValid=true;
		}
	}
	
	private int stringToInt(String s){
		if(!s.equals("")){
			return Integer.parseInt(s);
		}
		else{
			return -1;
		}
	}
	
	private void startClicked(ActionEvent e){
		agent_amount = stringToInt(ap.getText());
		empty_amount = stringToInt(ec.getText());
		grid_size = stringToInt(nc.getText());
		sim_delay = stringToInt(del.getText());
		
		handleBadInput();
		
		if(allValid){
			//ADVANCE
		}
	}
	
	private void clearClicked(ActionEvent e){
		ap.clear();
		ec.clear();
		nc.clear();
		del.clear();
	}
	
}
