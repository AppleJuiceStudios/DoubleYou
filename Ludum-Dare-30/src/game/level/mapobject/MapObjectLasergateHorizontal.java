package game.level.mapobject;

import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectLasergateHorizontal extends MapObject {

	protected BufferedImage[] images;

	public MapObjectLasergateHorizontal(int id, int x, int y, int width, boolean closed) {
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
		BufferedImage image = ResourceManager.getImage("/level/object/Lasergate-Horizontal.png");
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

	public void draw(Graphics2D g2, int size) {
		if (power) {
			g2.drawImage(images[0], x * size, y * size, size, size, null);
			for (int i = 1; i < width - 1; i++) {
				g2.drawImage(images[1], (x + i) * size, y * size, size, size, null);
			}
			g2.drawImage(images[2], (x + width - 1) * size, y * size, size, size, null);
		} else {
			g2.drawImage(images[3], x * size, y * size, size, size, null);
			for (int i = 1; i < width - 1; i++) {
				g2.drawImage(images[4], (x + i) * size, y * size, size, size, null);
			}
			g2.drawImage(images[5], (x + width - 1) * size, y * size, size, size, null);
		}
	}

	protected void drawIO(Graphics2D g2, int size) {
		drawInput(g2, width * size / 2 - (int) (1.5 * size / 16) + size * x, size * y, size / 16 * 3, power);
	}

	public int inputCount() {
		return 1;
	}

}
