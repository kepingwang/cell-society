package core.rules;


import java.util.ArrayList;
import java.util.List;

import core.Cell;
import core.SlimeCell;
import core.WaTorCell;
import javafx.scene.paint.Color;

public class SlimeRule extends Rule {
	private final static Color[] SLIME_COLORS = {Color.BLACK, Color.RED, Color.GREEN};
	private final static int EMPTY = 0;
	private final static int SPORE = 1;
	private final static int cAMP = 2;
	
	
	public SlimeRule(List<Double> parameters){
		super(SLIME_COLORS, parameters);
	}
	
	@Override
	public int update(Cell cell, List<Cell> neighbors){
		if(cell.getState()==EMPTY){
			return EMPTY;
		}
		
		ArrayList<Cell> neighborsIn = (ArrayList<Cell>) neighbors; 
		ArrayList<SlimeCell> allNeighbors = new ArrayList<SlimeCell>();
		ArrayList<SlimeCell> sporeNeighbors = new ArrayList<SlimeCell>();
		ArrayList<SlimeCell> cAMPNeighbors = new ArrayList<SlimeCell>();
		
		for(Cell c: neighborsIn){
			allNeighbors.add((SlimeCell) c);
			if(c.getState()==SPORE){
				sporeNeighbors.add((SlimeCell) c);
			}
			if(c.getState()==cAMP){
				cAMPNeighbors.add((SlimeCell) c);
			}
		}
		
		int sporeSpaces = sporeNeighbors.size();
		int cAMPSpaces = cAMPNeighbors.size();
		
		SlimeCell slimeCell = (SlimeCell) cell;
		
		if(slimeCell.getState()==SPORE){
			if(sporeSpaces>0){
				return SPORE;
			}
			//check cAMP
			if(cAMPSpaces>=slimeCell.getSniffThreshold() && checkSniffMatch(slimeCell, allNeighbors)){
				int target = (int)(Math.random()*cAMPSpaces);
				moveToOpen(slimeCell, cAMPNeighbors.get(target));
			}
			else{
				randomWiggle(slimeCell, allNeighbors);
			}
		}
		else if(slimeCell.getState()==cAMP){
			slimeCell.decay();
		}
		
		return slimeCell.getState();
	}
	
	public void moveToOpen(SlimeCell current, SlimeCell target){
		target.setNState(current.getState());
		current.setNState(cAMP);
	}
	
	public void randomWiggle(SlimeCell current, ArrayList<SlimeCell> neighbors){
		double wiggleBias = current.getWiggleBias();
		double wiggleAngle = current.getWiggleAngle();
		int rand_x = (int)(Math.random()*3);
		int rand_y = (int)(Math.random()*2);
		
		int targ = -1;
		
		if(wiggleBias>0){
			if(rand_x>0){
				if(wiggleAngle>=0 && wiggleAngle<=45){
					if(rand_y>0){
						targ=2;
					}
					else{
						targ=7;
					}
				}
				else{
					targ=4;
				}
			}
			else{
				if(wiggleAngle>=0 && wiggleAngle<=45){
					if(rand_y>0){
						targ=0;
					}
					else{
						targ=5;
					}
				}
				else{
					targ=3;
				}
			}
		}
		else if(wiggleBias<0){
			if(rand_x>0){
				if(wiggleAngle>=0 && wiggleAngle<=45){
					if(rand_y>0){
						targ=0;
					}
					else{
						targ=5;
					}
				}
				else{
					targ=3;
				}
			}
			else{
				if(wiggleAngle>=0 && wiggleAngle<=45){
					if(rand_y>0){
						targ=2;
					}
					else{
						targ=7;
					}
				}
				else{
					targ=4;
				}
			}
		}
		else{
			if(rand_y>0){
				targ=1;
			}
			else{
				targ=6;
			}
		}
		
		moveToOpen(current, neighbors.get(targ));
		
	}
	
	public boolean checkSniffMatch(SlimeCell slimeCell, ArrayList<SlimeCell> allNeighbors){
		double sniffAngle = slimeCell.getSniffAngle();
		SlimeCell[] nonAdjacentNeighbors = {allNeighbors.get(0), allNeighbors.get(2), allNeighbors.get(5), allNeighbors.get(7)};
		SlimeCell[] adjacentNeighbors = {allNeighbors.get(1), allNeighbors.get(3), allNeighbors.get(4), allNeighbors.get(6)};
		if(sniffAngle>=0 && sniffAngle<=45){
			for(SlimeCell sc: nonAdjacentNeighbors){
				if(sc.getState()==cAMP){
					return true;
				}
			}
		}
		else{
			for(SlimeCell sc: adjacentNeighbors){
				if(sc.getState()==cAMP){
					return true;
				}
			}
		}
		return false;
	}
}
