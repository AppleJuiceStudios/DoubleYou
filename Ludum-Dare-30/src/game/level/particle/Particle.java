package game.level.particle;

import game.level.LevelMap;
import game.res.ResourceManager;
import game.staging.StageLevel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Particle {

	protected double x;
	protected double y;

	protected int width;
	protected int height;

	protected BufferedImage image;
	protected int lifeTime;
	protected double lifeTimeremaining;

	public Particle(double x, double y, int width, int height, BufferedImage image, int lifeTime) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.lifeTime = lifeTime;
		lifeTimeremaining = lifeTime;
	}

	public Particle(double x, double y, int width, int height, String image, int lifeTime) {
		this(x, y, width, height, ResourceManager.getImage(image), lifeTime);
	}

	public void update(LevelMap map, double timeFactor) {
		lifeTimeremaining -= timeFactor;
	}

	public void draw(Graphics2D g2) {
		if (lifeTimeremaining < lifeTime) {
			int animationFrame = (int) (((lifeTime - lifeTimeremaining) * image.getWidth()) / (lifeTime * width));
			int dx = (int) (x - (width / 2));
			int dy = (int) (y - (height / 2));
			g2.drawImage(image, dx * StageLevel.SCALE, dy * StageLevel.SCALE, (dx + width) * StageLevel.SCALE, (dy + height) * StageLevel.SCALE, width
					* animationFrame, 0, width * (animationFrame + 1), height, null);
		}

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public double getLifeTimeremaining() {
		return lifeTimeremaining;
	}

	public void setLifeTimeremaining(double lifeTimeremaining) {
		this.lifeTimeremaining = lifeTimeremaining;
	}

}
