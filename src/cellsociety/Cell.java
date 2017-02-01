package cellsociety;

import javafx.scene.shape.Rectangle;

public abstract class Cell extends Rectangle {
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

	public int getState(){
		return state;
	}

	protected abstract void changeAppearanceTo(int state);
	
	public void syncState() {
		if (state != nextState) {
			changeAppearanceTo(nextState);
			state = nextState;
		}
	}
	
	public final void update(Cell[] neighbors) {
		nextState = rule.update(this, neighbors);
	}
}
