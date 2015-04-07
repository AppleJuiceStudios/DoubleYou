package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class EntityPlayerRecord extends EntityPlayer {

	private List<Byte> recording;

	public EntityPlayerRecord(double x, double y) {
		super(x, y);
		recording = new ArrayList<Byte>();
	}

	public void loadResources() {
		image = ResourceManager.getImage("/model/clone/Player-Model.png");
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		animationRun.load("/model/clone/Run-Animation.png", 2, 150);
		animationJump.load("/model/clone/Jump-Animation.png", 2, 150);
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
