package gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.SocietyXMLParser;

/**
 * The screen where users can make some settings.
 *
 */
public class OptionsScreen{
	private static int SIZE=600;
	private Paint BACKGROUND = Color.WHITE;
	BorderPane root = new BorderPane();
	private static int VGAP=10;
	private static int HGAP=5;
	private static int LABELLOCX=5;
	private static int LABELLOCY=8;
	private static final int TEXTLOCX=6;
	private static final int TEXTLOCY=8;
	private static final int BTNLOCX=4;
	private static final int BTNLOCY=20;
	
	private ArrayList<Label> labelList = new ArrayList<Label>();
	private ArrayList<TextField> textfieldList = new ArrayList<TextField>();
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	
	private String sim_type;
	private static int agent_amount;
	private static int empty_amount;
	private static int grid_size;
	private static int sim_delay;
	
	
	private Text error_agent_amount=new Text("");
	private Text error_empty_amount=new Text("");
	private Text error_grid_size=new Text("");
	private Text error_sim_delay=new Text("");

	private boolean allValid=false;
	private boolean valid_agent=false;
	private boolean valid_empty=false;
	private boolean valid_size=false; 
	private boolean valid_delay=false;
	
	// configuration variables
		// To be displayed to the user
	private int rows;
	private int cols;
	private double width = 100;
	private double height = 100;
	private double stepDelay; // TODO: add it to society
	
	private static Scene scene;
	private SceneController controller;
	
	public OptionsScreen(SceneController controller, String gameName) {
		this.controller = controller;
		sim_type = gameName;
		setUpOptionsScreen();
		//controller.getResource("GameOfLifeLabel");
	}
	
	private void setUpOptionsScreen(){
		
		GridPane grid = new GridPane();
		grid.setVgap(VGAP);
		grid.setHgap(HGAP);
		
		scene = new Scene(root, SIZE, SIZE, BACKGROUND);
		
		Text simulation = new Text(controller.getBundle().getString("simulation") + sim_type);
		simulation.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		GridPane.setConstraints(simulation, 5, 5);
		
		
		//adjust later
		labelList.add(makeLabel("pct_primary"));
		textfieldList.add(makeTextField("0-100"));
		
		labelList.add(makeLabel("pct_empty"));
		textfieldList.add(makeTextField("0-100"));
		
		labelList.add(makeLabel("size"));
		textfieldList.add(makeTextField("Enter N; N<50"));
		
		labelList.add(makeLabel("delay"));
		textfieldList.add(makeTextField("0-3000"));
		
		placeLabelAndTextField();
		
		buttonList.add(makeButton("START", e->startClicked(e)));
		buttonList.add(makeButton("CLEAR", e->clearClicked(e)));		
		buttonList.add(makeButton("BACK", e->backClicked(e)));
		
		placeButtons();
		
		//STUFF
		grid.getChildren().add(simulation);
		grid.getChildren().addAll(labelList);
		grid.getChildren().addAll(textfieldList);
		grid.getChildren().addAll(error_agent_amount, error_empty_amount, error_grid_size, error_sim_delay);
		grid.getChildren().addAll(buttonList);
		
		root.setCenter(grid);
	}
	
	private void placeLabelAndTextField(){
		int i=0;
		for(Label lbl : labelList){
			GridPane.setConstraints(lbl, LABELLOCX, LABELLOCY + i);
			i+=3;
		}
		int m=0;
		for(TextField tf: textfieldList){
			GridPane.setConstraints(tf, TEXTLOCX, TEXTLOCY + m);
			m+=3;
		}
	}
	
	private void placeButtons(){
		int i=0;
		for(Button btn: buttonList){
			GridPane.setConstraints(btn, BTNLOCX + i, BTNLOCY);
			i++;
		}
	}
	
	private Button makeButton(String name, EventHandler<ActionEvent> handler){
		Button btn = new Button(name);
		btn.setOnAction(handler);
		return btn;
	}
	
	private TextField makeTextField(String prompt) {
		TextField tf = new TextField();
		tf.setPromptText(prompt);
		return tf;
	}

	private Label makeLabel(String label) {
		Label lbl = new Label(controller.getBundle().getString(label));
		return lbl;
	}

	private void handleBadInput(){
		if(agent_amount<0 || agent_amount>100){
			error_agent_amount.setText(controller.getBundle().getString("error_agent_amount"));
			error_agent_amount.setFill(Color.RED);
			GridPane.setConstraints(error_agent_amount, 5, 23);
		}
		else{
			error_agent_amount.setText("");
			valid_agent=true;
		}
		if(empty_amount<0 || empty_amount>100){
			error_empty_amount.setText(controller.getBundle().getString("error_empty_amount"));
			error_empty_amount.setFill(Color.RED);
			GridPane.setConstraints(error_empty_amount, 5, 24);
		}
		else{
			error_empty_amount.setText("");
			valid_empty=true;
		}
		if(grid_size<10 || grid_size>50){
			error_grid_size.setText(controller.getBundle().getString("error_grid_size"));
			error_grid_size.setFill(Color.RED);
			GridPane.setConstraints(error_grid_size, 5, 25);
		}
		else{
			error_grid_size.setText("");
			valid_size=true;
		}
		if(sim_delay<0 || sim_delay>3000){
			error_sim_delay.setText(controller.getBundle().getString("error_sim_delay"));
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
		agent_amount = stringToInt(textfieldList.get(1).getText());
		empty_amount = stringToInt(textfieldList.get(2).getText());
		grid_size = stringToInt(textfieldList.get(3).getText());
		sim_delay = stringToInt(textfieldList.get(4).getText());
		
		handleBadInput();
		if(allValid){
			//(new SettingsScreen(controller)).show();
			playSociety();
		}
	}
	
	private void clearClicked(ActionEvent e){
		for(TextField tf: textfieldList){
			tf.clear();
		}
		textfieldList.clear();
	}
	
	private void backClicked(ActionEvent e){
		(new MainMenu(controller)).show();
	}
	
	public static int getAgentAmount(){
		return agent_amount;
	}
	
	public static int getEmptyAmount(){
		return empty_amount;
	}
	
	public static int getGridSize(){
		return grid_size;
	}
	
	public static int getSimDelay(){
		return sim_delay;
	}
	
	private void playSociety() {
		SocietyXMLParser parser = new SocietyXMLParser();
		try {
			SocietyScreen societyScreen;
			societyScreen = new SocietyScreen(
					controller, this, parser.parse("data/wator1.xml")
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
