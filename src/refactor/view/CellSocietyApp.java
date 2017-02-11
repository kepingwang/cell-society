package refactor.view;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CellSocietyApp extends Application {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String LANGUAGE = "English";
    private static ResourceBundle resourceBundle;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

	@Override
	public void start(Stage stage) throws Exception {
		resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
        String cssForm = getClass().getResource("main-style.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        loader.setResources(getResourceBundle());

        HBox root = new HBox();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(cssForm);
        
        VBox vBox = (VBox) loader.load();
        root.getChildren().addAll(vBox);

        stage.setResizable(false);
        stage.setTitle(resourceBundle.getString("Title"));
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
		
	}
	
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
