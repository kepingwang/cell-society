package refactor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class SquareCellGrid extends CellGrid {

	public SquareCellGrid(Cell[][] arr) {
		super(arr);
		// TODO Auto-generated constructor stub
	}

//	
//	private static final int[][] dps = new int[][] {
//		{-1,-1},{-1, 0},{-1, 1},
//		{ 0,-1},        { 0, 1},
//		{ 1,-1},{ 1, 0},{ 1, 1}
//	};
//	
//	/**
//	 * Return a List of neighbors surrounding target cell. 
//	 * Indices correspond to neighbors as follows:
//	 * <pre>
//	 * 0 1 2
//	 * 3 * 4
//	 * 5 6 7
//	 * </pre>
//	 * If the neighbor's position goes out of boundary, 
//	 * then that element is null in the List.
//	 * 
//	 * @param i
//	 * @param j
//	 * @return
//	 */
//	public List<T> getNeighbors(int i, int j) {
//		List<T> ans = new ArrayList<>();
//		for (int[] dp : dps) {
//			int x = i + dp[0];
//			int y = j + dp[1];
//			if (x < 0 || x >= rows() || y < 0 || y >= cols()) {
//				ans.add(null);
//			} else {
//				ans.add(get(x, y));
//			}
//		}
//		return ans;
//	}
//	
//	/**
//	 * {@link getNeighbors} with wrap around. If wrapping encounters
//	 * itself, then null is added to List. 
//	 * @param i
//	 * @param j
//	 * @return
//	 */
//	public List<C> getNeighborsWrap(int i, int j) {
//		List<T> ans = new ArrayList<>();
//		for (int[] dp : dps) {
//			int x = i + dp[0];
//			int y = j + dp[1];
//			if (x < 0) {
//				x = rows() - 1;
//			} 
//			if (x == rows()) {
//				x = 0;
//			} 
//			if (y < 0) {
//				y = cols() - 1;
//			} 
//			if (y == cols()) {
//				y = 0;
//			}
//			if (x == i && y == j) {
//				ans.add(null);
//			} else {
//				ans.add(get(x, y));
//			}
//		}
//		return ans;
//	}
//	

	@Override
	public List<Cell> getNeighborsWrapping(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Cell> getNeighborsNoWrapping(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public void render(GraphicsContext gc) {
		// TOOD: let cells to update themselves by giving them their positions.
	}

}
