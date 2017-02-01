package core;

import core.rules.Rule;
import core.rules.RuleGenerator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A Cell, also a JavaFX node. Can be visualized when added to JavaFX Group
 * ({@link Society}).
 * @author keping
 */
public class Cell extends Rectangle {
	private String gameName;
	private Rule rule;
	/**
	 * colors array contain the hex color string of the corresponding state.
	 */
	private Color[] colors;
	/**
	 * states are integers starting from 0.
	 */
	private int state = -1;
	private int nextState = -1;

	/**
	 * Construct a cell.
	 * @param gameName
	 * @param colors
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param state
	 */
	public Cell(String gameName, Color[] colors, double x, double y, 
			double w, double h, int state) {
		super(x, y, w, h);
		this.gameName = gameName;
		this.rule = RuleGenerator.genRule(this.gameName);
		this.colors = colors;
		this.state = state;
		syncColor();
	}
	
	public String getGameName() {
		return gameName; 
	}
	public Color[] getColors() {
		Color[] ans = new Color[colors.length];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = colors[i];
		}
		return ans;
	}
	public int getState() {
		return state; 
	}
	
	private void syncColor() {
		setFill(colors[state]);
	}
	public void syncState() {
		if (state != nextState) {
			state = nextState;
			syncColor();
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
	public void updateNextState(Cell[] neighbors) {
		nextState = rule.update(this, neighbors);
	}
	
	@Override
	public String toString() {
		return String.format("Cell(%.1f, %.1f)%d", getX(), getY(), state);
	}
	
}
