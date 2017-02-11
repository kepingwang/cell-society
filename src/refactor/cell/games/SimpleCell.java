package refactor.cell.games;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import refactor.cell.Cell;

public abstract class SimpleCell extends Cell {
	private Color[] colors;
	private String cellShapeType;
	protected List<Double> params;
	protected int currState;
	protected int nextState;
	Shape shape = null;

	public SimpleCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		this.cellShapeType = cellShapeType;
		this.colors = colors;
		this.params = params;
		currState = state;
		viewSynchronized = false;
	}

	@Override
	public abstract void updateNextState();

	@Override
	public void syncState() {
		viewSynchronized = (currState == nextState);
		currState = nextState;
	}
	
	@Override
	public void syncView(int i, int j, double cw, double ch) {
		if (viewSynchronized) { return; }
		if (shape == null) {
			shape = genPolygon(cellShapeType, i, j, cw, ch);
			getPaneChildren().clear();
			getPaneChildren().add(shape);
		}
		shape.setFill(colors[currState]);
		viewSynchronized = true;
	}

	@Override
	public String toString() {
		return String.format("Cell(curr:%d, next:%d)", currState, nextState);
	}
	
}
