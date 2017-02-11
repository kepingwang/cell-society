package refactor.control;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import refactor.society.Society;
import refactor.society.SocietyFactory;

public class SocietyController {
	
	private Society<?> society;
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
    @FXML
    private void chooseFile() {
    	FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);

    	if (selectedFile != null) {
    	    btnChooser.setText(selectedFile.getName());
    	    society = new SocietyFactory().genSociety(selectedFile.getName());
    	    societyBox.getChildren().add(society);
    	} else { }

    }
    
    public void update() {
    	if (society != null) { society.update(); }
    }
    
}
