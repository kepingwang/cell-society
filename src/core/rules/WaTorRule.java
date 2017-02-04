package core.rules;

import java.util.ArrayList;
import java.util.Random;

import core.Cell;
import core.WaTorCell;
import javafx.scene.paint.Color;

public class WaTorRule implements Rule{
	public static final int WATER = 0;
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
	public int update(Cell cell, Cell[] neighbors) {
		// TODO Auto-generated method stub
		if(cell.getState() == WATER){
			return cell.getNState();
		}
		Cell[] adjNeighbors = {neighbors[1], neighbors[3], neighbors[4], neighbors[6]};
		
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
		WaTorCell watorCell = (WaTorCell) cell;
		watorCell.getEnergy(1);
		if(watorCell.getState() == FISH){
			if(openSpaces > 0){
				WaTorCell target = openNeighbors.get(watorRNG.nextInt(openSpaces));
				watorCell.moveToOpen(target);
				WaTorCell old = watorCell;
				watorCell = target;
				if(watorCell.getEnergy() >= fishBirth){
					watorCell.giveBirth(old);
				}
			}
			
		}
		return 0;
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
