package refactor.cell;

import java.util.List;

import javafx.scene.paint.Color;
import refactor.cell.games.FireCell;
import refactor.cell.games.GameOfLifeCell;
import refactor.cell.games.SegregationCell;

public class CellFactory {

	public CellFactory() {

	}
	
	@SuppressWarnings("unchecked")
	public <T extends Cell> T genCell(String gameType, String cellShapeType,
			Color[] colors, List<Double> params, int state) throws Exception {
		try {
			if (gameType.equals("game-of-life")) {
				return (T) new GameOfLifeCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("fire")) {
				return (T) new FireCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("segregation")) {
				return (T) new SegregationCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("wator")) {  // TODO: change to specific cells
				return (T) new SegregationCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("ants")) {
				return (T) new SegregationCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("slime")) {
				return (T) new SegregationCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("sugar")) {
				return (T) new SegregationCell(cellShapeType, colors, params, state);
			} else {
				throw new Exception("Invalid game name: "+gameType);
			}
		} catch (RuntimeException re) {
			throw new Exception(re.getMessage());
		}
	}

}
