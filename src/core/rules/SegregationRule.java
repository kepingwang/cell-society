package core.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Cell;
import javafx.scene.paint.Color;

/**
 * Rules for Segregation simulator
 * @author Gordon
 *
 */

public class SegregationRule extends Rule{
	private final static Color[] SEG_COLORS = {Color.WHITE, Color.RED, Color.BLUE};
	private final static int EMPTY = 0;
	private final static int SIDE_ONE = 1;
	private final static int SIDE_TWO = 2;
	
	private Random segRNG = new Random();
	private double satisfaction;
	private ArrayList<Cell> openCells;
	
	
	public SegregationRule(double satisfactionIn, List<Cell> openCellsIn){
		super(SEG_COLORS);
		satisfaction = satisfactionIn;
		openCells = (ArrayList<Cell>) openCellsIn;
	}
	
	/**
	 * Checks if cell is already empty, if so does nothing.
	 * Checks non null neighbors for number of same side cells.
	 * Will move if not satisfied with neighbors to random empty cell
	 * otherwise remains the same
	 */
	@Override
	public int update(Cell cell, List<Cell> neighbors) {
		if(cell.getState() == EMPTY){
			return cell.getNState();
		}
		double total = 0;
		double same = 0;
		for(Cell c : neighbors){
			if(!c.equals(null)){
				total++;
				if(c.getState() == cell.getState()){
					same++;
				}
			}
		}
		if(same/total < satisfaction){
			move(cell);
			return EMPTY;
		}
		return cell.getState();
	}
	
	/**
	 * Moves the state of current cell to a known empty cell
	 * Known empty cells are cells that are currently empty and not changing
	 * or cells that will be empty
	 * at end will add cell to openCells assuming next state will be set to empty
	 */
	private void move(Cell cell){
		Cell target = openCells.get(segRNG.nextInt(openCells.size()));
		target.setNState(cell.getState());
		openCells.remove(target);
		openCells.add(cell);
	}
}
