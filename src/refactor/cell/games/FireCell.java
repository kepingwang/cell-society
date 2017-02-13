package refactor.cell.games;

import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class FireCell extends SimpleCell {
	private final static int EMPTY = 0;
	private final static int TREE = 1;
	private final static int FIRE = 2;
	private final static int TOTAL_STATES = 3;
	private final static int TOTAL_PARAMS = 1;
	private double chanceOfFire; 
	private Random fireRNG = new Random();

	/**
	 * Params required:
	 * chanceOfFire = chance to catch on fire (double 0-1)
	 * @param cellShapeType
	 * @param colors
	 * @param params
	 * @param state
	 */
	public FireCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		if(colors.length != TOTAL_STATES){
			throw new IllegalArgumentException(resourceBundle.getString("ColorError"));
		}
		if(params.size()!=TOTAL_PARAMS){
			throw new IllegalArgumentException(resourceBundle.getString("ParamError"));
		}
		if(params.get(0) < 0 || params.get(0) > 1){
			throw new IllegalArgumentException(resourceBundle.getString("FireParamError"));
		}
		if(state > TOTAL_STATES - 1 || state < 0){
			throw new IllegalArgumentException(resourceBundle.getString("StateError"));
		}
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
