package core.rules;


import java.util.Random;

import core.Cell;
import javafx.scene.paint.Color;

/**
 * Rules for Fire simulation
 * @author Gordon
 *
 */

public class FireRule implements Rule{
	private final double CHANCE_FOR_FIRE = .5;
	private Random fireRNG = new Random();
	private Color[] colors;
	
	// TODO: accept other parameters that affect chance of catching on fire
	public FireRule(Color[] colorIn){
		colors = colorIn;
	}
	
	public FireRule(){
		this(new Color[] {Color.BROWN, Color.GREEN, Color.RED});
	}

	/**
	 * Trees can only catch on fire from trees from adjacent cells
	 * Trees CANNOT catch on fire from diagonal trees
	 * Chances of catching on fire independent of number of trees
	 * i.e. a tree surrounded by trees on fire has a x% chance of
	 * catching on fire for each tree on fire
	 * 
	 * empty cells cannot catch on fire
	 * 
	 * trees on fire only last one cycle
	 * 
	 * @param cell
	 * @param neighbors
	 */
	@Override
	public int update(Cell cell, Cell[] neighbors) {
		if(cell.getState() == 0 || cell.getState() == 2){
			return 0;
		}
		Cell[] adjNeighbors = {neighbors[1], neighbors[3], neighbors[4], neighbors[6]};
		for(Cell c : adjNeighbors){
			if(!c.equals(null) && c.getState() == 2 && fireRNG.nextDouble() < probCatch()){
				return 2;
			}
		}
		return 1;
	}
	
	/**
	 * Calculates chance of catching fire for a tree
	 * depends on initial parameters such as humidity/temperature etc
	 * @return
	 */
	private double probCatch(){
		double retDouble = CHANCE_FOR_FIRE;
		// TODO add in other variables that affect chance of fire
		return retDouble;
	}

	public Color[] getColor() {
		return colors;
	}

	public Color updateColor(Cell cell) {
		return colors[cell.getState()];
	}

}
