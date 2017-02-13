package refactor.cell.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class WatorCell extends SimpleCell {
	private final static int WATER = 0;
	private final static int FISH = 1;
	private final static int SHARK = 2;
	private final static int TOTAL_STATES = 3;
	private final static int TOTAL_PARAMS = 4;
	
	private final int fishBirth;
	private final int sharkBirth;
	private final int sharkDeath;
	private final int eatEnergy;
	private int energy;
	private Random watorRNG = new Random();
	
	private ArrayList<WatorCell> openSpaces;

	/**
	 * Params required:
	 * fishBirth: positive integer
	 * sharkBirth: positive integer
	 * sharkDeath: negative integer
	 * eatEnergy: positive integer
	 * @param cellShapeType
	 * @param colors
	 * @param params
	 * @param state
	 */
	public WatorCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		if(colors.length != TOTAL_STATES){
			throw new IllegalArgumentException(resourceBundle.getString("ColorError"));
		}
		if(params.size()!=TOTAL_PARAMS ){
			throw new IllegalArgumentException(resourceBundle.getString("ParamError"));
		}
		if(params.get(0) < 0 || params.get(1) < 0 || params.get(2) > 0 || params.get(3) < 0){
			throw new IllegalArgumentException(resourceBundle.getString("WatorParamError"));
		}
		if(state > TOTAL_STATES - 1 || state < 0){
			throw new IllegalArgumentException(resourceBundle.getString("StateError"));
		}
		this.nextState = state;
		fishBirth = params.get(0).intValue();
		sharkBirth = params.get(1).intValue();
		sharkDeath = params.get(2).intValue();
		eatEnergy = params.get(3).intValue();
		energy = 0;
	}

	@SuppressWarnings("unchecked")
	public void updateNextState() {
		if(this.currState == WATER){
			return;
		}
		List<WatorCell> neighbors = (List<WatorCell>) this.getNeighbors();
		ArrayList<WatorCell> openNeighbors = new ArrayList<WatorCell>();
		ArrayList<WatorCell> fishNeighbors = new ArrayList<WatorCell>();
		for(WatorCell n : neighbors){
			if(n.nextState == WATER){
				openNeighbors.add(n);
			}
			else if(n.currState == FISH && n.nextState == FISH){
				fishNeighbors.add(n);
			}
		}
		openSpaces = openNeighbors;
		
		if(this.currState == this.nextState){
			if(this.currState == FISH && this.nextState == FISH){
				updateFish();
			}
			else if(this.currState == SHARK && this.nextState == SHARK){
				updateShark(fishNeighbors);
			}
		}
	}
	
	/**
	 * fish moves if possible
	 */
	private void updateFish(){
		this.energy++;
		if(openSpaces.size() > 0){
			move(fishBirth);
		}
	}
	
	/**
	 * checks if shark can eat a fish
	 * if fish eaten, shark won't move
	 * @param fishSpaces
	 */
	private void updateShark(List<WatorCell> fishSpaces){
		this.energy--;
		if(this.energy <= sharkDeath){
			this.energy = 0;
			this.nextState = WATER;
			return;
		}
		if(fishSpaces.size() > 0){
			WatorCell target = fishSpaces.get(watorRNG.nextInt(fishSpaces.size()));
			this.eat(target);
			openSpaces.add(target);
			if(this.energy >= sharkBirth && openSpaces.size() > 0){
				target = openSpaces.get(watorRNG.nextInt(openSpaces.size()));
				giveBirth(target);
			}
		}
		else if(openSpaces.size() > 0){
			move(sharkBirth);
		}
	}
	
	/**
	 * assumes at least one WatorCell is empty
	 * picks a random empty cell
	 * sets empty cell nextState to this cell
	 * checks if cell can give birth
	 * @param birthEnergy
	 */
	private void move(int birthEnergy){
		WatorCell target = openSpaces.get(watorRNG.nextInt(openSpaces.size()));
		target.nextState = this.currState;
		target.energy = this.energy;
		this.nextState = WATER;
		this.energy = 0;
		if(target.energy >= birthEnergy){
			giveBirth(this);
		}
	}
	
	/**
	 * gives birth to same type of WatorCell
	 * resets energies to default
	 * @param location
	 */
	private void giveBirth(WatorCell location){
		location.nextState = this.currState;
		location.energy = 0;
		this.energy = 0;
	}
	
	/**
	 * this cell is assumed to be a shark
	 * eats given fish by setting it's current state
	 * and next state to WATER so that "eaten" fish can't move
	 * allots energy to what is necessary
	 * @param fish
	 */
	private void eat(WatorCell fish){
		fish.nextState = WATER;
		fish.energy = 0;
		this.energy += eatEnergy;
	}

}
