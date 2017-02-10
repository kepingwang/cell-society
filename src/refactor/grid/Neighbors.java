package refactor.grid;

import java.util.Iterator;

/**
 * Use the static method to generate a config of the type of neighbors as input
 * to the getNeighbors methods of {@link Grid}.
 * 
 * @author keping
 */
public class Neighbors implements Iterable<Integer> {

	private int[] indices;

	protected Neighbors(int[] indices) {
		this.indices = indices;
	}

	private final class ArrayIterator implements Iterator<Integer> {
		int i = 0;

		@Override
		public boolean hasNext() {
			return i < indices.length;
		}

		@Override
		public Integer next() {
			return indices[i++];
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new ArrayIterator();
	}

}
