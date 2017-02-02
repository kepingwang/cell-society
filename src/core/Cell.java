package core;

import core.rules.Rule;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A Cell, also a JavaFX node. Can be visualized when added to JavaFX Group
 * ({@link Society}).
 * @author keping
 */
public class Cell extends Rectangle {
	/**
	 * states are integers starting from 0.
	 */
	private int state = -1;
	private int nextState = -1;


	/**
	 * Construct a (rectangle) cell
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param state
	 */
	public Cell(double x, double y, double w, double h, int state) {
		super(x, y, w, h);
		this.state = state;
	}

	public int getState() {
		return state; 
	}
	
	public void setNState(int i){
		nextState = i;
	}
	
	public void syncColor(Color[] colors) {
		setFill(colors[state]);
	}
	public void syncState() {
		if (state != nextState) {
			state = nextState;
		}
	}
	/**
	 * Update nextState and don't change current state.
	 * The neighbors array passed in shall always contain 8 elements.
	 * With "*" representing the current cell, the indices of its neighbors
	 * should be like:
	 * <pre>
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 * </pre>
	 * @param neighbors
	 */
	public void updateNextState(Rule rule, Cell[] neighbors) {
		nextState = rule.update(this, neighbors);
	}
	
	@Override
	public String toString() {
		return String.format("Cell(%.1f, %.1f)%d", getX(), getY(), state);
	}
	
}
