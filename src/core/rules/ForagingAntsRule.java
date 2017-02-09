package core.rules;

import java.util.List;

import core.Cell;
import javafx.scene.paint.Color;

public class ForagingAntsRule extends Rule {
	private final static Color[] FORAGING_ANTS_COLORS = {};

	public ForagingAntsRule(List<Double> parametersIn) {
		super(FORAGING_ANTS_COLORS, parametersIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int update(Cell cell, List<Cell> neighbors) {
		// TODO Auto-generated method stub
		return 0;
	}
}
