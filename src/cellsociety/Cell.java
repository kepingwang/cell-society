package cellsociety;

import javafx.scene.shape.Rectangle;

public abstract class Cell extends Rectangle {
	
	private Rule rule;
	private int state;
	private int nextState;
	
	public Cell(double x, double y, double height, double width, int state, Rule ruleIn) {
		super(x, y, height, width);
	}
	
	public Cell(){
		
	}
	
	public final void update(Cell[] neighbors) {
		nextState = rule.update(this, neighbors);
	}
	
	protected abstract void changeAppearance();
	
	public void syncState() {
		if (state != nextState) {
			changeAppearance();
			state = nextState;
		}
	}
}
