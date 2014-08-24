package game.level;

import game.level.map.LevelMap;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityPlayerClone extends EntityPlayer {

	private static BufferedImage imageIdel;
	private static BufferedImage imageRun;
	private static BufferedImage imageJump;

	private Byte[] recording;
	private int frame = 0;
	private boolean isDead = false;

	public EntityPlayerClone(double x, double y, Byte[] recording) {
		super(x, y);
		animationRun = new Animation();
		animationJump = new Animation();
		if (imageIdel == null) {
			try {
				imageIdel = ImageIO.read(getClass().getResourceAsStream("/model/player/Player-Model.png"));
				imageRun = ImageIO.read(getClass().getResourceAsStream("/model/player/Run-Animation.png"));
				imageJump = ImageIO.read(getClass().getResourceAsStream("/model/player/Jump-Animation.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
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

}
