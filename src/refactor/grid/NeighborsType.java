package refactor.grid;

/**
 * Use the static method to generate a config of the type of neighbors as input
 * to the getNeighbors methods of {@link Grid}.
 * 
 * @author keping
 */
public final class NeighborsType {

	public static final NeighborsType HEXAGON_6 = new NeighborsType(new int[] { 0, 1, 2, 3, 4, 5 });

	public static final NeighborsType SQUARE_8 = new NeighborsType(new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
	public static final NeighborsType SQUARE_4 = new NeighborsType(new int[] { 1, 3, 4, 6 });

	public static final NeighborsType TRIANGLE_12 = new NeighborsType(
			new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 });

	private int[] indices;

	private NeighborsType(int[] indices) {
		this.indices = indices;
	}

	public int[] filterIndices() {
		return indices;
	}

}
