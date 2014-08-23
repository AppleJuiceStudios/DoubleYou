package game.level;

import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityPlayerRecord extends EntityPlayer {

	public EntityPlayerRecord(double x, double y) {
		super(x, y);
		health = 3;
		animation = new Animation();
		animation.load("/model/player/Run-Animation.png", 2, 150);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/model/player/Player-Model.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
