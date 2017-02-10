package refactor.grid;

import java.util.Iterator;

import javafx.scene.Group;
import refactor.cell.Cell;


public abstract class Grid<C extends Cell> implements Iterable<C> {
	
	private Group rootNode = new Group();
	protected C[][] grid;
	
	public int rows() {
		return grid.length;
	}
	public int cols() {
		return grid[0].length;
	}
	public C get(int i, int j) {
		return grid[i][j];
	}
	
	private final class GridIterator implements Iterator<C> {
		int i = 0;
		int j = 0;
		@Override
		public boolean hasNext() {
			return (i < rows() && j < cols());
		}
		@Override
		public C next() {
			C res = get(i, j);
			j++;
			if (j == cols()) {
				i++;
				j = 0;
			}
			return res;
		}
	}
	@Override
	public Iterator<C> iterator() {
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
