package util.hud;

import game.level.mapobject.MapObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class HudItem {

	private BufferedImage image;
	private MapObject object;
	private int size;

	public HudItem(BufferedImage image, MapObject object) {
		this.setImage(image);
		this.setObject(object);

		size = 40;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public MapObject getObject() {
		return object;
	}

	public void setObject(MapObject object) {
		this.object = object;
	}

	public void draw(Graphics2D g2, int x, int y) {
		g2.setColor(Color.WHITE);
		g2.drawImage(image, x, y, size, size, null);
	}
}
