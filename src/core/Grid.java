package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper class around a 2d array
 * @author keping
 *
 * @param <T>
 */
public class Grid<T> implements Iterable<T> {
	private Object[][] grid;
	
	public Grid(T[][] arr) {
		if (arr.length == 0 || arr[0].length == 0) {
			throw new IllegalArgumentException(
					"Input cannot have size 0"
			);
		}
		if (arr[0][0] == null) {
			throw new IllegalArgumentException(
					"Element in 2d array input cannot be null"
			);
		}
		int cols = arr[0].length;
		grid = new Object[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++){
			if (cols != arr[i].length) {
				throw new IllegalArgumentException(
						"Input should be rectangle 2D array."
				);
			}
			for (int j = 0; j < arr[0].length; j++) {
				grid[i][j] = arr[i][j];
			}
		}
	}
	
	public int rows() {
		return grid.length;
	}
	public int cols() {
		return grid[0].length;
	}
	@SuppressWarnings("unchecked")
	public T get(int i, int j) {
		return (T) grid[i][j];
	}
	
	private static final int[][] dps = new int[][] {
		{-1,-1},{-1, 0},{-1, 1},
		{ 0,-1},        { 0, 1},
		{ 1,-1},{ 1, 0},{ 1, 1}
	};
	
	
	/**
	 * Return a List of neighbors surrounding target cell. 
	 * Indices correspond to neighbors as follows:
	 * <pre>
	 * 0 1 2
	 * 3 * 4
	 * 5 6 7
	 * </pre>
	 * If the neighbor's position goes out of boundary, 
	 * then that element is null in the List.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<T> getNeighbors(int i, int j) {
		List<T> ans = new ArrayList<>();
		for (int[] dp : dps) {
			int x = i + dp[0];
			int y = j + dp[1];
			if (x < 0 || x >= rows() || y < 0 || y >= cols()) {
				ans.add(null);
			} else {
				ans.add(get(x, y));
			}
		}
		return ans;
	}
	
	/**
	 * {@link getNeighbors} with wrap around. If wrapping encounters
	 * itself, then null is added to List. 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<T> getNeighborsWrap(int i, int j) {
		List<T> ans = new ArrayList<>();
		for (int[] dp : dps) {
			int x = i + dp[0];
			int y = j + dp[1];
			if (x < 0) {
				x = rows() - 1;
			} 
			if (x == rows()) {
				x = 0;
			} 
			if (y < 0) {
				y = cols() - 1;
			} 
			if (y == cols()) {
				y = 0;
			}
			if (x == i && y == j) {
				ans.add(null);
			} else {
				ans.add(get(x, y));
			}
		}
		return ans;
	}
	
	private final class GridIterator implements Iterator<T> {
		int i = 0;
		int j = 0;
		@Override
		public boolean hasNext() {
			return (i < rows() && j < cols());
		}
		@Override
		public T next() {
			T res = get(i, j);
			j++;
			if (j == cols()) {
				i++;
				j = 0;
			}
			return res;
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new GridIterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		for (int i = 0; i < rows(); i++) {
			sb.append("  [").append(get(i,0));
			for (int j = 1; j < cols(); j++) {
				sb.append(",").append(get(i,j));
			}
			sb.append("]").append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		Integer[][] objMat = new Integer[3][];
		objMat[0] = new Integer[3];
		objMat[0][0] = 1;
		objMat[0][1] = 2;
		objMat[0][2] = 3;
		objMat[1] = new Integer[3];
		objMat[1][0] = 4;
		objMat[1][1] = 5;
		objMat[1][2] = 6;
		objMat[2] = new Integer[3];
		objMat[2][0] = 7;
		objMat[2][1] = 8;
		objMat[2][2] = 9;
		
		Grid<Integer> grid = new Grid<>(objMat);
		for (int num : grid) {
			System.out.println(num);
		}
		System.out.println(grid);
		
		System.out.println(grid.getNeighbors(0,0));
		System.out.println(grid.getNeighbors(1,0));
		System.out.println(grid.getNeighbors(2,0));
		System.out.println(grid.getNeighbors(0,1));
		System.out.println(grid.getNeighbors(0,2));
	}
	
}
