package cell.games;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class SugarScapeCell extends SimpleCell {
	private static final int AGENT_CELL = 5;
	private static final int TOTAL_STATES = 6;
	private static final int TOTAL_PARAMS = 5;

	private final int sugarGrow;
	private final int vision;
	private final int sugarMetabolism;
	private final int initSugar;
	
	private final int maxSugar;

	private int sugar;
	private SugarAgent agent;
	private Shape agentShape;
	private Random sugarRNG = new Random();

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
		if(colors.length != TOTAL_STATES){
			throw new IllegalArgumentException(resourceBundle.getString("ColorError"));
		}
		if(params.size()!=TOTAL_PARAMS ){
			throw new IllegalArgumentException(resourceBundle.getString("ParamError"));
		}
		if(params.get(0) < 0 || params.get(1) < 0 || params.get(2) < 0 || params.get(3) < 0 || params.get(4) < 0 || params.get(4) > 1){
			throw new IllegalArgumentException(resourceBundle.getString("SugarParamError"));
		}
		if(state > TOTAL_STATES - 1 || state < 0){
			throw new IllegalArgumentException(resourceBundle.getString("StateError"));
		}
		sugarGrow = params.get(0).intValue();
		vision = params.get(1).intValue();
		sugarMetabolism = params.get(2).intValue();
		initSugar = params.get(3).intValue();
		if (sugarRNG.nextDouble() < params.get(4)) {
			agent = new SugarAgent(this, sugarMetabolism, initSugar);
		}
		sugar = state;
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
		if (agent!=null && agent.canAct()) {
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
		List<SugarScapeCell> temp = (List<SugarScapeCell>) this.getNeighbors();
		SugarScapeCell[] adjCells = new SugarScapeCell[temp.size()];
		for(int i = 0; i < temp.size(); i++){
			adjCells[i] = temp.get(i);
		}
		for (int i = 0; i < vision; i++) {
			for (SugarScapeCell c : adjCells) {
				if (c!=null && c.agent==null) {
					rTree.put(vision - i + c.sugar*vision, c);
				}
			}
			for (int j = 0; j < adjCells.length; j++) {
				if (adjCells[j]!=null) {
					adjCells[j] = (SugarScapeCell) adjCells[j].getNeighbors().get(j);
				}
			}
		}
		return rTree.get(rTree.firstKey());
	}
	
	@Override
	public void syncView(int i, int j, double cw, double ch) {
		if (viewSynchronized) {
			return;
		}
		if (shape == null) {
			shape = genPolygon(cellShapeType, i, j, cw, ch);
			getPaneChildren().clear();
			getPaneChildren().add(shape);
		}
		shape.setFill(colors[currState]);
		if (agent != null) {
			if (agentShape == null) {
				double diameter = cw;
				if (cw < ch) {
					diameter = ch;
				}
				double radius = diameter / 2;
				agentShape = new Circle(radius, radius, radius);
			}
			if (!getPaneChildren().contains(agentShape)) {
				getPaneChildren().add(agentShape);
				agentShape.setFill(colors[AGENT_CELL]);
			}
		}
		else if(getPaneChildren().contains(agentShape)){
			getPaneChildren().remove(agentShape);
		}
		viewSynchronized = true;
	}
	
	public void syncState() {
		viewSynchronized = false;
		currState = nextState;
		if(agent!=null){
			agent.resetMove();
		}
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
