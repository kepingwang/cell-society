package cell;

import java.util.List;

import cell.games.FireCell;
import cell.games.ForagingAntCell;
import cell.games.GameOfLifeCell;
import cell.games.SegregationCell;
import cell.games.SlimeCell;
import cell.games.SugarScapeCell;
import cell.games.WatorCell;
import javafx.scene.paint.Color;

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
				return (T) new WatorCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("ants")) {
				return (T) new ForagingAntCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("slime")) {
				return (T) new SlimeCell(cellShapeType, colors, params, state);
			} else if (gameType.equals("sugar")) {
				return (T) new SugarScapeCell(cellShapeType, colors, params, state);
			} else {
				throw new Exception("Invalid game name: "+gameType);
			}
		} catch (RuntimeException re) {
			throw new Exception(re.getMessage());
		}
	}

}
