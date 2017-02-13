package society;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cell.CellPane;
import config.GridConfig;
import config.SizeConfig;
import grid.CellPaneGrid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import view.ColorStat;

/**
 * <p>The specific subclass of game cell has to be passed as type for 
 * Society class. To construct a Society, we need the {@link GridConfig},
 * and {@link SizeConfig} as input, and also a 2d array of specific cells.
 * The 2d array of cells will basically just be put in the grid. I've designed
 * the {@link Cell} superclass such that a cell needs no information
 * about its position and size during its construction.</p>
 * 
 * @author keping
 *
 * @param <T> The specific Cell class, like WatorCell.
 */
public class Society<T extends Cell> extends Group {
	private double width;
	private double height;
	
	private CellPaneGrid<T> grid;

	public Society(GridConfig gridConfig, SizeConfig sizeConfig, T[][] cells) {
		grid = new CellPaneGrid<T>(gridConfig, sizeConfig, cells);
		for (CellPane<T> cellPane : grid) {
			getChildren().add(cellPane);
		}
		width = sizeConfig.width();
		height = sizeConfig.height();
	}
	
	private void updateNextStates() {
		for (CellPane<T> cellPane : grid) {
			cellPane.updateNextState();
		}
	}
	private void syncStates() {
		for (CellPane<T> cellPane : grid) {
			cellPane.syncState();
		}
	}
	private void syncViews() {
		for (CellPane<T> cellPane : grid) {
			cellPane.syncView();
		}
	}
	public void update() {
		updateNextStates();
		syncStates();
		syncViews();
	}
	
	public double width() { return width; }
	public double height() { return height; }

	public List<ColorStat> getColorStats() {
		Map<Color, Integer> cmap = new HashMap<>();
		for (CellPane<T> cellPane : grid) {
			Map<Color, Integer> map = cellPane.getColor();
			for (Color color : map.keySet()) {
				if (!cmap.containsKey(color)) {
					cmap.put(color, map.get(color));
				} else {
					cmap.put(color, cmap.get(color) + map.get(color));
				}
			}

		}
		
		List<ColorStat> res = new ArrayList<>();
		int totalCount = grid.rows() * grid.cols();
		for (Color color : cmap.keySet()) {
			res.add(new ColorStat(color, cmap.get(color)/ (double) totalCount));
		}
		return res;
	}
	
}
