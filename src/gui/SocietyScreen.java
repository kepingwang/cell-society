package gui;

import core.Society;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.SocietyXMLParser;

public class SocietyScreen {
	private final int FRAMES_PER_SECOND = 2;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private SceneController controller;
	private OptionsScreen settings;
	private Scene scene;
	private Timeline timeline;
	private KeyFrame frame;

	private double speedMultiplier;
	private Button buttonMain;
	private Button buttonSettings;
	private Button buttonPlay;
	private Button buttonPause;
	private Button buttonResume;
	private Button buttonFastForward;
	private Button buttonSlowForward;
	private Button buttonAdvanceFrame;
	private TextField textSavedName;
	private Button buttonSave;

	/**
	 * The Society Group.
	 */
	private Society society;

	public Scene getScene() {
		return scene;
	}

	public SocietyScreen(SceneController controller, OptionsScreen settings, Society society) {
		this.controller = controller;
		this.settings = settings;
		this.society = society;
		setUpScene();
		setUpHUD();
		initEventHandlers();
	}

	private void initEventHandlers() {
		buttonMain.setOnMouseClicked(e -> controller.backToMain());
		buttonSettings.setOnMouseClicked(e -> settings.show());
		buttonPlay.setOnMouseClicked(e -> startSimulation());
		buttonPause.setOnMouseClicked(e -> this.pauseSimulation());
		buttonResume.setOnMouseClicked(e -> this.resumeSimulation());
		buttonFastForward.setOnMouseClicked(e -> this.fastForward());
		buttonSlowForward.setOnMouseClicked(e -> this.slowForward());
		buttonAdvanceFrame.setOnMouseClicked(e -> this.advanceFrame());
		buttonSave.setOnMouseClicked(e -> {
			try {
				new SocietyXMLParser().saveAsXML(society, textSavedName.getText(), "default-id");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	/**
	 * Create the scene for simulator
	 */
	// TODO: scene setup
	private void setUpScene() {
		Group root = new Group();
		scene = new Scene(root, SceneController.WIDTH, SceneController.HEIGHT);
		VBox screen = new VBox();
		HBox transitionHBox = new HBox();
		HBox timeline1HBox = new HBox();
		HBox timeline2HBox = new HBox();
		root.getChildren().add(screen);
		screen.getChildren().add(transitionHBox);
		screen.getChildren().add(timeline1HBox);
		screen.getChildren().add(timeline2HBox);
		
		buttonMain = new Button("Back to Main");
		buttonSettings = new Button("Back to Settings");
		textSavedName = new TextField("data/saved-cell-society.xml");
		buttonSave = new Button("Save XML");
		transitionHBox.getChildren().addAll(buttonMain, buttonSettings, textSavedName, buttonSave);
		
		buttonPlay = new Button("PLAY");
		buttonPause = new Button("Pause");
		buttonPause.setDisable(true);
		buttonResume = new Button("Resume");
		buttonResume.setDisable(true);
		timeline1HBox.getChildren().addAll(buttonPlay, buttonPause, buttonResume);
		
		buttonFastForward = new Button("Speed Up");
		buttonFastForward.setDisable(true);
		buttonSlowForward = new Button("Slow Down");
		buttonSlowForward.setDisable(true);
		buttonAdvanceFrame = new Button("Forward Frame");
		buttonAdvanceFrame.setDisable(true);
		timeline2HBox.getChildren().addAll(buttonFastForward, buttonSlowForward, buttonAdvanceFrame);
		
		screen.getChildren().add(society);
	}

	/**
	 * Create the HUD for the simulator
	 */
	// TODO: HUD setup
	private void setUpHUD() {

	}

	public void show() {
		controller.setScene(scene);
	}

	/**
	 * Method to begin simulation Must be called in order to start a timeline
	 */
	public void startSimulation() {
		speedMultiplier = 1;
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
			society.update();
		});
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		buttonPause.setDisable(false);
		buttonFastForward.setDisable(false);
		buttonSlowForward.setDisable(false);
		buttonAdvanceFrame.setDisable(false);
		timeline.play();
	}

	/**
	 * Method to pause simulation
	 */
	private void pauseSimulation() {
		timeline.pause();
		buttonPause.setDisable(true);
		buttonResume.setDisable(false);
	}

	/**
	 * Method to resume simulation
	 */
	private void resumeSimulation() {
		timeline.play();
		buttonPause.setDisable(false);
		buttonResume.setDisable(true);
	}

	/**
	 * Method to fast forward simulation
	 */
	private void fastForward() {
		timeline.stop();
		if (speedMultiplier >= .25) {
			speedMultiplier -= .25;
		}
		timeline.getKeyFrames().clear();
		frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY * speedMultiplier), e -> {
			society.update();
		});
		timeline.getKeyFrames().add(frame);
		timeline.play();
		buttonPause.setDisable(false);
		buttonResume.setDisable(true);
	}

	/**
	 * Method to slow down simulation
	 */
	private void slowForward() {
		timeline.stop();
		speedMultiplier += .25;
		timeline.getKeyFrames().clear();
		frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY * speedMultiplier), e -> {
			society.update();
		});
		timeline.getKeyFrames().add(frame);
		timeline.play();
		buttonPause.setDisable(false);
		buttonResume.setDisable(true);
	}

	/**
	 * Method to step forward a frame, will automatically pause timeline if not
	 * pause already
	 */
	private void advanceFrame() {
		timeline.pause();
		society.update();
	}

}
