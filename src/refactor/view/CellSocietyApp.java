package refactor.view;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CellSocietyApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// View settings files
        InputStream fxmlStream = getClass().getResourceAsStream("cell-society.fxml");
        String cssForm = getClass().getResource("cell-society.css").toExternalForm();

        FXMLLoader loader = new FXMLLoader();		
        VBox root = (VBox) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssForm); 
        
        stage.setScene(scene);
        stage.setTitle("Cell Society");
        stage.show();
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
