package game.level.mapobject;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapObjectLasergateHorizontalClone extends MapObjectLasergateHorizontal {

	public MapObjectLasergateHorizontalClone(byte id, int x, int y, int width, boolean closed) {
		super(id, x, y, width, closed);
	}

	protected void loadTexture() {
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/level/object/Lasergate-HrizontalOnlyClone.png"));
			images = new BufferedImage[6];
			images[0] = image.getSubimage(0, 0, 16, 16);
			images[1] = image.getSubimage(16, 0, 16, 16);
			images[2] = image.getSubimage(32, 0, 16, 16);
			images[3] = image.getSubimage(0, 16, 16, 16);
			images[4] = image.getSubimage(16, 16, 16, 16);
			images[5] = image.getSubimage(32, 16, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isCloneSolid() {
		return false;
	}

}
