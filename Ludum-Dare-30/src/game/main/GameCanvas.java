package game.main;

import game.staging.StageManager;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private StageManager stageManager;
	public FpsManager fpsManager;

	private Thread thread;

	public static boolean IS_APPLET;

	public static final int FPS_MAX = 60;

	public GameCanvas() {
		setBackground(Color.RED);
		stageManager = new StageManager(this);
		fpsManager = new FpsManager();
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
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		thread = new Thread(new Runnable() {
			public void run() {
				fpsManager.init();
				while (true) {
					draw();
					fpsManager.limit();
				}
			}
		});
	}

	public void start() {
		thread.start();
	}

	public void stop() {

	}

	public void destroy() {

	}

	private class FpsManager {
		private long startTime;
		private long delay;
		private long waitTime;

		private long lastTime;
		private long time;

		private int fps;

		private void init() {
			startTime = System.currentTimeMillis();
			delay = 0;
			waitTime = 1000 / FPS_MAX;

			lastTime = 0;
			time = 0;
		}

		private void limit() {
			delay = waitTime - (System.currentTimeMillis() - startTime);
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			time = System.nanoTime();
			fps = (int) (1000000000d / (time - lastTime));
			lastTime = time;
			startTime = System.currentTimeMillis();
		}

		public int getFps() {
			return fps;
		}
	}
}
