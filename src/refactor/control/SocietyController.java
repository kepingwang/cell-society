package refactor.control;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import refactor.society.Society;
import refactor.society.SocietyFactory;
import refactor.view.CellSocietyApp;
import refactor.view.StatGraph;

public class SocietyController {

	private Society<?> society;
	private StatGraph statGraph;
	@FXML
	private Button btnChooser;
	@FXML
	private VBox societyBox;

	public SocietyController() {

	}

	@FXML
	private void initialize() {

	}

	public Society<?> getSociety() {
		return society;
	}

	private void addSociety(Society<?> society) {
		if (society != null) {
			societyBox.getChildren().clear();
		}
		this.society = society;
		this.statGraph = new StatGraph(society);
		societyBox.getChildren().addAll(society, statGraph);
		statGraph.update();
	}

	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(CellSocietyApp.getResourceBundle().getString("ErrorTitle"));
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	private void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile == null) { return; }

		try {
			Society<?> society = new SocietyFactory().genSociety(selectedFile.getName());
			btnChooser.setText(selectedFile.getName());
			addSociety(society);
		} catch (Exception e) {
			showError(e.getMessage());
		}
		
	}

	public void update() {
		if (society != null) {
			society.update();
			statGraph.update();
		}
	}

}
