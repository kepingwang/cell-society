package refactor.cell.games;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * For now this is the GameOfLife. But we can create a super class
 * for all simulations that uses only simple states (int) not 
 * custom objects (like ants).
 * 
 * @author keping
 *
 */
public class GameOfLifeCell extends SimpleCell {
	private final static int UNDER_POP_LIMIT = 2;
	private final static int OVER_POP_LIMIT = 3;
	private final static int SPAWN_REQUIREMENT = 3;
	private final static int DEAD = 0;
	private final static int ALIVE = 1;
	private final static int TOTAL_STATES = 2;
	private final static int TOTAL_PARAMS = 0;

	public GameOfLifeCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		if(colors.length != TOTAL_STATES){
			throw new IllegalArgumentException(resourceBundle.getString("ColorError"));
		}
		if(params.size()!=TOTAL_PARAMS ){
			throw new IllegalArgumentException(resourceBundle.getString("ParamError"));
		}
		if(state > TOTAL_STATES - 1 || state < 0){
			throw new IllegalArgumentException(resourceBundle.getString("StateError"));
		}
	}
	
	@Override
	public void updateNextState() {
		@SuppressWarnings("unchecked")
		List<GameOfLifeCell> neighbors = (List<GameOfLifeCell>) getNeighbors();
		int numLiving = 0;
		for (GameOfLifeCell nb : neighbors) {
			if (nb != null && nb.currState == 1) { numLiving++; }
		}
		if(currState == ALIVE && (numLiving < UNDER_POP_LIMIT || numLiving > OVER_POP_LIMIT)){
			nextState = DEAD;
		} else if (currState == DEAD && numLiving == SPAWN_REQUIREMENT){
			nextState = ALIVE;
		} else {
			// Don't assume about the value of nextState,
			// always assign nextState the correct value.
			nextState = currState;
		}
	}

}
