package gui;

import core.Society;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.SocietyXMLParser;

public class SocietyScreen {
	private final int FRAMES_PER_SECOND = 2;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private App controller;
	private SettingsScreen settings;
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

	public SocietyScreen(App controller, SettingsScreen settings, Society society) {
		this.controller = controller;
		this.settings = settings;
		this.society = society;
		setUpScene();
		setUpHUD();
		initEventHandlers();
	}

	private void initEventHandlers() {
		// TODO: button logics. Some cannot be pressed while not playing. eg. forward.
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
		scene = new Scene(root, App.WIDTH, App.HEIGHT);
		VBox vBox = new VBox();
		root.getChildren().add(vBox);
		buttonMain = new Button("Back to Main");
		buttonSettings = new Button("Back to Settings");
		buttonPlay = new Button("PLAY");
		buttonPause = new Button("Pause");
		buttonPause.setDisable(true);
		buttonResume = new Button("Resume");
		buttonResume.setDisable(true);
		buttonFastForward = new Button("Speed Up");
		buttonSlowForward = new Button("Slow Down");
		buttonAdvanceFrame = new Button("Forward Frame");
		vBox.getChildren().addAll(buttonMain, buttonSettings, buttonPlay, buttonPause, buttonResume, buttonFastForward,
				buttonSlowForward, buttonAdvanceFrame);
		textSavedName = new TextField("data/saved-cell-society.xml");
		buttonSave = new Button("Save XML");
		vBox.getChildren().addAll(textSavedName, buttonSave);
		vBox.getChildren().add(society);
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
		buttonResume.setDisable(false);
		timeline.play();
	}

	/**
	 * Method to pause simulation
	 */
	private void pauseSimulation() {
		timeline.pause();
	}

	/**
	 * Method to resume simulation
	 */
	private void resumeSimulation() {
		timeline.play();
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
