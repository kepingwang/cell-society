package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private static int VGAP=10;
	private static int HGAP=5;
	
	private String gameName;
	private TextField widthField;
	private TextField heightField;
	private TextField rowsField;
	private TextField colsField;
	
	protected HBox gameSpecificOptions;

	private SceneController controller;
	private MainMenu mainMenu;
	private Scene scene;
	
	public OptionsScreen(SceneController controller, MainMenu mainMenu, String gameName) {
		this.controller = controller;
		this.mainMenu = mainMenu;
		this.gameName = gameName;
		setUpOptionsScreen();
	}
	
	private void setUpOptionsScreen(){
		
		// layout of the scene:
		VBox body = new VBox();
		body.setSpacing(VGAP);
		
		HBox optionsContainer = new HBox();
		optionsContainer.setSpacing(HGAP);
		VBox labelsBox = new VBox();
		labelsBox.setSpacing(VGAP);
		VBox fieldsBox = new VBox();
		fieldsBox.setSpacing(VGAP);
		optionsContainer.getChildren().addAll(labelsBox, fieldsBox);
		
		gameSpecificOptions = new HBox();
		gameSpecificOptions.setSpacing(HGAP);

		
		HBox buttonsContainer = new HBox();
		buttonsContainer.setSpacing(HGAP);
		
		body.getChildren().addAll(
				gameTitle(),
				optionsContainer,
				gameSpecificOptions,
				buttonsContainer
			);
		
		scene = new Scene(body, SIZE, SIZE);		
		
		// Add nodes to the containers
		labelsBox.getChildren().addAll(
				makeLabel("pct_primary"),
				makeLabel("pct_primary"),
				makeLabel("size"),
				makeLabel("size")
			);
		
		widthField = makeTextField("0-100");
		heightField = makeTextField("0-100");
		rowsField = makeTextField("0-100");
		colsField = makeTextField("0-100");
		
		fieldsBox.getChildren().addAll(
				widthField,
				heightField,
				rowsField,
				colsField
			);
		
		buttonsContainer.getChildren().addAll(
				makeButton("START", e -> startGame()),
				makeButton("CLEAR", e -> clearOptions()),
				makeButton("BACK",  e -> goToMainMenu())
			);
		
	}
	
	// Layout construction helpers
	private Text gameTitle() {
		Text simulation = new Text(controller.getResource("simulation") + gameName);
		simulation.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		return simulation;
	}
	private TextField makeTextField(String prompt) {
		TextField tf = new TextField();
		tf.setPromptText(prompt);
		return tf;
	}
	private Label makeLabel(String label) {
		Label lbl = new Label(controller.getResource(label));
		return lbl;
	}
	private Button makeButton(String name, EventHandler<ActionEvent> handler){
		Button btn = new Button(name);
		btn.setOnAction(handler);
		return btn;
	}
	
	/**
	 * Subclasses should overwrite this method to add game specific options.
	 */
	protected void addGameSpecificOptions() {
		gameSpecificOptions.getChildren().add(new Text("Game Specific Options..."));
	}
	
	private boolean checkInputValidity(){
		return true;
		// TODO
	}
	protected boolean checkGameSpecificInputValidity() {
		return true;
	}
	
	private List<TextField> mainFieldList() {
		List<TextField> list = new ArrayList<>();
		list.add(widthField);
		list.add(heightField);
		list.add(rowsField);
		list.add(colsField);
		return list;
	}
	/**
	 * Subclass should overwrite this method to add game specific options
	 * @return
	 */
	protected List<TextField> gameSpecificFieldList() {
		List<TextField> list = new ArrayList<>();
		return list;
	}
	private List<TextField> fieldList() {
		List<TextField> list = new ArrayList<>();
		list.addAll(mainFieldList());
		list.addAll(gameSpecificFieldList());
		return list;
	}
	
	// TODO: other protected methods for subclasses to use
	
	// Button logics
	private void startGame(){
		if (checkInputValidity() && checkGameSpecificInputValidity()) {
			playSociety("hello");
		}
	}
	private void clearOptions() {
		for (TextField field : fieldList()) {
			field.clear();
		}
	}
	private void goToMainMenu() {
		mainMenu.show();
	}

	// Player Society TODO: take input from fields and overwrite config files
	private void playSociety(String configFile) {
		SocietyXMLParser parser = new SocietyXMLParser();
		try {
			SocietyScreen societyScreen;
			societyScreen = new SocietyScreen(
					controller, this, parser.parse(configFile)
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
