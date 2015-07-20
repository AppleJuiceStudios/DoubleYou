package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.level.particle.ParticleDoubleJump;
import game.res.ResourceManager;

public class EntityPlayerCloneJump extends EntityPlayerClone {

	private boolean canDoubleJump;
	private boolean old_key_W;

	public EntityPlayerCloneJump(double x, double y, Byte[] recording) {
		super(x, y, recording);
	}

	public void loadResources() {
		image = ResourceManager.getImage("/model/player/DoubleJumpClone-Model.png");
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		animationRun.load("/model/player/DoubleJumpClone-RunAnimation.png", 2, 150);
		animationJump.load("/model/player/DoubleJumpClone-JumpAnimation.png", 2, 150);
	}

	public void move(LevelMap map) {
		movementVelocityX = 0;
		if (onGround) {
			canDoubleJump = true;
		}
		if (key_left) {
			movementVelocityX = -2;
		}
		if (key_right) {
			movementVelocityX = 2;
		}
		if (key_up & onGround) {
			pushY(-2.75, 1);
		} else if (!old_key_W && key_up && canDoubleJump) {
			pushY(-2.75, 1);
			canDoubleJump = false;
			map.spawnParticle(new ParticleDoubleJump(x + (width / 2), y + height));
		}
		old_key_W = key_up;
	}

}
