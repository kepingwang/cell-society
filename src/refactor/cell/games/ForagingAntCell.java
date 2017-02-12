package refactor.cell.games;

import java.util.ArrayList;
import java.util.List;


import javafx.scene.paint.Color;

public class ForagingAntCell extends SimpleCell{
	public static final int NEUTRAL_CELL = 0;
	public static final int NEST_CELL = 1;
	public static final int FOOD_CELL = 2;
	private static final int INIT_FOOD_AMOUNT = 50;
	private static final double DECAY_RATE = .99;
	private static final int ANT_SPAWN_RATE = 2;
	
	private List<Ant> ants;
	private boolean nestCell;
	private boolean foodCell;
	private double foodScent;
	private double homeScent;
	private int foodAmount;
	
	private ArrayList<ForagingAntCell> neighbors;

	public ForagingAntCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		this.nextState = state;
		nestCell = state == NEST_CELL;
		foodCell = state == FOOD_CELL;
		if(foodCell){
			foodAmount = INIT_FOOD_AMOUNT;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateNextState() {
		this.decayScent();
		neighbors = (ArrayList<ForagingAntCell>) this.getNeighbors();
		if(this.currState == NEST_CELL){
			this.updateNest();
			return;
		}
		if(this.currState == FOOD_CELL){
			this.updateFood();
			return;
		}
		moveAnts();
	}
	
	private void updateNest(){
		moveAnts();
		for(int i = 0; i < ANT_SPAWN_RATE; i++){
			ants.add(new Ant(this));
		}
	}
	
	private void updateFood(){
		moveAnts();
		if(this.foodAmount <= 0){
			this.nextState = NEUTRAL_CELL;
		}
	}
	
	private void moveAnts(){
		ArrayList<Ant> antsToRemove = new ArrayList<Ant>();
		for(Ant a : ants){
			if(a.canMove() && a.forage(neighbors) == 1){
				antsToRemove.add(a);
			}
		}
		ants.removeAll(antsToRemove);
	}
	
	private void decayScent(){
		foodScent = foodScent * DECAY_RATE;
		homeScent = homeScent * DECAY_RATE;
	}

	public void syncState(){
		viewSynchronized = (currState == nextState);
		currState = nextState;
		nestCell = currState == NEST_CELL;
		foodCell = currState == FOOD_CELL;
		for(Ant a : ants){
			a.resetMove();
		}
	}
	
	public boolean isFood(){
		return foodCell;
	}
	
	public boolean isNest(){
		return nestCell;
	}
	
	public void incrementFood(){
		foodAmount++;
	}
	
	public void decrementFood(){
		foodAmount--;
	}
	
	public List<Ant> getAnts(){
		return ants;
	}
	
	public void setAnts(List<Ant> antsIn){
		ants = antsIn;
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
}
