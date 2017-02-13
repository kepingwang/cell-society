package gui;

import java.util.ResourceBundle;

import core.Society;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.SocietyXMLParser;

public class SocietyScreen extends Application {
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final double WIDTH = 600;
	private static final double HEIGHT = 600;
    private ResourceBundle myResources;
    private String language = "English";

	private final int MILLISECOND_DELAY = 800;
	private int delay;
	private Scene scene;
	private Timeline timeline;

	private TextField configField;
	private TextField saveField;

	private Button buttonSave;
	private Button buttonPlay;
	private Button buttonPause;
	private Button buttonSpeedUp;
	private Button buttonSlowDown;
	private Button buttonNextFrame;

	/**
	 * The Society Group.
	 */
	private Society society;
	/**
	 * Where Society resides.
	 */
	private Group screen;

	/**
	 * Create the scene for simulator
	 */
	private void setUpScene() {
		Group root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		VBox container = new VBox();
		root.getChildren().add(container);
		HBox configBox = new HBox();
		HBox transitionHBox = new HBox();
		HBox timeline1HBox = new HBox();
		HBox timeline2HBox = new HBox();
		container.getChildren().add(configBox);
		container.getChildren().add(transitionHBox);
		container.getChildren().add(timeline1HBox);
		container.getChildren().add(timeline2HBox);

		Button buttonLoad = makeButton("LoadConfig", true, e -> loadConfig());
		configField = new TextField("fire1.xml");
		configBox.getChildren().addAll(buttonLoad, configField);

		buttonSave = makeButton("SaveXML", false, e -> saveConfig());
		saveField = new TextField("saved-cell-society.xml");
		transitionHBox.getChildren().addAll(buttonSave, saveField);

		buttonPlay = makeButton("Play", false, e -> startSimulation());
		buttonPause = makeButton("Pause", false, e -> pauseSimulation());
		timeline1HBox.getChildren().addAll(buttonPlay, buttonPause);
		
		buttonSpeedUp = makeButton("SpeedUp", false, e -> fastForward());
		buttonSlowDown = makeButton("SlowDown", false, e -> slowForward());
		buttonNextFrame = makeButton("NextFrame", false, e -> nextFrame());
		timeline2HBox.getChildren().addAll(buttonSpeedUp, buttonSlowDown, buttonNextFrame);

		screen = new Group();
		container.getChildren().add(screen);
	}

	// view building helpers
	private Button makeButton(String nameKey, boolean enabled, EventHandler<ActionEvent> handler){
		Button btn = new Button(getResourceString(nameKey));
		btn.setOnAction(handler);
		btn.setDisable(!enabled);
		return btn;
	}
	private void setButton(Button button, boolean enabled) {
		button.setDisable(!enabled);
	}
	
	// animation
	private void setUpAnimation() {
		delay = MILLISECOND_DELAY;
		KeyFrame frame = new KeyFrame(
				Duration.millis(delay), 
				e -> society.update()
			);
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}
	
	// Button logics
	public void startSimulation() {
		timeline.play();
		buttonsPlaying();
	}
	private void pauseSimulation() {
		timeline.pause();
		buttonsPaused();
	}
	private void changeSpeed(double factor, boolean goOnAndPlay) {
		if (delay / factor >= 100 && delay / factor <= 2000) {
			delay = (int) (delay / factor);
		}
		KeyFrame frame = new KeyFrame(
				Duration.millis(delay),
				e -> society.update()
			);
		timeline.stop();
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(frame);
		if (goOnAndPlay) { timeline.play(); }
	}
	private void fastForward() {
		changeSpeed(1.5, isPlaying());
	}
	private void slowForward() {
		changeSpeed(0.667, isPlaying());
	}
	private void nextFrame() {
		timeline.pause();
		society.update();
		buttonsPaused();
	}
	/**
	 * Set the buttons status when simulation is playing
	 */
	private void buttonsPlaying() {
		setButton(buttonSave, true);
		setButton(buttonPlay, false);
		setButton(buttonPause, true);
		setButton(buttonSpeedUp, true);
		setButton(buttonSlowDown, true);
		setButton(buttonNextFrame, true);
	}
	/**
	 * Set the buttons status when simulation is paused (or not started)
	 * but already loaded.
	 */
	private void buttonsPaused() {
		setButton(buttonSave, true);
		setButton(buttonPlay, true);
		setButton(buttonPause, false);
		setButton(buttonSpeedUp, true);
		setButton(buttonSlowDown, true);
		setButton(buttonNextFrame, true);
	}
	private boolean isPlaying() {
		return buttonPlay.isDisabled();
	}
	
	
	private void setSociety(Society society) {
		this.society = society;
		screen.getChildren().clear();
		screen.getChildren().add(society);
		timeline.pause();
		buttonsPaused();
	}
	private void loadConfig() {
		loadConfig(configField.getText());
	}
	private void loadConfig(String configFile) {
		SocietyXMLParser parser = new SocietyXMLParser();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveConfig() {
		try {
			new SocietyXMLParser().saveAsXML(society, "data/"+saveField.getText(), "default-id");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private String getResourceString(String key) {
		return myResources.getString(key);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		setUpScene();
		setUpAnimation();
		society = null;
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
