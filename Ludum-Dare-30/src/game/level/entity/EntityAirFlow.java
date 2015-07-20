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
		int lifeTime = 120;
		int particleSpawnCount = (int) ((width * height) / (TileSet.SPRITE_SIZE * TileSet.SPRITE_SIZE)) / 5;
		for (int i = 0; i < particleSpawnCount; i++) {
			if (random.nextDouble() < 0.2 * timeFactor) {
				double x;
				if (forceX > 0) {
					x = this.x + (width - forceX * lifeTime) * random.nextDouble();
				} else {
					x = this.x + (width - (-forceX * lifeTime)) * random.nextDouble() - forceX * lifeTime;
				}
				double y;
				if (forceY > 0) {
					y = this.y + (height - forceY * lifeTime) * random.nextDouble();
				} else {
					y = this.y + (height - (-forceY * lifeTime)) * random.nextDouble() - forceY * lifeTime;
				}
				map.spawnParticle(new ParticleAirFlow(x, y, lifeTime, forceX, forceY));
			}

		}
	}

	public void draw(Graphics2D g2, double timeFactor) {

	}

	public void interaction(Entity entity, LevelMap map) {
		if (entity instanceof EntityMob) {
			EntityMob e = (EntityMob) entity;
			e.pushX(forceX, 0.2);
			e.pushY(forceY, 0.2);
		}
	}

	public void interactionPlayerRecord(EntityPlayerRecord entity, LevelMap map) {
		entity.pushX(forceX, 0.2);
		entity.pushY(forceY, 0.2);
	}

}
