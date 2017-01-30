package cellsociety;

import java.util.List;

import javafx.scene.shape.Shape;

public abstract class Cell extends Shape {
	
	private Rule rule;
	private int state;
	private int nextState;
	
	public Cell(double x, double y, double height, double width, int state, Rule ruleIn) {
		// TODO:  x, y, h, w, state(including initial), rule
		this.setLayoutX(x);
		this.setLayoutY(y);
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
