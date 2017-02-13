package view;

import javafx.scene.paint.Color;

public class ColorStat {
	private Color color;
	private double stat; 
	
	public ColorStat(Color color, double stat) {
		this.color = color;
		this.stat = stat;
	}

	public Color color() { return color; }
	public double stat() { return stat; }
	
}
