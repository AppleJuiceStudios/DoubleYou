package game.level.mapobject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MapObjectDecoration extends MapObject {

	protected BufferedImage image;

	public MapObjectDecoration(int id, int x, int y, int width, int height, boolean power) {
		super(id, x, y, 1, 2, false);
		loadTexture();
	}

	public MapObjectDecoration() {
		loadTexture();
	}

	protected void loadTexture() {

	}

	public boolean isSolid() {
		return false;
	}

	public void draw(Graphics2D g2, int size) {
		g2.drawImage(image, x * size, y * size, width * size, height * size, null);
	}
}
