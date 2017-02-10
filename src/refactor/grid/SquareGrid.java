package refactor.grid;

import java.util.ArrayList;
import java.util.List;

import refactor.cell.Cell;

public class SquareGrid<C extends Cell> extends Grid<C> {

	public SquareGrid(C[][] arr) {

	}
	
	private static final int[][] dps = new int[][] {
		{-1,-1},{-1, 0},{-1, 1},
		{ 0,-1},        { 0, 1},
		{ 1,-1},{ 1, 0},{ 1, 1}
	};

	public List<C> getNeighbors(int i, int j, SquareNeighbors neighborsType, boolean wrapping) {
		List<C> res = new ArrayList<>();
		for (int[] dp : dps) {
			int x = i + dp[0];
			int y = j + dp[1];
			if (wrapping) {
				if (x < 0) { x = rows() - 1; } 
				if (x == rows()) { x = 0; } 
				if (y < 0) { y = cols() - 1; } 
				if (y == cols()) { y = 0; }
				if (x == i && y == j) {
					res.add(null);
				} else {
					res.add(get(x, y));
				}
			} else {
				if (x < 0 || x >= rows() || y < 0 || y >= cols()) {
					res.add(null);
				} else {
					res.add(get(x, y));
				}
			}
		}
		// filter the neighbors list with the neighborsType input.
		List<C> filtered = new ArrayList<C>();
		for (int index : neighborsType) {
			filtered.add(res.get(index));
		}

		return filtered;
	}

}
