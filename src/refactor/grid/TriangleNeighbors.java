package refactor.grid;

public class TriangleNeighbors extends Neighbors {

	public static final TriangleNeighbors TRIANGLE_12 = new TriangleNeighbors(
			new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 });

	private TriangleNeighbors(int[] indices) {
		super(indices);
	}

}
