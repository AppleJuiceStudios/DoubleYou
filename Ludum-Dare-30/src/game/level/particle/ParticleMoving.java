package game.level.particle;

import game.level.LevelMap;

import java.awt.image.BufferedImage;

public class ParticleMoving extends Particle {

	protected double xMovement;
	protected double yMovement;

	public ParticleMoving(double x, double y, int width, int height, BufferedImage image, int lifeTime, double xMovement, double yMovement) {
		super(x, y, width, height, image, lifeTime);
		this.xMovement = xMovement;
		this.yMovement = yMovement;
	}

	public ParticleMoving(double x, double y, int width, int height, String image, int lifeTime, double xMovement, double yMovement) {
		super(x, y, width, height, image, lifeTime);
		this.xMovement = xMovement;
		this.yMovement = yMovement;
	}

	public void update(LevelMap map, double timeFactor) {
		x += xMovement * timeFactor;
		y += yMovement * timeFactor;
		super.update(map, timeFactor);
	}

}
