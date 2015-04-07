package game.level.entity;

import game.level.Animation;
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

	public void move() {
		xMovement = 0;
		if (onGround) {
			canDoubleJump = true;
		}
		if (key_A) {
			xMovement = -2;
		}
		if (key_D) {
			xMovement = 2;
		}
		if (key_W & onGround) {
			yMovement = -2.75;
		} else if (!old_key_W && key_W && canDoubleJump) {
			yMovement = -2.75;
			canDoubleJump = false;
		}
		old_key_W = key_W;
	}

}
