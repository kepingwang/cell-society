package cell;

import java.util.List;
import java.util.Map;

import cell.games.GameOfLifeCell;
import grid.GridType;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * <p>To add a new cellular automaton game, extend this class.
 * An example is {@link GameOfLifeCell}, which is currently game of life. </p>
 * 
 * <p>A cell contains current state and next state. For SimpleCell,
 * state is just a integer, but they can consist of multiple classes 
 * for more complicated games.</p>
 * 
 * <p>A game cell has to implement the three abstract methods:
 * <ol>
 * 	 <li>{@code updateNextState()}: To get the neighbors, call {@code getNeighbors()}
 * defined in this super class and cast the returned list to a list of 
 * the specific cell subclass. Then calculate the next state and store this
 * next state in the game cell. </li>
 *   <li>{@code syncState()}: Assign the next state to current state.</li>
 *   <li>{@code syncView(int i, int j, double cw, double ch)}: Generate (or draw)
 *   shapes (images are ok too) with the input arguments and add generated shapes
 *   to the cellPane to display. </li>
 *   <li>[optional] {@code onClick()}: Do whatever you want when the 
 *   cell is clicked. The default is to set a debug flag, which helps
 *   debugging.</li>
 * </ol>
 * </p>
 * 
 * <p>The {@code viewSynchronized} boolean flag may be used as in {@code SimpleCell} to
 * reduce unnecessary redrawing of the shapes. Or a shape can be cached (stored) in 
 * a game cell and only its color needs to be changed.</p>
 * 
 * @author keping
 *
 */
public abstract class Cell {
	private CellPane<? extends Cell> cellPane;
	/**
	 * A debug flag. You can put the debugFlag in the 
	 * subclass wherever you need.
	 * <pre>
	 * someMethod() {
	 *     if (debugFlag) {
	 *         // set break point here.
	 *     }
	 *     ...
	 * }
	 * </pre>
	 * For now debugging is triggered by clicking the cell
	 * (of course you have to first be in debug mode), which
	 * is defined in {@link onClick()} method.
	 */
	protected boolean debugFlag = false;
	protected boolean viewSynchronized = false;

	public void setPane(CellPane<? extends Cell> cellPane) {
		this.cellPane = cellPane;
	}
	
	protected final List<?> getNeighbors() {
		return cellPane.getNeighbors();
	}
	protected final ObservableList<Node> getPaneChildren() {
		return cellPane.getChildren();
	}
	
	/**
	 * Here you can define what to do when the cell is clicked.
	 */
	public void onClick() {
		debugFlag = true;
	}
	/**
	 * Call methods in cellPane to get neighbors
	 * and then calculate the next state and remember
	 * this next state.
	 */
	public abstract void updateNextState();
	/**
	 * To be called after updateNextState() has been called
	 * for every Cell. Synchronize current state to nextState.
	 */
	public abstract void syncState();
	/**
	 * <p>Generate shape according to position (i, j), cell size (cw, ch),
	 *  and add the generated shapes to cellPane. 
	 * Call the {@code getPaneChildren()} and modify
	 * the {@code ObservableList<Node>} to control what is displayed.</p> 
	 * <p>(i, j) are used for generating triangles of different orientations.
	 * The code to generate Triangle (Polygon) and Rectangle is already defined
	 * in the {@code Cell} super class.</p>
	 * <p>Transformation is already applied to the CellPane, so when adding shapes
	 * just assume that the top left point of the shape (or its smallest bounding
	 * rectangle) is (0, 0). This is true no matter it's rectangle, triangle, 
	 * or hexagon. If the cell is triangle or rectangle, then the cell width
	 * and cell height refer to the width and height of the smallest bounding rectangle
	 * of the triangle or rectangle.</p>
	 * 
	 * @param i position in grid
	 * @param j position in grid
	 * @param cw cell width
	 * @param ch cell height
	 */
	public abstract void syncView(int i, int j, double cw, double ch);
	/**
	 * Return the current color of the cell, for showing in stat graph.
	 */
	public abstract Map<Color, Integer> getColor();

	private Rectangle rectangle(double cw, double ch) {
		return new Rectangle(0, 0, cw, ch);
	}
	private Polygon triangle(int i, int j, double cw, double ch) {
		boolean pointUp = true;
		if (i % 2 != 0) { pointUp = !pointUp; }
		if (j % 2 != 0) { pointUp = !pointUp; }
		if (pointUp) { return trianglePointUp(cw, ch); }
		else { return trianglePointDown(cw, ch); }
	}
	// Here polygon points are specified clockwise
	private Polygon trianglePointUp(double cw, double ch) {
		return new Polygon(cw/2,0,  cw,ch,  0,ch);
	}
	private Polygon trianglePointDown(double cw, double ch) {
		return new Polygon(0,0,  cw,0,  cw/2,ch);
	}
	private Polygon hexagon(double cw, double ch) {
		double x2 = cw / GridType.HEXAGON_MULTIPLIER;
		double x1 = cw - x2;
		return new Polygon(
				x1, 0,
				x2, 0,
				cw, ch/2,
				x2, ch,
				x1, ch,
				0, ch/2
			);
	}
	protected Shape genPolygon(String gridTypeName, int i, int j, double cw, double ch) {
		if (gridTypeName.equals(GridType.SQUARE)) {
			return rectangle(cw, ch);
		} else if (gridTypeName.equals(GridType.HEXAGON)) {
			return hexagon(cw, ch);
		} else if (gridTypeName.equals(GridType.TRIANGLE)) {
			return triangle(i, j, cw, ch);
		} else {
			return null;
		}
	}
	
}
