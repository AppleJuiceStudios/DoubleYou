package game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class EntityPlayerRecord extends EntityPlayer {

	private static BufferedImage imageIdel;
	private static BufferedImage imageRun;
	private static BufferedImage imageJump;

	private List<Byte> recording;

	public EntityPlayerRecord(double x, double y) {
		super(x, y);
		if (imageIdel == null) {
			try {
				imageIdel = ImageIO.read(getClass().getResourceAsStream("/model/clone/Player-Model.png"));
				imageRun = ImageIO.read(getClass().getResourceAsStream("/model/clone/Run-Animation.png"));
				imageJump = ImageIO.read(getClass().getResourceAsStream("/model/clone/Jump-Animation.png"));
			} catch (IOException e) {

			}
		}
		image = imageIdel;
		animationRun = new Animation();
		animationJump = new Animation();
		animationRun.load(imageRun, 2, 150);
		animationJump.load(imageJump, 2, 150);
		recording = new ArrayList<Byte>();
	}

	public void update(LevelMap map) {
		byte movement = 0;
		if (key_D) {
			movement = 1;
		} else if (key_A) {
			movement = 2;
		}
		if (key_W) {
			movement += 3;
		}
		recording.add(movement);
		super.update(map);
	}

	public Byte[] getRecording() {
		return recording.toArray(new Byte[recording.size()]);
	}

}
