package game.level.mapobject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapObjectLasergateHorizontal extends MapObject {

	private BufferedImage[] images;

	public MapObjectLasergateHorizontal(byte id, int x, int y, int width, boolean closed) {
		super(id, x, y, width, 1, closed);
		if (width < 2) {
			width = 2;
		}
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/level/object/Lasergate-Horizontal.png"));
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

	public boolean isSolid() {
		return power;
	}

	public void draw(Graphics2D g2) {
		int scale = 16 * 3;
		if (power) {
			g2.drawImage(images[0], x * scale, y * scale, scale, scale, null);
			for (int i = 1; i < width - 1; i++) {
				g2.drawImage(images[1], (x + i) * scale, y * scale, scale, scale, null);
			}
			g2.drawImage(images[2], (x + width - 1) * scale, y * scale, scale, scale, null);
		} else {
			g2.drawImage(images[3], x * scale, y * scale, scale, scale, null);
			for (int i = 1; i < width - 1; i++) {
				g2.drawImage(images[4], (x + i) * scale, y * scale, scale, scale, null);
			}
			g2.drawImage(images[5], (x + width - 1) * scale, y * scale, scale, scale, null);
		}
	}

}
