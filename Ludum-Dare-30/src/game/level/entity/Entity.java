package game.level.entity;

import game.level.map.LevelMap;
import game.staging.StageLevel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {

	protected double x = 0;
	protected double y = 0;
	protected double width = 16;
	protected double height = 16;

	protected BufferedImage image;
	protected boolean lookLeft;

	public Entity(double x, double y, double width, double height, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	public void draw(Graphics2D g2, boolean animated) {
		if (lookLeft) {
			g2.drawImage(getImage(animated), (int) ((x + width) * StageLevel.SCALE), (int) (y * StageLevel.SCALE), (int) (-width * StageLevel.SCALE),
					(int) (height * StageLevel.SCALE), null);
		} else {
			g2.drawImage(getImage(animated), (int) (x * StageLevel.SCALE), (int) (y * StageLevel.SCALE), (int) (width * StageLevel.SCALE),
					(int) (height * StageLevel.SCALE), null);
		}
	}

	protected BufferedImage getImage(boolean animated) {
		return image;
	}

	public void update(LevelMap map) {

	}

	public double getXPos() {
		return x;
	}

	public double getYPos() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
