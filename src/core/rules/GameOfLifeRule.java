package core.rules;

import java.util.List;

import core.Cell;
import javafx.scene.paint.Color;

/**
 * Rules for Game of Life simulation
 * @author Gordon
 *
 */
public class GameOfLifeRule implements Rule {
	private final int UNDER_POP_LIMIT = 2;
	private final int OVER_POP_LIMIT = 3;
	private final int SPAWN_REQUIREMENT = 3;
	
	private final int DEAD = 0;
	private final int ALIVE = 1;
	
	private Color[] colors;
	
	public GameOfLifeRule(Color[] colorIn){
		colors = colorIn;
	}
	
	public GameOfLifeRule(){
		this(new Color[] {Color.WHITE, Color.BLACK});
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

	public Color[] getColor() {
		// TODO Auto-generated method stub
		return colors;
	}

	public Color updateColor(Cell cell) {
		return colors[cell.getState()];
	}

}
