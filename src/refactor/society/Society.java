package refactor.society;

import javafx.scene.Group;
import refactor.cell.Cell;
import refactor.cell.CellPane;
import refactor.config.GridConfig;
import refactor.config.SizeConfig;
import refactor.grid.CellPaneGrid;

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
	
	private CellPaneGrid<T> grid;

	public Society(GridConfig gridConfig, SizeConfig sizeConfig, T[][] cells) {
		grid = new CellPaneGrid<T>(gridConfig, sizeConfig, cells);
		for (CellPane<T> cellPane : grid) {
			getChildren().add(cellPane);
		}
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

	
}
