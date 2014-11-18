package game.level.mapobject;

import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectLasergateHorizontal extends MapObject {

	protected BufferedImage[] images;

	public MapObjectLasergateHorizontal(byte id, int x, int y, int width, boolean closed) {
		super(id, x, y, width, 1, closed);
		if (width < 2) {
			width = 2;
		}
		loadTexture();
	}

	public MapObjectLasergateHorizontal() {
		loadTexture();
	}

	protected void loadTexture() {
		BufferedImage image = ResourceManager.getImage("/level/object/Lasergate-Hrizontal.png");
		images = new BufferedImage[6];
		images[0] = image.getSubimage(0, 0, 16, 16);
		images[1] = image.getSubimage(16, 0, 16, 16);
		images[2] = image.getSubimage(32, 0, 16, 16);
		images[3] = image.getSubimage(0, 16, 16, 16);
		images[4] = image.getSubimage(16, 16, 16, 16);
		images[5] = image.getSubimage(32, 16, 16, 16);
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
