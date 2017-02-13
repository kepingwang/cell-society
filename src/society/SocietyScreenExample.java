package society;

import java.util.ArrayList;
import java.util.List;

import cell.games.SegregationCell;
import config.GridConfig;
import config.SizeConfig;
import grid.GridType;
import grid.NeighborsType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
		
		GridConfig gridConfig = new GridConfig(GridType.SQUARE_GRID, NeighborsType.SQUARE_8, wrapping);
		// neighborsType can also be NeighborsType.SQUARE_4, or whatever we can define. 
		String cellShapeType = GridType.SQUARE;
		
//		GridConfig gridConfig = new GridConfig(GridType.TRIANGLE_GRID, NeighborsType.TRIANGLE_12, wrapping);
//		String cellShapeType = GridType.TRIANGLE;
		
//		GridConfig gridConfig = new GridConfig(GridType.HEXAGON_GRID, NeighborsType.HEXAGON_6, wrapping);
//		String cellShapeType = GridType.HEXAGON;
		
		
		SizeConfig sizeConfig = new SizeConfig(5, 5, 200, 200);
		
		
		// We initialize the specific game cells and put them into Society.
		// These cells don't need positions or sizes to construct, only 
		// state related information is needed.
//		int[][] layout = new int[][] {
//			{0, 1, 0, 0, 1},
//			{1, 1, 0, 1, 1},
//			{1, 0, 1, 0, 0},
//			{1, 1, 0, 0, 1},
//			{0, 0, 1, 1, 1}
//		};
//		Color[] colors = new Color[] {Color.ALICEBLUE, Color.BLACK};
//		GameOfLifeCell[][] cells = new GameOfLifeCell[layout.length][layout[0].length];
//		List<Double> params = new ArrayList<Double>();
//		for (int i = 0; i < layout.length; i++) {
//			for (int j = 0 ; j < layout[0].length; j++) {
//				cells[i][j] = new GameOfLifeCell(cellShapeType, colors, params, layout[i][j]);
//			}
//		}
//		society = new Society<GameOfLifeCell>(gridConfig, sizeConfig, cells);

		
//		int[][] layout = new int[][] {
//			{0, 1, 0, 0, 1},
//			{1, 1, 0, 1, 1},
//			{1, 2, 1, 1, 0},
//			{1, 1, 0, 0, 1},
//			{0, 0, 1, 1, 1}
//		};
//		Color[] colors = new Color[] {Color.DARKGREY, Color.GREEN, Color.RED};
//		FireCell[][] cells = new FireCell[layout.length][layout[0].length];
//		List<Double> params = new ArrayList<Double>();
//		params.add(0.4);
//		for (int i = 0; i < layout.length; i++) {
//			for (int j = 0 ; j < layout[0].length; j++) {
//				cells[i][j] = new FireCell(cellShapeType, colors, params, layout[i][j]);
//			}
//		}
//		society = new Society<FireCell>(gridConfig, sizeConfig, cells);

		int[][] layout = new int[][] {
			{2, 1, 0, 0, 1},
			{1, 1, 0, 1, 1},
			{1, 2, 2, 2, 0},
			{1, 1, 0, 0, 1},
			{0, 0, 1, 1, 2}
		};
		Color[] colors = new Color[] {Color.LIGHTGREY, Color.RED, Color.BLUE};
		SegregationCell[][] cells = new SegregationCell[layout.length][layout[0].length];
		List<Double> params = new ArrayList<Double>();
		params.add(0.4);
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0 ; j < layout[0].length; j++) {
				cells[i][j] = new SegregationCell(cellShapeType, colors, params, layout[i][j]);
			}
		}
		society = new Society<SegregationCell>(gridConfig, sizeConfig, cells);
		
		
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
