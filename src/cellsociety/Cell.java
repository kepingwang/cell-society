package cellsociety;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	private Rule rule;
	private int state = Integer.MIN_VALUE;
	private int nextState;

	public Cell(double x, double y, double h, double w, Rule rule) {
		this(x, y, h, w, 0, rule);
	}
	public Cell(double x, double y, double height, double width, int stateIn, Rule ruleIn) {
		// TODO:  x, y, h, w, state(including initial), rule
		super(x, y, height, width);
		nextState = stateIn;
		rule = ruleIn;
		syncState();
	}
	
	public Cell(){
		
	}

	public int getState(){
		return state;
	}
	
	public final void update(Cell[] neighbors) {
		nextState = rule.update(this, neighbors);
	}
	
	protected void changeAppearanceTo(int state) {
		if (state == 0) { super.setFill(Color.ALICEBLUE); }
		else if (state == 1) { super.setFill(Color.BLACK); }
	}
	
	public void syncState() {
		if (state != nextState) {
			changeAppearanceTo(nextState);
			state = nextState;
		}
	}
	
}
