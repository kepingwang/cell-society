package refactor.cell.games;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * TODO: There is still bug with this SlimeCell code. And Code refactoring is
 * also needed.
 * 
 * @author jacob
 *
 */
public class SlimeCell extends SimpleCell {
	private final static int EMPTY = 0;
	private final static int SPORE = 1;
	private final static int cAMP = 2;
	private final static int NUM_STATES = 3;

	private double wiggleBias;
	private double wiggleAngle;
	private double sniffThreshold;
	private double sniffAngle;
	private double cAMPStrength;

	private boolean nextStateUpdated;

	public SlimeCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		if (colors.length != NUM_STATES || params.size() != 5 || state < 0 || state > 2) {
			throw new IllegalArgumentException("Illegal configuration for SlimeCell.");
		}
		wiggleBias = params.get(0);
		wiggleAngle = params.get(1);
		sniffThreshold = params.get(2);
		sniffAngle = params.get(3);
		cAMPStrength = params.get(4);
		this.nextState = state;
	}

	@Override
	public void syncState() {
		viewSynchronized = (currState == nextState);
		currState = nextState;
		nextStateUpdated = false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateNextState() {
		if (nextStateUpdated) {
			return;
		}

		if (this.currState == EMPTY) {
			return;
		}

		ArrayList<SlimeCell> neighborsIn = (ArrayList<SlimeCell>) this.getNeighbors();
		ArrayList<SlimeCell> allNeighbors = new ArrayList<SlimeCell>();
		ArrayList<SlimeCell> sporeNeighbors = new ArrayList<SlimeCell>();
		ArrayList<SlimeCell> cAMPNeighbors = new ArrayList<SlimeCell>();

		for (SlimeCell c : neighborsIn) {
			if (c == null) {
				break;
			}
			allNeighbors.add(c);
			if (c.currState == SPORE) {
				sporeNeighbors.add(c);
			}
			if (c.currState == cAMP) {
				cAMPNeighbors.add(c);
			}
		}

		int sporeSpaces = sporeNeighbors.size();
		int cAMPSpaces = cAMPNeighbors.size();

		if (this.currState == SPORE) {
			if (sporeSpaces > 0) {
				return;
			}
			// check cAMP
			if (cAMPSpaces >= this.sniffThreshold && checkSniffMatch(allNeighbors)) {
				int target = (int) (Math.random() * cAMPSpaces);
				moveToOpen(cAMPNeighbors.get(target));
			} else {
				randomWiggle(allNeighbors);
			}
		} else if (this.currState == cAMP) {
			cAMPStrength--;
			if (cAMPStrength == 0) {
				nextState = EMPTY;
			}
		}

	}

	public void moveToOpen(SlimeCell target) {
		target.nextState = this.currState;
		target.nextStateUpdated = true;
		this.nextState = cAMP;
		cAMPStrength = 2;
	}

	public void randomWiggle(ArrayList<SlimeCell> neighbors) {
		double wiggleBias = this.wiggleBias;
		double wiggleAngle = this.wiggleAngle;
		int rand_x = (int) (Math.random() * 3);
		int rand_y = (int) (Math.random() * 2);

		int targ = -1;

		if (wiggleBias > 0) {
			if (rand_x > 0) {
				if (wiggleAngle >= 0 && wiggleAngle <= 45) {
					if (rand_y > 0) {
						targ = 2;
					} else {
						targ = 7;
					}
				} else {
					targ = 4;
				}
			} else {
				if (wiggleAngle >= 0 && wiggleAngle <= 45) {
					if (rand_y > 0) {
						targ = 0;
					} else {
						targ = 5;
					}
				} else {
					targ = 3;
				}
			}
		} else if (wiggleBias < 0) {
			if (rand_x > 0) {
				if (wiggleAngle >= 0 && wiggleAngle <= 45) {
					if (rand_y > 0) {
						targ = 0;
					} else {
						targ = 5;
					}
				} else {
					targ = 3;
				}
			} else {
				if (wiggleAngle >= 0 && wiggleAngle <= 45) {
					if (rand_y > 0) {
						targ = 2;
					} else {
						targ = 7;
					}
				} else {
					targ = 4;
				}
			}
		} else {
			if (rand_y > 0) {
				targ = 1;
			} else {
				targ = 6;
			}
		}

		if (targ < neighbors.size()) {
			moveToOpen(neighbors.get(targ));
		}

	}

	public boolean checkSniffMatch(ArrayList<SlimeCell> allNeighbors) {
		if (sniffAngle >= 0 && sniffAngle <= 45) {
			for (SlimeCell sc : allNeighbors) {
				if (sc.currState == cAMP) {
					return true;
				}
			}
		} else {
			// TODO
		}
		return false;
	}
}
