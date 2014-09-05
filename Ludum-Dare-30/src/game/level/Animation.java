package game.level;

import game.res.ResourceManager;

import java.awt.image.BufferedImage;

public class Animation {
	public static final int WIDTH = 14;
	public static final int HEIGHT = 31;

	private int index = 0;
	private int delay = 0;
	private BufferedImage[] imgAnimation;

	private long lastChange;

	public Animation() {
	}

	public void load(String path, int imageAmount, int delay) {
		this.delay = delay;
		BufferedImage image = ResourceManager.getImage(path);
		imgAnimation = new BufferedImage[imageAmount];
		for (int i = 0; i < imageAmount; i++) {
			imgAnimation[i] = image.getSubimage(i * WIDTH, 0, WIDTH, HEIGHT);
		}
	}

	public void load(BufferedImage image, int imageAmount, int delay) {
		this.delay = delay;
		imgAnimation = new BufferedImage[imageAmount];
		for (int i = 0; i < imageAmount; i++) {
			imgAnimation[i] = image.getSubimage(i * WIDTH, 0, WIDTH, HEIGHT);
		}

	}

	public BufferedImage getImage() {
		if (System.currentTimeMillis() > lastChange + delay) {
			lastChange += delay;
			index = (index + 1) % imgAnimation.length;
			lastChange = System.currentTimeMillis();
		}
		return imgAnimation[index];
	}
}
