package core;

import java.util.List;

import core.rules.Rule;
import javafx.scene.paint.Color;



/**
 * A Cell, also a JavaFX node. Can be visualized when added to JavaFX Group
 * ({@link Society}).
 * @author keping
 */
public class Cell {
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
		// TODO
		this.state = state;
		this.nextState = state;
	}

	public void setState(int i){
		state = i;
	}
	
	public int getState() {
		return state; 
	}
	
	/**
	 * Set Next State
	 * @param i
	 */
	public void setNState(int i){
		nextState = i;
	}
	
	/**
	 * Get Next State
	 * @return
	 */
	public int getNState(){
		return nextState;
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
	public void updateNextState(Rule rule, List<Cell> neighbors) {
		nextState = rule.update(this, neighbors);
	}

	public void syncColor(Color[] color) {
		// TODO Auto-generated method stub
		
	}

	public int width() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

}
