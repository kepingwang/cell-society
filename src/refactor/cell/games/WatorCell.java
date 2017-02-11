package refactor.cell.games;

import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import refactor.cell.Cell;

public class WatorCell extends SimpleCell {
	private final static int WATER = 0;
	private final static int FISH = 1;
	private final static int SHARK = 2;
	
	private final int fishBirth;
	private final int sharkBirth;
	private final int sharkDeath;
	private int energy;
	private Random watorRNG = new Random();

	public WatorCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		this.nextState = state;
		fishBirth = params.get(0).intValue();
		sharkBirth = params.get(1).intValue();
		sharkDeath = params.get(2).intValue();
		energy = 0;
	}

	@SuppressWarnings("unchecked")
	public void updateNextState() {
		// TODO Auto-generated method stub
		if(this.currState == WATER){
			return;
		}
		List<WatorCell> neighbors = (List<WatorCell>) this.getNeighbors();
		if(this.currState == this.nextState){
			if(this.currState == FISH){
				updateFish();
			}
			else if(this.currState == SHARK){
				updateShark();
			}
		}
	}
	
	private void updateFish(){
		
	}
	
	private void updateShark(){
		
	}

}
