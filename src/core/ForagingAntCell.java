package core;

import java.util.List;

/**
 * Class to represent a cell in Foraging Ant Simulation
 * @author Gordon
 *
 */
public class ForagingAntCell extends Cell{
	public static final int NEUTRAL_CELL = 0;
	public static final int NEST_CELL = 1;
	public static final int FOOD_CELL = 2;
	private static final int INIT_FOOD_AMOUNT = 50;
	private static final double DECAY_RATE = .99;
	
	private List<Ant> occupyingAnts;
	private boolean nestCell;
	private boolean foodCell;
	private double foodScent;
	private double homeScent;
	private int foodAmount;

	/**
	 * General constructor similar to cell constructor
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param state
	 */
	public ForagingAntCell(double x, double y, double w, double h, int state) {
		super(x, y, w, h, state);
		// TODO Auto-generated constructor stub
		nestCell = state == NEST_CELL;
		foodCell = state == FOOD_CELL;
		if(foodCell){
			foodAmount = INIT_FOOD_AMOUNT;
		}
	}
	
	public void syncState(){
		if (this.getState() != this.getNState()) {
			this.setState(this.getNState());
			int temp = this.getState();
			nestCell = temp == NEST_CELL;
			foodCell = temp == FOOD_CELL;
		}
	}
	
	public void decayScent(){
		foodScent = foodScent * DECAY_RATE;
		homeScent = homeScent * DECAY_RATE;
	}
	
	public List<Ant> getAnts(){
		return occupyingAnts;
	}
	
	public void setAnts(List<Ant> antList){
		occupyingAnts = antList;
	}
	
	public int getFood(){
		return foodAmount;
	}
	
	public boolean isNest(){
		return nestCell;
	}
	
	public boolean isFood(){
		return foodCell;
	}
	
	public void setFoodScent(double d){
		foodScent = d;
	}
	
	public double getFoodScent(){
		return foodScent;
	}
	
	public void setHomeScent(double d){
		homeScent = d;
	}
	
	public double getHomeScent(){
		return homeScent;
	}
	
	public void incrementFood(){
		foodAmount++;
	}
	
	public void decrementFood(){
		foodAmount--;
	}

}
