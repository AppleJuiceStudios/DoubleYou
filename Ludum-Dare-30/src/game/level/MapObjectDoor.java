package game.level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapObjectDoor extends MapObject {

	private BufferedImage[] images;

	public MapObjectDoor(byte id, int x, int y, int height, boolean closed) {
		super(id, x, y, 1, height, closed);
		if (height < 2) {
			height = 2;
		}
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/level/object/Door.png"));
			images = new BufferedImage[3];
			images[0] = image.getSubimage(0, 0, 16, 16);
			images[1] = image.getSubimage(0, 16, 16, 16);
			images[2] = image.getSubimage(0, 32, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isSolid() {
		return power;
	}

	public void draw(Graphics2D g2) {
		int scale = 16 * 3;
		g2.drawImage(images[0], x * scale, y * scale, scale, scale, null);
		for (int i = 1; i < height - 1; i++) {
			g2.drawImage(images[1], x * scale, (y + i) * scale, scale, scale, null);
		}
		g2.drawImage(images[2], x * scale, (y + height - 1) * scale, scale, scale, null);
	}

}
