package game.level.entity;

import java.awt.image.BufferedImage;

public class EntityEnemy extends EntityMob {

	public boolean isAlive;
	public int health;

	public EntityEnemy(double x, double y, double width, double height, BufferedImage image) {
		super(x, y, width, height, image);

		health = 1;
		isAlive = true;
	}
}
