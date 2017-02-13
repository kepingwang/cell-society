package cell.games;

public class SugarAgent {
	private final int sugarMetabolism;
	private SugarScapeCell cell;
	private boolean hasActed;
	private int sugar;
	
	public SugarAgent(SugarScapeCell cellIn, int metabolism, int sugarIn){
		cell = cellIn;
		sugarMetabolism = metabolism;
		sugar = sugarIn;
	}
	
	/**
	 * Moves the agent, assumes the target is already empty
	 * @param target
	 */
	public void act(SugarScapeCell target){
		target.setAgent(this);
		cell = target;
		sugar += cell.getSugar();
		cell.setSugar(0);
		sugar -= sugarMetabolism;
		if(sugar <= 0){
			cell.removeAgent();
		}
		hasActed = true;
	}
	
	public boolean canAct(){
		return !hasActed;
	}
	
	public void resetMove(){
		hasActed = false;
	}
	
}
