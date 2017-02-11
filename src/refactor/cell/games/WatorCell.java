package refactor.cell.games;

import java.util.List;

import javafx.scene.paint.Color;
import refactor.cell.Cell;

public class WatorCell extends SimpleCell {
	private final static int WATER = 0;
	private final static int FISH = 1;
	private final static int SHARK = 2;
	private int energy;
	

	public WatorCell(String cellShapeType, Color[] colors, List<Double> params, int state) {
		super(cellShapeType, colors, params, state);
		
	}

	@Override
	public void updateNextState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncView(int i, int j, double cw, double ch) {
		// TODO Auto-generated method stub
		
	}

}
