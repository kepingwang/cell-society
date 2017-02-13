package refactor.cell.games;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;


/**
 * Ant class for Foraging Ants
 * @author Gordon
 *
 */
public class Ant {
	private static final String HOME_STRING = "home";
	private static final String FOOD_STRING = "food";
	private static final double SCENT_CONSTANT = 2;
	private static final double MAX_SCENT = 1000;
	private ForagingAntCell currentCell;
	private boolean hasFood;
	private boolean hasMoved;
	private int direction;
	private Random antRNG = new Random();
	private ArrayList<ForagingAntCell> neighbors;

	/**
	 * Constructor for ant
	 * Requires the cell it is born in
	 * 
	 * @param cellIn
	 */
	public Ant(ForagingAntCell cellIn) {
		currentCell = cellIn;
	}

	/**
	 * checks surroundings for next cell to move towards returns 1 if ant
	 * successfully moves returns 0 if ant remains in same cell
	 * 
	 * @param neighbors
	 * @return
	 */
	public int forage(List<ForagingAntCell> neighborsIn) {
		hasMoved = true;
		neighbors = (ArrayList<ForagingAntCell>) neighborsIn;
		if (this.hasFood) {
			return this.returnHome();
		}
		return this.findFood();
	}

	/**
	 * Algorithm for ant to find home
	 * @return
	 */
	private int returnHome() {
		if (currentCell.isFood()) {
			direction = neighbors.indexOf(optimalMove(HOME_STRING));
		}
		ForagingAntCell target = optimalForwardMove(HOME_STRING);
		if (target.equals(null)) {
			target = optimalMove(HOME_STRING);
		}
		if (!target.equals(null)) {
			placeScent(FOOD_STRING);
			direction = neighbors.indexOf(target);
			if (moveTo(target) == 0) {
				return 0;
			}
			if (currentCell.isNest()) {
				currentCell.incrementFood();
				hasFood = false;
			}
		}
		return 1;
	}

	/**
	 * Algorithm for ant to search for food
	 * @return
	 */
	private int findFood() {
		if(currentCell.isNest()){
			direction = neighbors.indexOf(optimalMove(FOOD_STRING));
		}
		ForagingAntCell target = optimalForwardMove(FOOD_STRING);
		if(target.equals(null)){
			placeScent(HOME_STRING);
			direction = neighbors.indexOf(target);
			if(moveTo(target) == 0){
				return 0;
			}
			if(currentCell.isFood()){
				currentCell.decrementFood();
				hasFood = true;
			}
		}

		return 1;
	}

	/**
	 * Attempts to move ant to next cell If next cell already has too many ants,
	 * nothing happens If next cell has space, this ant will add itself to next
	 * cell list of ants
	 * 
	 * @param next
	 * @return 0 if ant DID NOT move 1 if ant DID move
	 */
	private int moveTo(ForagingAntCell next) {
		if (next.getAnts().size() >= 10) {
			return 0;
		}
		currentCell = next;
		next.getAnts().add(this);
		return 1;
	}

	/**
	 * given a type of scent, ant will
	 * max scent on nest or food source
	 * or give scent less than nearby max source
	 * in order to create a gradient
	 * @param type
	 */
	private void placeScent(String type) {
		if (currentCell.isNest()) {
			currentCell.setHomeScent(MAX_SCENT);
			return;
		} else if (currentCell.isFood()) {
			currentCell.setFoodScent(MAX_SCENT);
			return;
		}
		double maxScent;
		if (type.equals(FOOD_STRING)) {
			maxScent = optimalMove(FOOD_STRING).getFoodScent() - SCENT_CONSTANT;
			if (currentCell.getFoodScent() < maxScent) {
				currentCell.setFoodScent(maxScent);
			}
		} else {
			maxScent = optimalMove(HOME_STRING).getHomeScent() - SCENT_CONSTANT;
			if (currentCell.getHomeScent() < maxScent) {
				currentCell.setHomeScent(maxScent);
			}
		}
	}

	/**
	 * Ant will check all neighbors for max scent to move towards
	 * If all neighbors same amount of scent, will randomly select one
	 * @param type
	 * @return
	 * AntCell with most scent
	 */
	private ForagingAntCell optimalMove(String type) {
		TreeSet<ForagingAntCell> tempTree;
		if (type.equals(HOME_STRING)) {
			tempTree = new TreeSet<ForagingAntCell>(new HomeScentComp());
			tempTree.addAll(neighbors);
			if(tempTree.first().getHomeScent() == tempTree.last().getHomeScent()){
				return neighbors.get(antRNG.nextInt(neighbors.size()));
			}
		} else{
			tempTree = new TreeSet<ForagingAntCell>(new FoodScentComp());
			tempTree.addAll(neighbors);
			if(tempTree.first().getFoodScent() == tempTree.last().getFoodScent()){
				return neighbors.get(antRNG.nextInt(neighbors.size()));
			}
		}
		return tempTree.pollFirst();
	}

	/**
	 * Ant will check all forward positions for max scent to move towards
	 * @param type
	 * @return
	 * 1 of 3 possible AntCell to move to
	 */
	private ForagingAntCell optimalForwardMove(String type) {
		TreeSet<ForagingAntCell> tempTree;
		if (type.equals(HOME_STRING)) {
			tempTree = new TreeSet<ForagingAntCell>(new HomeScentComp());
		} else
			tempTree = new TreeSet<ForagingAntCell>(new FoodScentComp());
		tempTree.add(neighbors.get(direction));
		if (neighbors.size() <= direction + 1) {
			tempTree.add(neighbors.get(0));
		} else
			tempTree.add(neighbors.get(direction + 1));
		if (direction - 1 < 0) {
			tempTree.add(neighbors.get(neighbors.size() - 1));
		} else
			tempTree.add(neighbors.get(direction - 1));
		return tempTree.pollFirst();
	}
	
	public void resetMove(){
		hasMoved = false;
	}
	
	public boolean canMove(){
		return !hasMoved;
	}
}

/**
 * Comparator to find max FoodScent
 * @author Gordon
 *
 */
class FoodScentComp implements Comparator<ForagingAntCell> {

	@Override
	public int compare(ForagingAntCell cell1, ForagingAntCell cell2) {
		if (cell1.getFoodScent() >= cell2.getFoodScent()) {
			return 1;
		}
		return -1;
	}
}

/**
 * Comparator to find max HomeScent
 * @author Gordon
 *
 */
class HomeScentComp implements Comparator<ForagingAntCell> {

	@Override
	public int compare(ForagingAntCell cell1, ForagingAntCell cell2) {
		// TODO Auto-generated method stub
		if (cell1.getHomeScent() >= cell2.getHomeScent()) {
			return 1;
		}
		return -1;
	}

}
