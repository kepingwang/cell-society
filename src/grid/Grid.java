package grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import config.GridConfig;


/**
 * The grid is basically a 2d array. The grid can also represent 
 * triangle and hexagon tiling, by giving different {@link GridConfig}
 * during construction. The different {@link GridConfig} then determines
 * how the method {@link getNeighbors(int i, int j)} works.
 * 
 * @author keping
 *
 * @param <T>
 */
public abstract class Grid<T> implements Iterable<T> {
	private GridType gridType;
	private NeighborsType neighborsType;
	private boolean wrapping;
	
	private T[][] grid;

	@SuppressWarnings("unchecked")
	public Grid(GridConfig gridConfig, int rows, int cols) {
		gridType = gridConfig.gridType();
		neighborsType = gridConfig.neighborsType();
		wrapping = gridConfig.isWrapping();
		grid = (T[][]) new Object[rows][cols];
	}

	public int rows() {
		return grid.length;
	}
	public int cols() {
		return grid[0].length;
	}
	public T get(int i, int j) {
		return grid[i][j];
	}
	public void set(int i, int j, T t) {
		grid[i][j] = t;
	}
	
	public String gridTypeName() {
		return gridType.name();
	}
	
	
	private static final int[] TRIANGLE_UP = new int[] {1,2,3,5,6,7,8,9,10,11,12,13};
	private static final int[] TRIANGLE_DOWN = new int[] {0,1,2,3,4,5,6,7,8,10,11,12};
	private List<T> filter(List<T> list, int[] indices) {
		List<T> res = new ArrayList<T>();
		for (int index : indices) {
			res.add(list.get(index));
		}
		return res;
	}
	public final List<T> getNeighbors(int i, int j) {
		List<T> res = new ArrayList<>();
		for (int[] dp : gridType.deltaPositions()) {
			int inb = i + dp[0];
			int jnb = j + dp[1];
			if (wrapping) {
				if (inb < 0) { inb += rows(); } 
				if (inb >= rows()) { inb -= rows(); } 
				if (jnb < 0) { jnb += cols(); } 
				if (jnb >= cols()) { jnb -= cols(); }
				if ((inb == i && jnb == j) ||
						inb < 0 || inb >= rows() || jnb < 0 || jnb >= cols()) {
					res.add(null);
				} else {
					res.add(get(inb, jnb));
				}
			} else {
				if (inb < 0 || inb >= rows() || jnb < 0 || jnb >= cols()) {
					res.add(null);
				} else {
					res.add(get(inb, jnb));
				}
			}
		}
		// For triangle, pointing up and down has different neighbors.
		if (gridType.name().equals(GridType.TRIANGLE)) {
			boolean trianglePointUp = true;
			if (i % 2 != 0) { trianglePointUp = !trianglePointUp; }
			if (j % 2 != 0) { trianglePointUp = !trianglePointUp; }
			if (trianglePointUp) {
				res = filter(res, TRIANGLE_UP);
			} else {
				res = filter(res, TRIANGLE_DOWN);
			}	
		}
		
		return filter(res, neighborsType.filterIndices());
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
	
}
