package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

public class EntityPlayerClone extends EntityPlayer {

	private Byte[] recording;
	private int frame = 0;
	private boolean isDead = false;

	public EntityPlayerClone(double x, double y, Byte[] recording) {
		super(x, y);
		this.recording = recording;
	}

	public void loadResources() {
		image = ResourceManager.getImage("/model/player/Player-Model.png");
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		animationRun.load("/model/player/Run-Animation.png", 2, 150);
		animationJump.load("/model/player/Jump-Animation.png", 2, 150);
	}

	public void update(LevelMap map, double timeFactor) {
		if (!isDead) {
			byte movement = recording[frame];
			key_W = movement > 2;
			key_A = (movement % 3) == 2;
			key_D = (movement % 3) == 1;
			super.update(map, timeFactor);
			frame++;
			if (frame >= recording.length) {
				isDead = true;
			}
		}
	}

	public boolean isDead() {
		return isDead;
	}

	public boolean colideWithBlock(LevelMap map, int x, int y) {
		return map.isCloneSolid(x, y);
	}

}
