package utils;

public class LayoutGenerator {

	/**
	 * Take rows, cols,
	 * and an array of probabilities of different states (starting from state 0),
	 * and generate an uniformly random layout of states on a 2d array. 
	 * <b>The input probabilities have to sum up to 1! (Not necessarily precisely.)</b>
	 * For example, for a two-state game (game of life),
	 * the input probs can be [0.3, 0.7], which means that state 0 occurs with
	 * probability 0.3 and state 1 occurs with probability 0.7;
	 * @param rows
	 * @param cols
	 * @param probs
	 * @return a state layout
	 */
	public static int[][] generateRandomLayout(int rows, int cols, double[] probs) {
		double[] sum = new double[probs.length];
		sum[0] = probs[0];
		for (int i = 1; i < sum.length; i++) {
			sum[i] = probs[i] + sum[i-1];
		}
		int[][] res = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double rand = Math.random();
				int state = 0;
				while (state < sum.length && sum[state] < rand) {
					state++;
				}
				res[i][j] = state;
			}
		}
		return res;
	}
	
	private static void print(int[][] arr) {
		System.out.println("[");
		for (int i = 0; i < arr.length; i++) {
			System.out.print("  ["+arr[i][0]);
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(", "+arr[i][j]);
			}
			System.out.println("]");
		}
		System.out.println("]");
	}
	
	public static void main(String[] args) {
		int[][] layout = generateRandomLayout(5, 4, new double[] {0.3, 0.7});
		print(layout);
	}
	
}
