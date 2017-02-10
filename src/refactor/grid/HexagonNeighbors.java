package refactor.grid;

public class HexagonNeighbors extends Neighbors {

	public static final HexagonNeighbors HEXAGON_6 = new HexagonNeighbors(new int[] { 0, 1, 2, 3, 4, 5 });

	private HexagonNeighbors(int[] indices) {
		super(indices);
	}

}
