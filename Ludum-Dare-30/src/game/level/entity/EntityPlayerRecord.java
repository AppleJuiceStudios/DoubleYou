package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EntityPlayerRecord extends EntityPlayer {

	private static BufferedImage imageIdel;
	private static BufferedImage imageRun;
	private static BufferedImage imageJump;

	private List<Byte> recording;

	public EntityPlayerRecord(double x, double y) {
		super(x, y);
		if (imageIdel == null) {
			imageIdel = ResourceManager.getImage("/model/clone/Player-Model.png");
			imageRun = ResourceManager.getImage("/model/clone/Run-Animation.png");
			imageJump = ResourceManager.getImage("/model/clone/Jump-Animation.png");
		}
		image = imageIdel;
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
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

	public boolean colideWithBlock(LevelMap map, int x, int y) {
		return map.isBlock(x, y);
	}

}
