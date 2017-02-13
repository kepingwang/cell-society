package config;

public class SizeConfig {
	int rows;
	int cols;
	double width;
	double height;

	public SizeConfig(int rows, int cols, double width, double height) {
		this.rows = rows;
		this.cols = cols;
		this.width = width;
		this.height = height;
	}

	public int rows() { return rows; }
	public int cols() { return cols; }
	public double width() { return width; }
	public double height() { return height; }
	
}
