package refactor.view;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CellSocietyApp extends Application {
	public static final String MAIN_PAGE_RESOURCE = "/refactor/view/main-screen.fxml";
	public static final String CSS_RESOURCE = "/refactor/view/main-style.css";
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String LANGUAGE = "English";
    private static ResourceBundle resourceBundle;
    public static final double WIDTH = 800;
    public static final double HEIGHT = 650;

    private Stage stage;
    private Scene scene;
    
	@Override
	public void start(Stage stage) throws Exception {
		resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
        String cssForm = getClass().getResource(CSS_RESOURCE).toExternalForm();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PAGE_RESOURCE));
        loader.setResources(getResourceBundle());

        HBox root = new HBox();
        scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(cssForm);
        
        VBox vBox = (VBox) loader.load();
        root.getChildren().addAll(vBox);

        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle(resourceBundle.getString("Title"));
        
        SplashScreen splash = new SplashScreen(this);
        splash.getScene().getStylesheets().add(cssForm);
        stage.setScene(splash.getScene());
        stage.sizeToScene();
        stage.show();	
	}
	
	public void showMainScreen() {
		stage.setScene(scene);
	}
	
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
