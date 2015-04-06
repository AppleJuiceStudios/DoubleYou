package game.level;

import game.res.ResourceManager;

import java.awt.image.BufferedImage;

public class Animation {
	private int width = 14;
	private int height = 31;

	private int index = 0;
	private int delay = 0;
	private BufferedImage[] imgAnimation;

	private long lastChange;

	public Animation(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void load(String path, int imageAmount, int delay) {
		this.delay = delay;
		BufferedImage image = ResourceManager.getImage(path);
		imgAnimation = new BufferedImage[imageAmount];
		for (int i = 0; i < imageAmount; i++) {
			imgAnimation[i] = image.getSubimage(i * width, 0, width, height);
		}
	}

	public void load(BufferedImage image, int imageAmount, int delay) {
		this.delay = delay;
		imgAnimation = new BufferedImage[imageAmount];
		for (int i = 0; i < imageAmount; i++) {
			imgAnimation[i] = image.getSubimage(i * width, 0, width, height);
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
