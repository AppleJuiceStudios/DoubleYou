package game.main;

import game.res.ResourceManager;
import game.res.SoundManager;
import game.staging.StageManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import util.Log;
import de.Auch.Monitoring;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas {

	public static int WIDTH;
	public static int HEIGHT;

	private StageManager stageManager;
	public static FpsManager fpsManager;

	private Thread thread;

	public static boolean IS_APPLET;

	public static final int FPS_MAX = 60;

	public GameCanvas(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
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
		setFocusable(true);
		requestFocus();

		thread = new Thread(new Runnable() {
			public void run() {
				fpsManager.init();
				while (!Thread.interrupted()) {
					draw();
					fpsManager.limit();
				}
			}
		});
		thread.setName("DrawingThread");
	}

	public void start() {
		thread.start();
	}

	public void close() {
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stageManager.close();
		SoundManager.close();
		ResourceManager.close();
		Log.closeLog();
		System.exit(0);
	}

	public void destroy() {

	}

	public class FpsManager {
		private long startTime;
		private long delay;
		private long waitTime;

		private long lastTime;
		private long time;

		private void init() {
			startTime = System.currentTimeMillis();
			delay = 0;
			waitTime = 1000 / FPS_MAX;

			lastTime = 0;
			time = 0;
		}

		private void limit() {
			Monitoring.start(1);
			delay = waitTime - (System.currentTimeMillis() - startTime);
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					thread.interrupt();
				}
			}

			time = System.nanoTime();
			Monitoring.updateInformation(0, (int) (1000000000d / (time - lastTime)));
			lastTime = time;
			startTime = System.currentTimeMillis();
			Monitoring.stop(1);
		}
	}

}
