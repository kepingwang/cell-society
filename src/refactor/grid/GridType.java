package refactor.grid;

/**
 * For Triangle and Hexagon I assume there to be at least one horizontal line.
 * Implementation of the tiling is in {@link CellPaneGrid}
 * 
 * @author keping
 *
 */
public final class GridType {
	
	public static final String SQUARE = "square";
	public static final String TRIANGLE = "triangle";
	public static final String HEXAGON = "hexagon";

	// These are the relative (di, dj) positions for neighbors
	public static final GridType SQUARE_GRID = new GridType(SQUARE, new int[][] {
		{-1,-1},{-1, 0},{-1, 1},
		{ 0,-1},        { 0, 1},
		{ 1,-1},{ 1, 0},{ 1, 1}
	});
	public static final GridType TRIANGLE_GRID = new GridType(TRIANGLE, new int[][] {
		{-1,-2},{-1,-1},{-1, 0},{-1, 1},{-1, 2},
		{ 0,-2},{ 0,-1},        { 0, 1},{ 0, 2},
		{ 1,-2},{ 1,-1},{ 1, 0},{ 1, 1},{ 1, 2}
	});
	public static final GridType HEXAGON_GRID = new GridType(HEXAGON, new int[][] {
		{-1,-1},{-1, 0},{-1, 1},
		{ 0,-1},        { 0, 1},
		        { 1, 0}
	});

	/**
	 * An extra parameter to determine the shape of the hexagon
	 */
	public static final double  HEXAGON_MULTIPLIER = 4.0 / 3.0;
	
	
	private String name;
	private int[][] dps;
	
	private GridType(String name, int[][] dps) {
		this.name = name;
		this.dps = dps;
	}

	public int[][] deltaPositions() {
		return dps;
	}
	public String name() {
		return name;
	}
	
}
