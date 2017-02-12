package refactor.cell.games;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import refactor.cell.Cell;

/**
 * 
 * @author jacob
 *
 */
public class SlimeCell extends SimpleCell{
	private final static int EMPTY = 0;
	private final static int SPORE = 1;
	private final static int cAMP = 2;
	
	private double wiggleBias;
	private double wiggleAngle;
	private double sniffThreshold;
	private double sniffAngle;
	private double cAMPStrength;

	public SlimeCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		wiggleBias = 0;
		wiggleAngle = 40;
		sniffThreshold = 1.0;
		sniffAngle = 45;
		cAMPStrength=2;
		this.nextState = state;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateNextState() {
		if(this.currState==EMPTY){
			return;
		}
		
		if(this.cAMPStrength <= 0){
			this.nextState=EMPTY;
			return;
		}
		
		ArrayList<SimpleCell> neighborsIn = (ArrayList<SimpleCell>) this.getNeighbors(); 
		ArrayList<SlimeCell> allNeighbors = new ArrayList<SlimeCell>();
		ArrayList<SlimeCell> sporeNeighbors = new ArrayList<SlimeCell>();
		ArrayList<SlimeCell> cAMPNeighbors = new ArrayList<SlimeCell>();
		
		for(SimpleCell c: neighborsIn){
			allNeighbors.add((SlimeCell) c);
			if(c.currState==SPORE){
				sporeNeighbors.add((SlimeCell) c);
			}
			if(c.currState==cAMP){
				cAMPNeighbors.add((SlimeCell) c);
			}
		}
		
		int sporeSpaces = sporeNeighbors.size();
		int cAMPSpaces = cAMPNeighbors.size();
		
		if(this.currState==SPORE){
			if(sporeSpaces>0){
				return;
			}
			//check cAMP
			if(cAMPSpaces>=this.sniffThreshold && checkSniffMatch(allNeighbors)){
				int target = (int)(Math.random()*cAMPSpaces);
				moveToOpen(cAMPNeighbors.get(target));
			}
			else{
				randomWiggle(allNeighbors);
			}
		}
		else if(this.currState==cAMP){
			cAMPStrength--;
		}
		
	}

	public void moveToOpen(SlimeCell target){
		target.nextState = this.currState;
		this.nextState = cAMP;
	}
	
	public void randomWiggle(ArrayList<SlimeCell> neighbors){
		double wiggleBias = this.wiggleBias;
		double wiggleAngle = this.wiggleAngle;
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
		
		moveToOpen(neighbors.get(targ));
		
	}
	
	public boolean checkSniffMatch(ArrayList<SlimeCell> allNeighbors){
		double sniffAngle = this.sniffAngle;
		SlimeCell[] nonAdjacentNeighbors = {allNeighbors.get(0), allNeighbors.get(2), allNeighbors.get(5), allNeighbors.get(7)};
		SlimeCell[] adjacentNeighbors = {allNeighbors.get(1), allNeighbors.get(3), allNeighbors.get(4), allNeighbors.get(6)};
		if(sniffAngle>=0 && sniffAngle<=45){
			for(SlimeCell sc: nonAdjacentNeighbors){
				if(sc.currState==cAMP){
					return true;
				}
			}
		}
		else{
			for(SlimeCell sc: adjacentNeighbors){
				if(sc.currState==cAMP){
					return true;
				}
			}
		}
		return false;
	}
}
