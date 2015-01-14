package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

public class EntityEnemyMars extends EntityEnemy {

	protected Animation animationRun;
	protected Animation animationFight;

	private boolean walkLeft;

	public EntityEnemyMars(double x, double y, double width, double height) {
		super(x, y, width, height, null);

		walkLeft = true;
		animationRun = new Animation(14, 28);
		animationFight = new Animation(16, 28);
		animationRun.load("/model/enemys/MarsKnight-RunAnimation.png", 2, 150);
		animationFight.load("/model/enemys/MarsKnight-FightAnimation.png", 2, 150);
		image = ResourceManager.getImage("/model/enemys/MarsKnight-Model.png");
	}

	public void update(LevelMap map) {
		if (walkLeft) {
			xMovement = 1;
		} else {
			xMovement = -1;
		}
		super.update(map);
		if (xMovement == 0) {
			walkLeft = !walkLeft;
		}
	}
}
