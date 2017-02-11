package refactor.cell.games;

import java.util.HashSet;
import java.util.List;

import javafx.scene.paint.Color;

public class SegregationCell extends SimpleCell {
	private final static int EMPTY = 0;
	private boolean settled = false; // true if next state already determined
	private double satisfaction;
	
	public SegregationCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		satisfaction = params.get(0);
	}
	
	@SuppressWarnings("unchecked")
	private SegregationCell findCellWithState(HashSet<SegregationCell> visited, SegregationCell cell, int state) {
		if (cell == null || visited.contains(cell) || cell.settled) { return null; }
		if (cell.currState == state) { return cell; }
		visited.add(cell);
		for (SegregationCell nb : (List<SegregationCell>) cell.getNeighbors()) {
			SegregationCell res = findCellWithState(visited, nb, state);
			if (res != null) { return res; }
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private SegregationCell findCellWithState(int state) {
		HashSet<SegregationCell> visited = new HashSet<>();
		for (SegregationCell nb : (List<SegregationCell>) this.getNeighbors()) {
			SegregationCell res = findCellWithState(visited, nb, state);
			if (res != null) { return res; }
		}
		return null;
	}

	private void move() {
		SegregationCell target = findCellWithState(EMPTY);
		if (target != null) {
			target.nextState = currState;
			nextState = EMPTY;
			target.settled = true;
		} else {
			nextState = currState;
		}
	}

	@Override
	public void updateNextState() {
		if (settled) { return; }
		if (currState == EMPTY) {
			nextState = currState;
			return;
		}
		int nbTotal = 0;
		int nbSame = 0;
		@SuppressWarnings("unchecked")
		List<SegregationCell> neighbors = (List<SegregationCell>) getNeighbors();
		for (SegregationCell nb : neighbors) {
			if (nb != null) {
				nbTotal++;
				if (currState == nb.currState) {
					nbSame++;
				}
			}
		}
		
		if (nbSame / (double) nbTotal < satisfaction) {
			move();
		} else {
			nextState = currState;
		}
	}

	@Override
	public void syncState() {
		viewSynchronized = (currState == nextState);
		currState = nextState;
		settled = false;
	}

}
