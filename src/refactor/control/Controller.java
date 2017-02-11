package refactor.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import refactor.view.CellSocietyApp;

public class Controller {
	private static final String RESOUCE_SCREEN = "/refactor/view/society-screen.fxml";
	
	// Animation
	public static final double MIN_SPEED_DIVIDER = 0.2;
	public static final double MAX_SPEED_DIVIDER = 8;
	public static final double INIT_SPEED_DIVIDER = 1.0;
	private final int INIT_DELAY = 800;
	private Timeline timeline;

	// Society screens
	private List<SocietyController> societyControllers;
	@FXML
	private HBox screens;
	
	// Buttons and controls
	@FXML
	private HBox buttons;
	@FXML
	private Button btnPlay;
	@FXML
	private Button btnPause;
	@FXML
	private Button btnSpeedUp;
	@FXML
	private Button btnSlowDown;
	@FXML
	private Button btnNextFrame;
	@FXML
	private Slider speedSlider;
	@FXML
	private Button btnToggleScreen;

	public Controller() {
		// A controller must have a public no-args constructor.
	}
	@FXML
	private void initialize() throws IOException {
		societyControllers = new ArrayList<>();
		initSpeedSlider();
		setUpAnimation();
		addSocietyScreen();
		buttonsPaused();
	}

	// Add/Remove Society Screens
	private void addSocietyScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOUCE_SCREEN));
		loader.setResources(CellSocietyApp.getResourceBundle());
		VBox vBox = (VBox) loader.load();
		SocietyController societyController = loader.getController();
		screens.getChildren().add(vBox);
		societyControllers.add(societyController);
	}
	private void removeScreen() {
		// TODO: The current implementation assumes for at most two society screens.
		screens.getChildren().remove(1);
		societyControllers.remove(1);
	}
	@FXML
	private void toggleScreen() throws IOException {
		ObservableList<String> styleClasses = btnToggleScreen.getStyleClass();
		if (styleClasses.contains("btn-toggle-add")) {
			addSocietyScreen();
			styleClasses.remove("btn-toggle-add");
			styleClasses.add("btn-toggle-remove");
		} else {
			removeScreen();
			styleClasses.remove("btn-toggle-remove");
			styleClasses.add("btn-toggle-add");
		}	
	}
	
	// Animation
	private void setUpAnimation() {
		KeyFrame frame = new KeyFrame(
				Duration.millis(INIT_DELAY), 
				e -> update()
			);
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}
	private void update() {
		for (SocietyController societyController : societyControllers) {
			societyController.update();
		}
	}

	// Button logics
	private void initSpeedSlider() {
		speedSlider.setMin(Math.log(MIN_SPEED_DIVIDER));
		speedSlider.setMax(Math.log(MAX_SPEED_DIVIDER));
		speedSlider.setValue(Math.log(INIT_SPEED_DIVIDER));
		speedSlider.valueProperty().addListener(e -> changeSpeed());
	}
	@FXML
	private void startSimulation() {
		timeline.play();
		buttonsPlaying();
	}
	@FXML
	private void pauseSimulation() {
		timeline.pause();
		buttonsPaused();
	}
	private void changeSpeed() {
		boolean isPlaying = (timeline.getStatus() == Animation.Status.RUNNING);
		KeyFrame frame = new KeyFrame(
				Duration.millis(INIT_DELAY / Math.exp(speedSlider.getValue())),
				e -> update()
			);
		timeline.stop();
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(frame);
		if (isPlaying) { timeline.play(); }
	}
	@FXML
	private void nextFrame() {
		timeline.pause();
		update();
		buttonsPaused();
	}
	private void setButton(Button button, boolean enabled) {
		button.setDisable(!enabled);
	}
	/**
	 * Set the buttons status when simulation is playing
	 */
	private void buttonsPlaying() {
		setButton(btnPlay, false);
		setButton(btnPause, true);
		setButton(btnNextFrame, true);
	}
	/**
	 * Set the buttons status when simulation is paused (or not started)
	 * but already loaded.
	 */
	private void buttonsPaused() {
		setButton(btnPlay, true);
		setButton(btnPause, false);
		setButton(btnNextFrame, true);
	}


}
