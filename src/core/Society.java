package core;

import java.util.ArrayList;
import java.util.List;

import core.rules.FireRule;
import core.rules.GameOfLifeRule;
import core.rules.Rule;
import core.rules.SegregationRule;
import core.rules.WaTorRule;
import javafx.scene.Group;

/**
 * A JavaFX {@link Group} of Society, containing a 2d array of {@link Cell}s (Nodes).
 * Responsible for the update and visualization of the cells.
 * @author keping, Gordon
 *
 */
public class Society extends Group {
	private static final String GAME_OF_LIFE = "game_of_life";
	private static final String FIRE = "fire";
	private static final String SEGREGATION = "segregation";
	private static final String WATOR = "wator";
	private String gameName;
	private Rule rule;
	private Grid<Cell> cells;
	private List<Cell> segList;

	public Society(String gameName, double width, double height, List<Double> parameters, int[][] layout) {
		super();
		this.gameName = gameName;
		this.cells = generateCells(gameName, width, height, layout);
		rule = generateRule(gameName, parameters);
		for (Cell cell : cells) { getChildren().add(null);} // TOOD
		syncColors();
	}
	
	private Rule generateRule(String gameName, List<Double> parameters){
		Rule retRule = new GameOfLifeRule(parameters);
		if(gameName.equals(GAME_OF_LIFE)){
			retRule = new GameOfLifeRule(parameters);
		}
		else if(gameName.equals(FIRE)){
			retRule = new FireRule(parameters);
		}
		else if(gameName.equals(SEGREGATION)){
			retRule = new SegregationRule(parameters, segList);
		}
		else if(gameName.equals(WATOR)){
			retRule = new WaTorRule(parameters);
		}
		return retRule;
	}
	
	private Grid<Cell> generateCells(String gameName, double width, double height, int[][] layout){
		List<Cell> stateZeroCells = new ArrayList<Cell>();
		int rows = layout.length;
		int cols = layout[0].length;
		double cWidth = width/cols;
		double cHeight = height/rows;
		Cell[][] temp = new Cell[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				Cell tempCell = new Cell(j*cWidth, i*cHeight, cWidth, cHeight, layout[i][j]);
				if(gameName.equals(SEGREGATION) && layout[i][j] == 0){
					stateZeroCells.add(tempCell);
				}
				if(gameName.equals(WATOR)){
					tempCell = new WaTorCell(j*cWidth, i*cHeight, cWidth, cHeight, layout[i][j]);
				}
				temp[i][j] = tempCell;
			}
		}
		segList = stateZeroCells;
		return new Grid<Cell>(temp);
	}
	
	public String getGameName() {
		return gameName;
	}
	public double getWidth() {
		return cells.get(0,0).width() * cells.cols();
	}
	public double getHeight() {
		return cells.get(0,0).height() * cells.rows();
	}
	public int getRows() { 
		return cells.rows();
	}
	public int getCols() {
		return cells.cols();
	}
	public int[][] getLayout() {
		int[][] res = new int[getRows()][getCols()];
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				res[i][j] = cells.get(i, j).getState();
			}
		}
		return res;
	}
	public List<Double> getParams() {
		return rule.getParams();
	}

	private void updateNextStates() {
		for (int i = 0; i < cells.rows(); i++) {
			for (int j = 0; j < cells.cols(); j++) {
				cells.get(i, j).updateNextState(rule, cells.getNeighborsWrap(i, j));
			}
		}
	}
	private void syncStates() {
		for (Cell cell : cells) { cell.syncState(); }
	}
	private void syncColors() {
		for (Cell cell : cells) { cell.syncColor(rule.getColor()); }
	}
	/**
	 * Update the cell states in one round.
	 */
	public void update() {
		updateNextStates();
		syncStates();
		syncColors();
	}

}
