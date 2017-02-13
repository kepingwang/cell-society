package refactor.grid;

import refactor.cell.Cell;
import refactor.cell.CellPane;
import refactor.config.GridConfig;
import refactor.config.SizeConfig;

/**
 * {@code CellPaneGrid} extends {@code Grid}. How the rectangle, triangle,
 * or hexagon cells are tiled is determined in {@code initCellPaneGrid()};
 * @author keping
 *
 * @param <T> The specific Game Cell.
 */
public class CellPaneGrid<T extends Cell> extends Grid<CellPane<T>> {
	
	public CellPaneGrid(GridConfig gridConfig, SizeConfig size, T[][] cells) {
		super(gridConfig, size.rows(), size.cols());
		initCellPaneGrid(size, cells);
	}

	private void initCellPaneGrid(SizeConfig size, T[][] cells) {
		double w = size.width(); // total width
		double h = size.height(); // total height
		for (int i = 0; i < rows(); i++) {
			for (int j = 0; j < cols(); j++) {
				double cw = 0, ch = 0, dx = 0, dy = 0;
				if (gridTypeName().equals(GridType.SQUARE)) {
					cw = w / cols(); // cell width
					ch = h / rows(); // cell height
					dx = j * cw; // transformation in x position
					dy = i * ch; // transformation in y position
				} else if (gridTypeName().equals(GridType.TRIANGLE)) {
					cw = w / cols() * 2;
					ch = h / rows();
					dx = j * (w / cols());
					dy = i * ch;
				} else if (gridTypeName().equals(GridType.HEXAGON)) {
					// an additional parameter is needed to determine the hexagons 
					cw = w / cols() * GridType.HEXAGON_MULTIPLIER;
					ch = h / rows();
					dx = j * (w / cols());
					dy = i * ch + ((j % 2) * ch / 2.0);
				}
				set(i, j, new CellPane<T>(this, i, j, cw, ch, dx, dy, cells[i][j]));
			}
		}
	}
	
}
