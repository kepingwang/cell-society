package refactor.grid;

import java.util.Iterator;

/**
 * A wrapper class around a 2d array
 * @author keping
 *
 * @param <T>
 */
public abstract class Grid<T> implements Iterable<T> {
	
	protected Object[][] grid;
	
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
