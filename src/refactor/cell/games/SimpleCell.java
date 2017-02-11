package refactor.cell.games;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import refactor.cell.Cell;

/**
 * For now this is the GameOfLife. But we can create a super class
 * for all simulations that uses only simple states (int) not 
 * custom objects (like ants).
 * 
 * @author keping
 *
 */
public class SimpleCell extends Cell {
	private final static int UNDER_POP_LIMIT = 2;
	private final static int OVER_POP_LIMIT = 3;
	private final static int SPAWN_REQUIREMENT = 3;
	private final static int DEAD = 0;
	private final static int ALIVE = 1;
	private int currState;
	private int nextState;
	private String cellShapeType;

	public SimpleCell(String cellShapeType, int state) {
		this.cellShapeType = cellShapeType;
		currState = state;
		viewSynchronized = false;
	}
	
	@Override
	public void onClick() {
		debugFlag = true; 
		// This method is optional.
	}

	@Override
	public void updateNextState() {
		@SuppressWarnings("unchecked")
		List<SimpleCell> neighbors = (List<SimpleCell>) getNeighbors();
		int numLiving = 0;
		for (SimpleCell nb : neighbors) {
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
		
		viewSynchronized = (currState == nextState);
	}

	@Override
	public void syncState() {
		currState = nextState;
	}
	
	
	@Override
	public void syncView(int i, int j, double cw, double ch) {
		if (viewSynchronized) { return; }
		Shape shape = genPolygon(cellShapeType, i, j, cw, ch);
		if (currState == ALIVE) { shape.setFill(Color.BLACK); }
		else { shape.setFill(Color.ALICEBLUE); }
		getPaneChildren().clear();
		getPaneChildren().add(shape);
		viewSynchronized = true;
	}

}
