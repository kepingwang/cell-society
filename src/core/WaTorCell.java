package core;

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
	
	public void receiveEnergy(int energyIn){
		energy+=energyIn;
	}
	

}
