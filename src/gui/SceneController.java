package gui;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The entrance of the Cell Society Application.
 * @author keping
 *
 */
public class SceneController extends Application {
	public static final double WIDTH = 500;
	public static final double HEIGHT = 500;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String STYLESHEET = "default.css";
    private ResourceBundle myResources;
    private String language = "English";
    
	private Stage stage;
	private MainMenu mainMenu;

	@Override
	public void init() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		mainMenu = new MainMenu(this);
		goToMainMenu();
		System.out.println(Locale.getDefault());
		stage.show();
	}
	
	/**
	 * Set the scene of the stage to a given scene.
	 * @param scene
	 */
	public void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	public void goToMainMenu() {
		mainMenu.show();
	}

	
	public String getResource(String key) {
		return myResources.getString(key);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public ResourceBundle getBundle(){
		return myResources;
	}
	
}
