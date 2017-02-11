package refactor.cell.games;

import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class FireCell extends SimpleCell {
	private final static int EMPTY = 0;
	private final static int TREE = 1;
	private final static int FIRE = 2;
	private double chanceOfFire; 
	private Random fireRNG = new Random();

	public FireCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		chanceOfFire = this.params.get(0);
	}
	
	@Override
	public void updateNextState() {
		if (currState == EMPTY || currState == FIRE) {
			nextState = EMPTY;
		} else {
			nextState = TREE;
			@SuppressWarnings("unchecked")
			List<FireCell> neighbors = (List<FireCell>) getNeighbors();
			for (FireCell nb : neighbors) {
				if (nb != null && nb.currState == FIRE && 
						fireRNG.nextDouble() < chanceOfFire) {
					nextState = FIRE;
					break;
				}
			}
		}
	}
	
}
