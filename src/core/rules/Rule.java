package core.rules;

import java.util.ArrayList;
import java.util.List;

import core.Cell;
import javafx.scene.paint.Color;

public abstract class Rule {
	private Color[] colors;
	protected List<Double> parameters;
	
	public Rule(Color[] colorIn, List<Double> parametersIn){
		parameters = parametersIn;
		colors = colorIn;
	}
		
	/**
	 * Return the nextState of the cell.
	 * The neighbors array passed in shall always contain 8 elements.
	 * With "*" representing the current cell, the indices of its neighbors
	 * should be like:
	 * <pre>
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 * </pre>
	 * @param cell
	 * @param neighbors
	 * @return nextState
	 */
	public abstract int update(Cell cell, List<Cell> neighbors);
	
	public List<Double> getParams() {
		List<Double> res = new ArrayList<>();
		for (double param : parameters) {
			res.add(param);
		}
		return res;
	}
	public Color[] getColor(){
		return colors;
	}
}
