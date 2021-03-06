package cell.games;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import cell.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class SimpleCell extends Cell {
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String LANGUAGE = "English";
	
	protected Color[] colors;
	protected String cellShapeType;
	protected List<Double> params;
	protected int currState;
	protected int nextState;
	protected ResourceBundle resourceBundle;
	Shape shape = null;

	public SimpleCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
		this.cellShapeType = cellShapeType;
		this.colors = colors;
		this.params = params;
		currState = state;
		viewSynchronized = false;
	}

	@Override
	public void onClick() {
		debugFlag = true;
		nextState++;
		if (nextState == colors.length) { nextState = 0; }
		currState = nextState;
		shape.setFill(colors[currState]);
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
	public Map<Color, Integer> getColor() {
		Map<Color, Integer> map = new HashMap<>();
		for (int i = 0; i < colors.length; i++) {
			if (i == currState) { map.put(colors[i], 1); }
			else { map.put(colors[i], 0); }
		}
		return map;
	}

	@Override
	public String toString() {
		return String.format("Cell(curr:%d, next:%d)", currState, nextState);
	}
	
}
