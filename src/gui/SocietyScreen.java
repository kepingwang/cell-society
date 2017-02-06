package gui;

import core.Society;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
	private static final double WIDTH = 600;
	private static final double HEIGHT = 600;
	
	private final int FRAMES_PER_SECOND = 2;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private Scene scene;
	private Timeline timeline;
	private KeyFrame frame;

	private TextField configField;
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
	/**
	 * Where Society resides.
	 */
	private Group screen;

	public Scene getScene() {
		return scene;
	}

	public SocietyScreen() {

	}
	
	private void initEventHandlers() {
		// TODO: button logics. Some cannot be pressed while not playing. eg.
		// forward.
//		buttonMain.setOnMouseClicked(e -> controller.goToMainMenu());
//		buttonSettings.setOnMouseClicked(e -> settings.show());
		buttonPlay.setOnMouseClicked(e -> startSimulation());
		buttonPause.setOnMouseClicked(e -> pauseSimulation());
		buttonResume.setOnMouseClicked(e -> resumeSimulation());
		buttonFastForward.setOnMouseClicked(e -> fastForward());
		buttonSlowForward.setOnMouseClicked(e -> slowForward());
		buttonAdvanceFrame.setOnMouseClicked(e -> advanceFrame());
		buttonSave.setOnMouseClicked(e -> saveConfig());
	}

	/**
	 * Create the scene for simulator
	 */
	// TODO: scene setup
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

		Button buttonLoadConfig = new Button("Load Config");
		buttonLoadConfig.setOnAction(e -> loadConfig());
		configField = new TextField("fire1.xml");
		configBox.getChildren().addAll(buttonLoadConfig, configField);

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

		screen = new Group();
		container.getChildren().add(screen);
	}

	/**
	 * Create the HUD for the simulator
	 */
	private void setUpHUD() {
		// TODO: HUD setup
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

	// TODO: there is still bug with speed up and slow down.
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

	private void setSociety(Society society) {
		this.society = society;
		screen.getChildren().clear();
		screen.getChildren().add(society);
	}

	private void loadConfig() {
		loadConfig(configField.getText());
	}

	private void loadConfig(String configFile) {
		SocietyXMLParser parser = new SocietyXMLParser();
		try {
			setSociety(parser.parse("data/" + configFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveConfig() {
		try {
			new SocietyXMLParser().saveAsXML(society, "data/"+textSavedName.getText(), "default-id");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		setUpScene();
		setUpHUD();
		initEventHandlers();
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
