package game.level.mapobject;

import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectFinishFlag extends MapObject {

	protected BufferedImage image;

	public MapObjectFinishFlag(int id, int x, int y, int width, int height, boolean power) {
		super(id, x, y, 1, 2, false);
		loadTexture();
	}

	public MapObjectFinishFlag() {
		loadTexture();
	}

	protected void loadTexture() {
		image = ResourceManager.getImage("/model/finishFlag.png");
	}

	public boolean isSolid() {
		return false;
	}

	public void draw(Graphics2D g2, int size) {
		g2.drawImage(image, x * size, y * size, size, size, null);
	}

}
