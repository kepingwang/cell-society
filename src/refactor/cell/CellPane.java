package refactor.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import refactor.grid.CellPaneGrid;

/**
 * <p>All child Nodes in the Cell should be added to
 * this pane. The size of the pane shouldn't be directly
 * modified, but should instead be determined by the sizes
 * of its child Nodes. </p>
 * 
 * <p>This pane will be added to the pane of the society
 * (or grid) each with a transformation of (x, y) location
 * based on the position of this cell on the grid.</p>
 * 
 */
public class CellPane<T extends Cell> extends Pane {
	
	private CellPaneGrid<T> grid;

	private int i; // (i, j) position in grid
	private int j;
	private double cw; // cell width 
	private double ch; // cell height
	private T cell;

	/**
	 * Construct the Cell super class and set the (x, y)
	 * @param dx
	 * @param dy
	 */
	public CellPane(CellPaneGrid<T> grid, int i, int j, double cw, double ch, double dx, double dy, T cell) {
		this.grid = grid;
		this.i = i;
		this.j = j;
		this.cw = cw;
		this.ch = ch;
		this.setTranslateX(dx);
		this.setTranslateY(dy);
		this.cell = cell;
		cell.setPane(this);
		syncView();
		this.setOnMouseClicked(e -> this.cell.onClick());
	}
	
	public void updateNextState() {
		cell.updateNextState();
	}
	public void syncState() {
		cell.syncState();
	}
	public void syncView() {
		cell.syncView(i, j, cw, ch);
	}
	public T getCell() {
		return cell;
	}
	public Map<Color, Integer> getColor() {
		return cell.getColor();
	}
	
	public List<T> getNeighbors() {
		List<CellPane<T>> paneList = grid.getNeighbors(i, j);
		List<T> res = new ArrayList<>();
		for (CellPane<T> pane : paneList) {
			if (pane == null) { res.add(null); }
			else { res.add(pane.getCell()); }
		}
		return res;
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
	protected CellPaneGrid<T> getGrid() {
		return grid;
	}

	@Override
	public String toString() {
		return String.format("Pane(%d, %d) %s", i, j);
	}
	
}
