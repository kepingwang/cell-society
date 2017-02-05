package core.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Cell;
import javafx.scene.paint.Color;

/**
 * Rules for Fire simulation
 * @author Gordon
 *
 */

public class FireRule extends Rule{
	private final static Color[] FIRE_COLORS = {Color.BROWN, Color.GREEN, Color.RED};
	private final static int EMPTY = 0;
	private final static int TREE = 1;
	private final static int FIRE = 2;
	
	private final double chanceOfFire;
	private Random fireRNG = new Random();
	
	public FireRule(List<Double> parameters){
		super(FIRE_COLORS, parameters);
		chanceOfFire = parameters.get(0);
	}
	
	public FireRule(List<Double> parameters, double fireChance){
		super(FIRE_COLORS, parameters);
		chanceOfFire = fireChance;
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
	public int update(Cell cell, List<Cell> neighbors) {
		if(cell.getState() == EMPTY || cell.getState() == FIRE){
			return 0;
		}
		
		ArrayList<Cell> neighborsIn = (ArrayList<Cell>) neighbors;
		Cell[] adjNeighbors = {neighborsIn.get(1), neighborsIn.get(3), neighborsIn.get(4), neighbors.get(6)};
		for(Cell c : adjNeighbors){
			if(!c.equals(null) && c.getState() == 2 && fireRNG.nextDouble() < chanceOfFire){
				return FIRE;
			}
		}
		return TREE;
	}
}
