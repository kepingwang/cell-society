package refactor.control;

import javafx.fxml.FXML;

public class Controller {

	public Controller() {
		// A controller must have a public no-args constructor.
	}

    @FXML
    private void initialize(){

    }
	
	@FXML
	public void printOutput() {
		System.out.println("Hallo");
	}
	
	
}
