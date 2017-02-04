package core;

import core.rules.WaTorRule;

/**
 * Cell for Wa-Tor simulation
 * @author gordon
 *
 */
public class WaTorCell extends Cell {
	private int energy;

	public WaTorCell(double x, double y, double w, double h, int state) {
		super(x, y, w, h, state);
		energy = 0;
	}
	
	public int getEnergy(){
		return energy;
	}
	
	public void setEnergy(int energyIn){
		energy = energyIn;
	}
	
	public void loseEnergy(){
		energy--;
	}
	
	public void getEnergy(int energyIn){
		energy+=energyIn;
	}
	
	/**
	 * One WaTorCell "moving" to open WaTorCell
	 * This assumes that the target cell is an
	 * unoccupied cell
	 * @param target
	 */
	public void moveToOpen(WaTorCell target){
		target.setNState(this.getState());
		target.setEnergy(this.getEnergy());
		this.setNState(WaTorRule.WATER);
		this.setEnergy(0);
	}
	
	/**
	 * Assumes target is known open WaTorCell
	 * Sets target to become new fish/shark
	 * Resets energy to 0 for both WaTorCells
	 * @param target
	 */
	public void giveBirth(WaTorCell target){
		target.setNState(this.getState());
		target.setEnergy(0);
		this.setEnergy(0);
	}

}
