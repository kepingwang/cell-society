package cell.games;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ForagingAntCell extends SimpleCell {
	public static final int NEUTRAL_CELL = 0;
	public static final int NEST_CELL = 1;
	public static final int FOOD_CELL = 2;
	public static final int ANT_CELL = 3;
	private static final int INIT_FOOD_AMOUNT = 50;
	private static final double DECAY_RATE = .99;
	private static final int ANT_SPAWN_RATE = 2;
	private static final int TOTAL_STATES = 4;
	private static final int TOTAL_PARAMS = 0;

	private List<Ant> ants;
	private boolean nestCell;
	private boolean foodCell;
	private double foodScent;
	private double homeScent;
	private int foodAmount;
	private Shape antShape;

	private ArrayList<ForagingAntCell> neighbors;

	public ForagingAntCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
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
		this.nextState = state;
		nestCell = state == NEST_CELL;
		foodCell = state == FOOD_CELL;
		if (foodCell) {
			foodAmount = INIT_FOOD_AMOUNT;
		}
		ants = new ArrayList<Ant>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateNextState() {
		neighbors = (ArrayList<ForagingAntCell>) this.getNeighbors();
		this.decayScent();
		if (this.currState == NEST_CELL) {
			this.updateNest();
			return;
		}
		if (this.currState == FOOD_CELL) {
			this.updateFood();
			return;
		}
		moveAnts();
	}

	@Override
	public void syncView(int i, int j, double cw, double ch) {
		if (viewSynchronized) {
			return;
		}
		if (shape == null) {
			shape = genPolygon(cellShapeType, i, j, cw, ch);
			getPaneChildren().clear();
			getPaneChildren().add(shape);
		}
		shape.setFill(colors[currState]);
		if (ants.size() > 0) {
			if (antShape == null) {
				double diameter = cw;
				if (cw < ch) {
					diameter = ch;
				}
				double radius = diameter / 2;
				antShape = new Circle(i + radius, j + radius, radius);
			}
			if (!getPaneChildren().contains(antShape)) {
				getPaneChildren().add(antShape);
				antShape.setFill(colors[ANT_CELL]);
			}
		}
		else if(getPaneChildren().contains(antShape)){
			getPaneChildren().remove(antShape);
		}
		viewSynchronized = true;
	}

	private void updateNest() {
		for (int i = 0; i < ANT_SPAWN_RATE; i++) {
			ants.add(new Ant(this));
		}
		moveAnts();
	}

	private void updateFood() {
		moveAnts();
		if (this.foodAmount <= 0) {
			this.nextState = NEUTRAL_CELL;
		}
	}

	private void moveAnts() {
		ArrayList<Ant> antsToRemove = new ArrayList<Ant>();
		System.out.println(ants.size());
		for (Ant a : ants) {
			if (a.canMove() && a.forage(neighbors) == 1) {
				antsToRemove.add(a);
			}
		}
		ants.removeAll(antsToRemove);
	}

	private void decayScent() {
		foodScent = foodScent * DECAY_RATE;
		homeScent = homeScent * DECAY_RATE;
	}

	public void syncState() {
		viewSynchronized = false;
		currState = nextState;
		nestCell = currState == NEST_CELL;
		foodCell = currState == FOOD_CELL;
		for (Ant a : ants) {
			a.resetMove();
		}
	}

	public void addAnt(Ant a) {
		ants.add(a);
	}

	public boolean isFood() {
		return foodCell;
	}

	public boolean isNest() {
		return nestCell;
	}

	public void incrementFood() {
		foodAmount++;
	}

	public void decrementFood() {
		foodAmount--;
	}

	public List<Ant> getAnts() {
		return ants;
	}

	public void setAnts(List<Ant> antsIn) {
		ants = antsIn;
	}

	public void setFoodScent(double d) {
		foodScent = d;
	}

	public double getFoodScent() {
		return foodScent;
	}

	public void setHomeScent(double d) {
		homeScent = d;
	}

	public double getHomeScent() {
		return homeScent;
	}
}
