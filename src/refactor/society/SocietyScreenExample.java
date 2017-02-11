package refactor.society;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import refactor.cell.games.SimpleCell;
import refactor.config.GridConfig;
import refactor.config.SizeConfig;
import refactor.grid.GridType;
import refactor.grid.NeighborsType;

public class SocietyScreenExample extends Application {
	
	Society<?> society;
	Timeline timeline;

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);
		
		// Try different settings!
		// (it's possible that cellShapeType is triangle while grid is square, 
		//  just not tiling)
		boolean wrapping = false; 
//		boolean wrapping = true;
		
//		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
//		// neighborsType can also be NeighborsType.SQUARE_4, or whatever we can define. 
//		String cellShapeType = GridType.SQUARE;
		
		GridConfig gridConfig = new GridConfig(GridType.TRIANGLE_GRID, NeighborsType.TRIANGLE_12, wrapping);
		String cellShapeType = GridType.TRIANGLE;
		
//		GridConfig gridConfig = new GridConfig(GridType.HEXAGON_GRID, NeighborsType.HEXAGON_6, wrapping);
//		String cellShapeType = GridType.HEXAGON;
		
		
		SizeConfig sizeConfig = new SizeConfig(5, 5, 200, 200);
		
		int[][] layout = new int[][] {
			{0, 1, 0, 0, 1},
			{1, 1, 0, 1, 1},
			{1, 0, 1, 0, 0},
			{1, 1, 0, 0, 1},
			{0, 0, 1, 1, 1}
		};
		
		// We initialize the specific game cells and put them into Society.
		// These cells don't need positions or sizes to construct, only 
		// state related information is needed.
		SimpleCell[][] cells = new SimpleCell[layout.length][layout[0].length];
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0 ; j < layout[0].length; j++) {
				cells[i][j] = new SimpleCell(cellShapeType, layout[i][j]);
			}
		}
		
		society = new Society<SimpleCell>(gridConfig, sizeConfig, cells);
		
		root.getChildren().add(society);
		
		setUpAnimation();
		
		stage.setScene(scene);
		stage.show();
		timeline.play();
		
	}
	
	private void setUpAnimation() {
		KeyFrame frame = new KeyFrame(
				Duration.millis(1000), 
				e -> society.update()
			);
		timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
