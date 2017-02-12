package refactor.control;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import refactor.society.Society;
import refactor.society.SocietyFactory;
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

	@FXML
	private void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			btnChooser.setText(selectedFile.getName());
			addSociety(new SocietyFactory().genSociety(selectedFile.getName()));
		} else {
		}

	}

	public void update() {
		if (society != null) {
			society.update();
			statGraph.update();
		}
	}

}
