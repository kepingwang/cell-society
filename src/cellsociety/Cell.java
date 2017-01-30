package cellsociety;

import java.util.List;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cell extends Rectangle {
	
	private Rule rule;
	private int state;
	private int nextState;
	
	public int getState(){
		return state;
	}
	
	public Cell(double x, double y, double height, double width, int stateIn, Rule ruleIn) {
		// TODO:  x, y, h, w, state(including initial), rule
		super(x, y, height, width);
		state = stateIn;
		rule = ruleIn;
	}
	
	public Cell(){
		
	}
	
	public final void update(Cell[] neighbors) {
		nextState = rule.update(this, neighbors);
	}
	
//	protected abstract void changeAppearance();
	
	public void syncState() {
		if (state != nextState) {
//			changeAppearance();
			state = nextState;
		}
	}
}
