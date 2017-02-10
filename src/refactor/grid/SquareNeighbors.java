package refactor.grid;

public class SquareNeighbors extends Neighbors {

	public static final SquareNeighbors SQUARE_8 = new SquareNeighbors(new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
	public static final SquareNeighbors SQUARE_4 = new SquareNeighbors(new int[] { 1, 3, 4, 6 });

	private SquareNeighbors(int[] indices) {
		super(indices);
	}

}
