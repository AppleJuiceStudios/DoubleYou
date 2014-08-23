package game.main;

import game.staging.StageManager;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas {

	private StageManager stageManager;

	public GameCanvas() {
		setBackground(Color.RED);
		stageManager = new StageManager(this);
	}

	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		stageManager.draw(g2);
		g2.dispose();
		bs.show();
	}

	public void paint() {
		draw();
	}

	public void init() {
		setPreferredSize(new Dimension(800, 600));
	}

	public void start() {

	}

	public void stop() {

	}

	public void destroy() {

	}

}
