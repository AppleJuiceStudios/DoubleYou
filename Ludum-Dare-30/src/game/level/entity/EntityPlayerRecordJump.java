package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.level.particle.ParticleDoubleJumpRecord;
import game.res.ResourceManager;

public class EntityPlayerRecordJump extends EntityPlayerRecord {

	private boolean canDoubleJump;
	private boolean old_key_W;

	public EntityPlayerRecordJump(double x, double y) {
		super(x, y);
	}

	public void loadResources() {
		image = ResourceManager.getImage("/model/clone/DoubleJumpClone-Model.png");
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		animationRun.load("/model/clone/DoubleJumpClone-RunAnimation.png", 2, 150);
		animationJump.load("/model/clone/DoubleJumpClone-JumpAnimation.png", 2, 150);
	}

	public void move(LevelMap map) {
		xMovement = 0;
		if (onGround) {
			canDoubleJump = true;
		}
		if (key_left) {
			xMovement = -2;
		}
		if (key_right) {
			xMovement = 2;
		}
		if (key_up & onGround) {
			yMovement = -2.75;
		} else if (!old_key_W && key_up && canDoubleJump) {
			yMovement = -2.75;
			canDoubleJump = false;
			map.spawnParticle(new ParticleDoubleJumpRecord(x + (width / 2), y + height));
		}
		old_key_W = key_up;
	}
}
