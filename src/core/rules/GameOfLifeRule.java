package core.rules;

import java.util.List;
import core.Cell;
import javafx.scene.paint.Color;

/**
 * Rules for Game of Life simulation
 * @author Gordon
 *
 */
public class GameOfLifeRule extends Rule {
	private final static Color[] GOL_COLORS = {Color.WHITE, Color.BLACK};
	private final static int UNDER_POP_LIMIT = 2;
	private final static int OVER_POP_LIMIT = 3;
	private final static int SPAWN_REQUIREMENT = 3;
	private final static int DEAD = 0;
	private final static int ALIVE = 1;
	
	public GameOfLifeRule(List<Double> parameters){
		super(GOL_COLORS, parameters);
	}

	/**
	 * Cells that are alive will die if there are less than
	 * 2 or more than 3 living cells around it
	 * (overpopulation/underpopulation)
	 * Cells can be born if exactly 3 cells are nearby
	 */
	@Override
	public int update(Cell cell, List<Cell> neighbors) {
		int cellState = cell.getState();
		int numLiving = 0;
		for(Cell c : neighbors){
			if(c != null){
				numLiving += c.getState();
			}
		}
		if(cellState == ALIVE && (numLiving < UNDER_POP_LIMIT || numLiving > OVER_POP_LIMIT)){
			cellState = DEAD;
		}
		else if(cellState == DEAD && numLiving == SPAWN_REQUIREMENT){
			cellState = ALIVE;
		}
		return cellState;
	}
}
