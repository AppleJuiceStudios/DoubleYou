package game.level.entity;

import game.level.LevelMap;
import game.level.TileSet;
import game.level.particle.ParticleAirFlow;

import java.awt.Graphics2D;
import java.util.Random;

public class EntityAirFlow extends Entity {

	protected double forceX;
	protected double forceY;

	protected Random random;

	public EntityAirFlow(double x, double y, double width, double height, double forceX, double forceY) {
		super(x, y, width, height, null);
		this.forceX = forceX;
		this.forceY = forceY;
		random = new Random();
	}

	public void update(LevelMap map, double timeFactor) {
		if (random.nextDouble() < 0.1) {
			int lifeTime = 120;
			double x;
			if (forceX > 0) {
				x = this.x * TileSet.SPRITE_SIZE + (width * TileSet.SPRITE_SIZE - forceX * lifeTime) * random.nextDouble();
			} else {
				x = this.x * TileSet.SPRITE_SIZE + (width * TileSet.SPRITE_SIZE + forceX * lifeTime) * random.nextDouble() + forceX * lifeTime;
			}
			double y;
			if (forceY > 0) {
				y = this.y * TileSet.SPRITE_SIZE + (height * TileSet.SPRITE_SIZE - forceY * lifeTime) * random.nextDouble();
			} else {
				y = this.y * TileSet.SPRITE_SIZE + (height * TileSet.SPRITE_SIZE + forceY * lifeTime) * random.nextDouble() + forceY * lifeTime;
			}
			map.spawnParticle(new ParticleAirFlow(x, y, lifeTime, forceX, forceY));
		}
	}

	public void draw(Graphics2D g2, double timeFactor) {

	}

	public void interaction(Entity entity, LevelMap map) {
		if (entity instanceof EntityMob) {
			EntityMob e = (EntityMob) entity;
			e.pushX(forceX);
			e.pushY(forceY);
		}
	}

}
