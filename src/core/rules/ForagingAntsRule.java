package core.rules;

import java.util.ArrayList;
import java.util.List;

import core.Ant;
import core.Cell;
import core.ForagingAntCell;
import javafx.scene.paint.Color;

/**
 * Rules for Foraging Ant simulator
 * @author Gordon
 *
 */
public class ForagingAntsRule extends Rule {
	private final static Color[] FORAGING_ANTS_COLORS = {};
	private final static int ANT_SPAWN_RATE = 2;

	public ForagingAntsRule(List<Double> parametersIn) {
		super(FORAGING_ANTS_COLORS, parametersIn);
	}

	/**
	 * update method called every step to update each cell
	 * must also update all ants within a cell
	 */
	public int update(Cell cell, List<Cell> neighbors) {
		ForagingAntCell antCell = (ForagingAntCell) cell;
		antCell.decayScent();
		if(antCell.getState() == ForagingAntCell.NEST_CELL){
			return this.updateNest(antCell, neighbors);
		}
		if(antCell.getState() == ForagingAntCell.FOOD_CELL){
			return this.updateFood(antCell, neighbors);
		}
		List<Ant> ants = antCell.getAnts();
		moveAnts(ants, neighbors);
		antCell.setAnts(ants);
		return ForagingAntCell.NEUTRAL_CELL;
	}
	
	/**
	 * if cell is a nest then update ants and spawn new ants
	 * @param cell
	 * @param neighbors
	 * @return
	 */
	private int updateNest(ForagingAntCell cell, List<Cell> neighbors){
		List<Ant> ants = cell.getAnts();
		moveAnts(ants, neighbors);
		for(int i = 0; i < ANT_SPAWN_RATE; i++){
			ants.add(new Ant(cell));
		}
		cell.setAnts(ants);
		return ForagingAntCell.FOOD_CELL;
	}
	
	/**
	 * update ants
	 * check if food is depleted or not
	 * @param cell
	 * @param neighbors
	 * @return
	 */
	private int updateFood(ForagingAntCell cell, List<Cell> neighbors){
		List<Ant> ants = cell.getAnts();
		moveAnts(ants, neighbors);
		cell.setAnts(ants);
		if(cell.getFood() <= 0){
			return ForagingAntCell.NEUTRAL_CELL;
		}
		return ForagingAntCell.FOOD_CELL;
	}
	
	/**
	 * given a list of ants and surrounding cells
	 * calls each ant to move if possible
	 * changes the given list if the ant moved
	 * @param ants
	 * @param neighbors
	 */
	private void moveAnts(List<Ant> ants, List<Cell> neighbors){
		ArrayList<Ant> antsToRemove = new ArrayList<Ant>();
		for(Ant a : ants){
			if(a.forage(neighbors) == 1){
				antsToRemove.add(a);
			}
		}
		ants.removeAll(antsToRemove);
	}
}
