package cellsociety;

import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {

	public GameOfLifeCell(double x, double y, double height, double width, int stateIn, Rule ruleIn) {
		super(x, y, height, width, stateIn, ruleIn);
	}

	protected void changeAppearanceTo(int state) {
		if (state == 0) { super.setFill(Color.ALICEBLUE); }
		else if (state == 1) { super.setFill(Color.BLACK); }
	}
	
	
}
