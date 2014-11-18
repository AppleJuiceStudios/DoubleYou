package game.level.mapobject;

import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectLasergate extends MapObject {

	protected BufferedImage[] images;

	public MapObjectLasergate(byte id, int x, int y, int height, boolean closed) {
		super(id, x, y, 1, height, closed);
		if (height < 2) {
			height = 2;
		}
		loadTexture();
	}

	public MapObjectLasergate() {
		loadTexture();
	}

	protected void loadTexture() {
		BufferedImage image = ResourceManager.getImage("/level/object/Lasergate.png");
		images = new BufferedImage[6];
		images[0] = image.getSubimage(0, 0, 16, 16);
		images[1] = image.getSubimage(0, 16, 16, 16);
		images[2] = image.getSubimage(0, 32, 16, 16);
		images[3] = image.getSubimage(16, 0, 16, 16);
		images[4] = image.getSubimage(16, 16, 16, 16);
		images[5] = image.getSubimage(16, 32, 16, 16);
	}

	public boolean isSolid() {
		return power;
	}

	public void draw(Graphics2D g2) {
		int scale = 16 * 3;
		if (power) {
			g2.drawImage(images[0], x * scale, y * scale, scale, scale, null);
			for (int i = 1; i < height - 1; i++) {
				g2.drawImage(images[1], x * scale, (y + i) * scale, scale, scale, null);
			}
			g2.drawImage(images[2], x * scale, (y + height - 1) * scale, scale, scale, null);
		} else {
			g2.drawImage(images[3], x * scale, y * scale, scale, scale, null);
			for (int i = 1; i < height - 1; i++) {
				g2.drawImage(images[4], x * scale, (y + i) * scale, scale, scale, null);
			}
			g2.drawImage(images[5], x * scale, (y + height - 1) * scale, scale, scale, null);
		}
	}

}
