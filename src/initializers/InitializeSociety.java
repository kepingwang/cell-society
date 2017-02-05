package initializers;

import core.Cell;
import core.Society;
import core.rules.Rule;
import gui.SceneController;

public abstract class InitializeSociety {
	protected final double WIDTH = SceneController.WIDTH;
	protected final double HEIGHT = SceneController.HEIGHT - 100;
	protected double cellWidth;
	protected double cellHeight;
	
	protected void calculateCellSize(int rows, int cols){
		cellWidth = WIDTH/cols;
		cellHeight = HEIGHT/rows;
	}
	
	public abstract Society getSociety();
	
	protected abstract Rule createRule();
	
	protected abstract Cell[][] generateCells();
}
