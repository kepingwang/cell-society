package refactor.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class SplashScreen {
	public static final String SPLASH_PAGE_RESOURCE = "/refactor/view/splash-screen.fxml";

	private CellSocietyApp app;
	private Scene scene;

	
	public SplashScreen(CellSocietyApp app) throws IOException {
		this.app = app;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SPLASH_PAGE_RESOURCE));
        loader.setResources(CellSocietyApp.getResourceBundle());
        VBox vBox = (VBox) loader.load();
		scene = new Scene(vBox, CellSocietyApp.WIDTH, CellSocietyApp.HEIGHT);
		scene.setOnMouseClicked(e -> showMainScreen());
	}

	public Scene getScene() {
		return scene;
	}
	public void showMainScreen() {
		app.showMainScreen();
	}
	
	
}
