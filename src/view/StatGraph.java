package view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import society.Society;
import society.SocietyFactory;

public class StatGraph extends Canvas {
	private static final double WIDTH = SocietyFactory.SIZE;
	private static final double HEIGHT = 100;
	private static final double SPACING = 3;

	private Society<?> society;
	private GraphicsContext gc;
	private Map<Color, LinkedList<Double>> stats;
	private int maxSize;

	public StatGraph(Society<?> society) {
		super(WIDTH, HEIGHT);
		this.society = society;
		gc = this.getGraphicsContext2D();
		stats = new HashMap<>();
		maxSize = 1 + (int) ((WIDTH / SPACING) / 2);
		update();
	}

	public void update() {
		updateStats();
		syncView();
	}

	private void addToList(LinkedList<Double> ll, double num) {
		ll.addLast(num);
		if (ll.size() > maxSize) {
			ll.removeFirst();
		}
	}

	private void updateStats() {
		for (ColorStat colorStat : society.getColorStats()) {
			LinkedList<Double> ll = stats.computeIfAbsent(
					colorStat.color(), e -> new LinkedList<Double>());
			addToList(ll, colorStat.stat());
		}
	}
	
	private void drawLine(GraphicsContext gc, LinkedList<Double> list) {
		ListIterator<Double> it = list.listIterator(list.size());
		double xPos = WIDTH / 2;
		boolean started = false;
		gc.beginPath();
		while (it.hasPrevious()) {
			double stat = it.previous();
			if (!started) {
				gc.moveTo(xPos, HEIGHT * (1-stat));
				started = true;
			} else {
				gc.lineTo(xPos, HEIGHT * (1-stat));
			}
			xPos -= SPACING;
		}
		gc.stroke();
		gc.closePath();
	}
	private void syncView() {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.WHITE); // TODO White line cannot be seen
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		for (Color key : stats.keySet()) {
			gc.setStroke(key);
			gc.setLineWidth(3);
			drawLine(gc, stats.get(key));
		}
		
	}

}
