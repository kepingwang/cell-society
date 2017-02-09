package core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class Ant {
	private static final int NUM_OF_NEIGHBORS = 8;
	private ForagingAntCell currentCell;
	private boolean hasFood;
	private int direction;
	private Random antRNG = new Random();
	
	/**
	 * Constructor for ant
	 * Requires the cell it is born in
	 * Generates a random direction based off it's neighbors
	 * @param cellIn
	 */
	public Ant(ForagingAntCell cellIn){
		currentCell = cellIn;
		direction = antRNG.nextInt(NUM_OF_NEIGHBORS);
	}
	
	/**
	 * checks surroundings for next cell to move towards
	 * returns 1 if ant successfully moves
	 * returns 0 if ant remains in same cell
	 * @param neighbors
	 * @return
	 */
	public int forage(List<Cell> neighborsIn){
		ArrayList<ForagingAntCell> neighbors = new ArrayList<ForagingAntCell>();
		for(Cell c : neighborsIn){
			neighbors.add((ForagingAntCell) c);
		}
		if(this.hasFood){
			return this.returnHome(neighbors);
		}
		return this.findFood(neighbors);
	}
	
	private int returnHome(ArrayList<ForagingAntCell> neighbors){
		if(currentCell.isFood()){
			TreeSet<ForagingAntCell> tempTree = new TreeSet<ForagingAntCell>(new HomeScentComp());
			direction = 
		}
		return 1;
	}
	
	private int findFood(ArrayList<ForagingAntCell> neighbors){
		
		return 1;
	}
}

class FoodScentComp implements Comparator<ForagingAntCell>{

	@Override
	public int compare(ForagingAntCell cell1, ForagingAntCell cell2) {
		if(cell1.getFoodScent() > cell2.getFoodScent()){
			return 1;
		}
		return -1;
	}
}

class HomeScentComp implements Comparator<ForagingAntCell>{

	@Override
	public int compare(ForagingAntCell cell1, ForagingAntCell cell2) {
		// TODO Auto-generated method stub
		if(cell1.getHomeScent() > cell2.getHomeScent()){
			return 1;
		}
		return -1;
	}
	
}
