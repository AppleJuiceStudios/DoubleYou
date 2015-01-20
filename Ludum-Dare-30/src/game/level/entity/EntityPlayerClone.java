package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

import java.awt.image.BufferedImage;

public class EntityPlayerClone extends EntityPlayer {

	private static BufferedImage imageIdel;
	private static BufferedImage imageRun;
	private static BufferedImage imageJump;

	private Byte[] recording;
	private int frame = 0;
	private boolean isDead = false;

	public EntityPlayerClone(double x, double y, Byte[] recording) {
		super(x, y);
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		if (imageIdel == null) {
			imageIdel = ResourceManager.getImage("/model/player/Player-Model.png");
			imageRun = ResourceManager.getImage("/model/player/Run-Animation.png");
			imageJump = ResourceManager.getImage("/model/player/Jump-Animation.png");
		}
		image = imageIdel;
		animationRun.load(imageRun, 2, 150);
		animationJump.load(imageJump, 2, 150);
		this.recording = recording;
	}

	public void update(LevelMap map) {
		if (!isDead) {
			byte movement = recording[frame];
			key_W = movement > 2;
			key_A = (movement % 3) == 2;
			key_D = (movement % 3) == 1;
			super.update(map);
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
