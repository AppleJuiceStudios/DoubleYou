package game.level;

import game.staging.StageLevel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {

	protected double x = 0;
	protected double y = 0;
	protected double width = 16;
	protected double height = 16;

	protected BufferedImage image;

	public Entity(double x, double y, double width, double height, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(image, (int) (x * StageLevel.SCALE), (int) (y * StageLevel.SCALE), (int) (width * StageLevel.SCALE),
				(int) (height * StageLevel.SCALE), null);
	}

	public void update(LevelMap map) {

	}

}
