package util.hud;

import game.level.mapobject.MapObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.Log;

public class HudItem {

	private BufferedImage image;
	private Class<? extends MapObject> object;
	public static final int SIZE = 40;

	public HudItem(BufferedImage image, Class<? extends MapObject> object) {
		this.setImage(image);
		this.setObject(object);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public MapObject getObject() {
		try {
			return object.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			Log.error("Error creating MapObject!");
			return null;
		}
	}

	public void setObject(Class<? extends MapObject> object) {
		this.object = object;
	}

	public void draw(Graphics2D g2, int x, int y) {
		g2.setColor(Color.WHITE);
		g2.drawImage(image, x, y, SIZE, SIZE, null);
	}
}
