package refactor.cell;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import refactor.grid.Grid;

public abstract class Cell {
	/**
	 * <p>A cell contains the reference of the grid where 
	 * its resides. <b>The using of this field should 
	 * be avoided!</b></p>
	 */
	@Deprecated
	private Grid<? extends Cell> grid;
	/*
	 * <p>All child Nodes in the Cell should be added to
	 * this pane. The size of the pane shouldn't be directly
	 * modified, but should instead be determined by the sizes
	 * of its child Nodes. </p>
	 * 
	 * <p>This pane will be added to the pane of the society
	 * (or grid) each with a transformation of (x, y) location
	 * based on the position of this cell on the grid.</p>
	 */
	private Pane pane = new Pane();

	/**
	 * Construct the Cell super class and set the (x, y)
	 * @param dx
	 * @param dy
	 */
	protected Cell(Grid<? extends Cell> grid, double dx, double dy) {
		this.grid = grid;
		pane.setTranslateX(dx);
		pane.setTranslateY(dy);
	}
	
	/**
	 * A wrapper around pane.getChildren().
	 * @return List of child {@link Node}s
	 */
	protected List<Node> getChildNodes() {
		return pane.getChildren();
	}
	
	/**
	 * <p>Calling this method should be avoided. Since 
	 * it's cellular automaton. We will never need the whole 
	 * grid to update a cell, only neighbors will be enough.</p>
	 * 
	 * <p>For example, to find an empty cell from the whole 
	 * grid. One method would be keeping an array of all 
	 * the empty cells all the time. A better implementation
	 * would probably be using dfs or bfs to search from neighbors
	 * and that implementation of graph search can be restricted
	 * within the subclass of Cell that needs it.</p>
	 * @return grid
	 */
	@Deprecated
	protected Grid<? extends Cell> getGrid() {
		return grid;
	}
	
	public abstract void syncView();

}
