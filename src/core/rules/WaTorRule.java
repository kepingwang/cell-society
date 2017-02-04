package core.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Cell;
import core.WaTorCell;
import javafx.scene.paint.Color;

public class WaTorRule implements Rule{
	private final int WATER = 0;
	private final int FISH = 1;
	private final int SHARK = 2;
	
	private final int fishBirth;
	private final int sharkDeath;
	private final int sharkBirth;
	private final int eatEnergy;
	private Random watorRNG = new Random();
	
	
	private Color[] colors;
	
	
	public WaTorRule(Color[] colorsIn, int fishBirthIn, int sharkDeathIn, int sharkBirthIn, int eatEnergyIn){
		colors = colorsIn;
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
			watorCell.getEnergy(1);
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
			if(fishSpaces > 0){
				WaTorCell target = fishNeighbors.get(watorRNG.nextInt(fishSpaces));
				eat(watorCell, target);
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
		predator.getEnergy(eatEnergy);
		
	}

	public Color[] getColor() {
		// TODO Auto-generated method stub
		return colors;
	}

	public Color updateColor(Cell cell) {
		// TODO Auto-generated method stub
		return colors[cell.getState()];
	}

}
