package core.rules;

import core.Cell;
import javafx.scene.paint.Color;

public class WaTorRule implements Rule{
	private final int WATER = 0;
	private final int fishBirth;
	private final int fishNew;
	private final int sharkDeath;
	private final int sharkBirth;
	private final int sharkNew;
	
	
	private Color[] colors;
	
	
	public WaTorRule(Color[] colorsIn, int fishTimer, int deathTimer, int sharkTimer){
		colors = colorsIn;
		int fishStages = fishTimer + 1;
		fishNew = 1;
		fishBirth = fishStages + 1;
		int sharkStages = sharkTimer + deathTimer + 1;
		sharkDeath = fishBirth + 1;
		sharkNew = sharkDeath + deathTimer + 1;
		sharkBirth = sharkNew + sharkTimer;
	}

	@Override
	public int update(Cell cell, Cell[] neighbors) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Color[] getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public Color updateColor(Cell cell) {
		// TODO Auto-generated method stub
		return null;
	}

}
