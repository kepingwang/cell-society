package cellsociety;

import java.util.List;

import javafx.scene.shape.Shape;

public abstract class Cell extends Shape {
	
	private Rule rule;
	private int state;
	private int nextState;
	
	public Cell() {
		// TODO:  x, y, h, w, state(including initial), rule

	}
	
	public final void update(List<Cell> neighbors) {
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
