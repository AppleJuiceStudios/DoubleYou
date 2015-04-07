package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.Entity;
import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MapObjectSpike extends MapObject {

	private BufferedImage image;

	public MapObjectSpike(int id, int x, int y, int width, int height, boolean power) {
		super(id, x, y, 1, 1, false);
		loadTextures();
	}

	public MapObjectSpike() {
		loadTextures();
	}

	protected void loadTextures() {
		image = ResourceManager.getImage("/level/object/spike.png");
	}

	public void draw(Graphics2D g2, int size) {
		g2.drawImage(image, x * size, y * size, size, size, null);
	}

	public void updateTriger(LevelMap map, Entity... entities) {
		for (int i = 0; i < entities.length; i++) {
			if (entities[i] != null) {
				if (x == (int) ((entities[i].getXPos() + (entities[i].getWidth() / 2)) / 16)
						& y == (int) ((entities[i].getYPos() + entities[i].getHeight() - 1) / 16)) {
					entities[i].damage(1, map);
				}
			}
		}
	}
}
