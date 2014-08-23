package game.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class EntityPlayerRecord extends EntityPlayer {

	private List<Byte> recording;

	public EntityPlayerRecord(double x, double y) {
		super(x, y);
		health = 3;
		animation = new Animation();
		animation.load("/model/clone/Run-Animation.png", 2, 150);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/model/clone/Player-Model.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
