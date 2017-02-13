package refactor.cell.games;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javafx.scene.paint.Color;

public class SugarScapeCell extends SimpleCell {

	private final int sugarGrow;
	private final int vision;
	private final int sugarMetabolism;
	private final int initSugar;
	

	private final int maxSugar;

	private int sugar;
	private SugarAgent agent;

	/**
	 * Params required:
	 * sugarGrow = rate of sugar growth (default should be 1)
	 * vision = vision of agents (1-6)
	 * sugarMetabolism = rate at which agent consumes sugar (1-4)
	 * initSugar = initial sugar given to agents (5-25)
	 * agentSpawn = yes/no to spawn agent (0 or 1)
	 * @param cellShapeType
	 * @param colors
	 * @param params
	 * @param state
	 */
	public SugarScapeCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		sugarGrow = params.get(0).intValue();
		vision = params.get(1).intValue();
		sugarMetabolism = params.get(2).intValue();
		initSugar = params.get(3).intValue();
		if (params.get(4).intValue() == 1) {
			agent = new SugarAgent(this, sugarMetabolism, initSugar);
		}
		maxSugar = state;
		this.nextState = state;
	}

	@Override
	public void updateNextState() {
		if (sugar + sugarGrow >= maxSugar) {
			sugar = maxSugar;
		} else {
			sugar += sugarGrow;
		}
		if (!agent.equals(null) && agent.canAct()) {
			agent.act(getTargetCell());
			agent = null;
		}
		this.nextState = sugar;
	}

	/**
	 * Finds vision# of cells to the top, left, right, and down
	 * Sorts by vision*sugar amount + vision - distance
	 * Prioritizes in finding cell with most sugar
	 * if multiple cells with same sugar, the kicker is the vision - distance
	 * This guarantees closer cells have higher priority in ties
	 * Also guarentees that the agent will move to a different cell
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private SugarScapeCell getTargetCell() {
		TreeMap<Integer, SugarScapeCell> rTree = new TreeMap<Integer, SugarScapeCell>(Collections.reverseOrder());
		SugarScapeCell[] adjCells = (SugarScapeCell[]) this.getNeighbors().toArray();
		for (int i = 0; i < vision; i++) {
			for (SugarScapeCell c : adjCells) {
				if (!c.equals(null) && c.agent.equals(null)) {
					rTree.put(vision - i + c.sugar*vision, c);
				}
			}
			for (int j = 0; j < adjCells.length; j++) {
				if (!adjCells[j].equals(null)) {
					adjCells[j] = (SugarScapeCell) adjCells[j].getNeighbors().get(j);
				}
			}
		}
		return rTree.get(rTree.firstKey());
	}
	
	public void setAgent(SugarAgent agentIn){
		agent = agentIn;
	}
	
	public void removeAgent(){
		agent = null;
	}
	
	public void setSugar(int i){
		sugar = i;
	}
	
	public int getSugar(){
		return sugar;
	}

}
