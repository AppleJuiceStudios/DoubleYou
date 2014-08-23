package game.level;

import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityPlayerClone extends EntityPlayer {

	private Byte[] recording;
	private int frame = 0;
	private boolean isDead = false;

	public EntityPlayerClone(double x, double y, Byte[] recording) {
		super(x, y);
		animationRun = new Animation();
		animationJump = new Animation();
		animationRun.load("/model/player/Run-Animation.png", 2, 150);
		animationJump.load("/model/player/Jump-Animation.png", 2, 150);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/model/player/Player-Model.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
