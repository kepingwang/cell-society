package core.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Cell;
import core.WaTorCell;
import javafx.scene.paint.Color;

/**
 * Rules for WaTor Simulation
 * @author gordo
 *
 */
public class WaTorRule extends Rule{
	private final static Color[] WATOR_COLORS = {Color.BLUE, Color.GREEN, Color.GOLD};
	private final static int WATER = 0;
	private final static int FISH = 1;
	private final static int SHARK = 2;
	
	private final int fishBirth;
	private final int sharkDeath;
	private final int sharkBirth;
	private final int eatEnergy;
	private Random watorRNG = new Random();
	
	

	public WaTorRule(int fishBirthIn, int sharkDeathIn, int sharkBirthIn, int eatEnergyIn){
		super(WATOR_COLORS);
		fishBirth = fishBirthIn;
		sharkDeath = sharkDeathIn;
		sharkBirth = sharkBirthIn;
		eatEnergy = eatEnergyIn;
	}
	
	@Override
	public int update(Cell cell, List<Cell> neighbors) {
		// TODO Auto-generated method stub
		if(cell.getState() == WATER){
			return cell.getNState();
		}
		ArrayList<Cell> neighborsIn = (ArrayList<Cell>) neighbors;
		Cell[] adjNeighbors = {neighborsIn.get(0), neighborsIn.get(3), neighbors.get(4), neighbors.get(6)};
		
		ArrayList<WaTorCell> openNeighbors = new ArrayList<WaTorCell>();
		ArrayList<WaTorCell> fishNeighbors = new ArrayList<WaTorCell>();
		for(Cell c : adjNeighbors){
			if(c.getNState() == WATER){
				openNeighbors.add((WaTorCell) c);
			}
			else if(c.getState() == FISH && c.getNState() == FISH){
				fishNeighbors.add((WaTorCell) c);
			}
		}
		
		int openSpaces = openNeighbors.size();
		int fishSpaces = fishNeighbors.size();
		WaTorCell watorCell = (WaTorCell) cell;
		if(watorCell.getState() == FISH){
			watorCell.receiveEnergy(1);
			if(openSpaces > 0){
				WaTorCell target = openNeighbors.get(watorRNG.nextInt(openSpaces));
				moveToOpen(watorCell, target);
				WaTorCell old = watorCell;
				watorCell = target;
				if(watorCell.getEnergy() >= fishBirth){
					giveBirth(watorCell, old);
				}
				return old.getNState();
			}
		}
		else if(watorCell.getState() == SHARK){
			watorCell.loseEnergy();
			if(watorCell.getEnergy() <= sharkDeath){
				watorCell.setEnergy(0);
				return WATER;
			}
			if(fishSpaces > 0){
				WaTorCell target = fishNeighbors.get(watorRNG.nextInt(fishSpaces));
				eat(watorCell, target);
				if(watorCell.getEnergy() >= sharkBirth && openSpaces > 0){
					target = openNeighbors.get(watorRNG.nextInt(openSpaces));
					giveBirth(watorCell, target);
				}
				return target.getNState();
			}
			else if(openSpaces > 0){
				WaTorCell target = openNeighbors.get(watorRNG.nextInt(openSpaces));
				moveToOpen(watorCell, target);
				WaTorCell old = watorCell;
				watorCell = target;
				if(watorCell.getEnergy() >= sharkBirth){
					giveBirth(watorCell, old);
				}
				return old.getNState();
			}
		}
		return watorCell.getState();
	}
	
	/**
	 * One WaTorCell "moving" to open WaTorCell
	 * This assumes that the target cell is an
	 * unoccupied cell
	 * @param target
	 */
	public void moveToOpen(WaTorCell current, WaTorCell target){
		target.setNState(current.getState());
		target.setEnergy(current.getEnergy());
		current.setNState(WATER);
		current.setEnergy(0);
	}
	
	/**
	 * Assumes target is known open WaTorCell
	 * Sets target to become new fish/shark
	 * Resets energy to 0 for both WaTorCells
	 * @param target
	 */
	public void giveBirth(WaTorCell parent, WaTorCell target){
		target.setNState(parent.getState());
		target.setEnergy(0);
		parent.setEnergy(0);
	}
	
	public void eat(WaTorCell predator, WaTorCell target){
		target.setState(WATER);
		target.setNState(WATER);
		target.setEnergy(0);
		predator.receiveEnergy(eatEnergy);
	}
}
