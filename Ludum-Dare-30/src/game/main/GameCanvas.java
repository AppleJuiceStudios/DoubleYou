package game.main;

import game.staging.StageManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private StageManager stageManager;
	public static FpsManager fpsManager;

	private Thread thread;

	public static boolean IS_APPLET;

	public static final int FPS_MAX = 60;

	public GameCanvas() {

		stageManager = new StageManager(this);
		fpsManager = new FpsManager();
	}

	public void draw() {
		Monitoring.startDraw();
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		stageManager.draw(g2);
		g2.dispose();
		bs.show();
		Monitoring.stopDraw();
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
					Monitoring.tick();
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

	static class FpsManager {
		private long startTime;
		private long delay;
		private long waitTime;

		private long lastTime;
		private long time;

		private static int fps;

		private void init() {
			startTime = System.currentTimeMillis();
			delay = 0;
			waitTime = 1000 / FPS_MAX;

			lastTime = 0;
			time = 0;
		}

		private void limit() {
			Monitoring.startSleep();
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
			//			System.out.println("FPS: " + fps);
			lastTime = time;
			startTime = System.currentTimeMillis();
			Monitoring.stopSleep();
		}

		public static int getFps() {
			return fps;
		}
	}
}
