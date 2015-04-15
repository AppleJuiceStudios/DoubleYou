package game.staging;

import game.main.GameCanvas;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Stage {

	private StageManager stageManager;

	public Stage(StageManager stageManager, Map<String, String> data) {
		this.stageManager = stageManager;
	}

	public StageManager getStageManager() {
		return stageManager;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void stop();

	public void drawBackground(Graphics2D g2, BufferedImage image) {
		double minXScale = (double) GameCanvas.WIDTH / image.getWidth();
		double minYScale = (double) GameCanvas.HEIGHT / image.getHeight();
		if (minXScale > minYScale) {
			g2.drawImage(image, 0, (int) (-(image.getHeight() * minXScale - GameCanvas.HEIGHT) / 2), GameCanvas.WIDTH, (int) (image.getHeight() * minXScale),
					null);
		} else if (minXScale < minYScale) {
			g2.drawImage(image, (int) (-(image.getWidth() * minYScale - GameCanvas.WIDTH) / 2), 0, (int) (image.getWidth() * minYScale), GameCanvas.HEIGHT,
					null);
		} else {
			g2.drawImage(image, 0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT, null);
		}
	}

	public void drawBackgroundTopAligned(Graphics2D g2, BufferedImage image) {
		double minXScale = (double) GameCanvas.WIDTH / image.getWidth();
		double minYScale = (double) GameCanvas.HEIGHT / image.getHeight();
		if (minXScale > minYScale) {
			g2.drawImage(image, 0, 0, GameCanvas.WIDTH, (int) (image.getHeight() * minXScale), null);
		} else if (minXScale < minYScale) {
			g2.drawImage(image, (int) (-(image.getWidth() * minYScale - GameCanvas.WIDTH) / 2), 0, (int) (image.getWidth() * minYScale), GameCanvas.HEIGHT,
					null);
		} else {
			g2.drawImage(image, 0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT, null);
		}
	}

}
