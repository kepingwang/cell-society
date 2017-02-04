package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneController extends Application {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	//private Paint BACKGROUND = Color.WHITE;
/**
 * The entrance of the Cell Society Application.
 * @author keping
 *
 */
public class App extends Application {
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
		
		Text title = new Text("Cell Society");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		title.setX(180);
		title.setY(50);
		
		Text credit = new Text("By Keping Hang, Gordon Hyunh and Jacob Weiss");
		credit.setX(100);
		credit.setY(80);
		
		//MAYBE IMAGE
		
		// TODO: Add button text for the main Screen
		vBox = new VBox();
		vBox.setLayoutX(210);
		vBox.setLayoutY(400);
		root.getChildren().addAll(title, credit);
		root.getChildren().add(vBox);
		initButton();
		 
	}
	
	private void playMainMenu() {
		(new MainMenu(this)).show();
	}
	
	
	/**
	 * To be changed if we want to add new cell society games.
	 */
	private void initButton() {
		buttons = new ArrayList<Button>();
		
		Button main_menu = new Button("Main Menu");
		main_menu.setOnMouseClicked(e -> playMainMenu());
		buttons.add(main_menu);
		
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
