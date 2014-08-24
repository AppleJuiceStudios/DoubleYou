package game.level;

import java.awt.Graphics2D;
import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelMap {

	private byte[][] spritesheet;
	private int width;
	private int height;

	private MapObject[] objects;

	public LevelMap() {
		byte[][] map = new byte[50][13];
		for (int x = 0; x < map.length; x++) {
			map[x][12] = 5;
		}
		for (int x = 0; x < map.length; x++) {
			map[x][11] = 2;
		}
		for (int y = 0; y < 11; y++) {
			map[0][y] = 6;
		}
		for (int y = 0; y < 11; y++) {
			map[49][y] = 4;
		}
		map[0][11] = 5;
		map[49][11] = 5;
		map[5][10] = 32;

		objects = new MapObject[1];
		objects[0] = new MapObject((byte) 32, 5, 10, 1, 1, false);

		setSpritesheet(map);
		save("test");
	}

	public void drawObjects(Graphics2D g2) {
		for (int i = 0; i < objects.length; i++) {
			objects[i].draw(g2);
		}
	}

	public void updateTriger(EntityPlayer... player) {
		for (int i = 0; i < objects.length; i++) {
			objects[i].updateTriger(player);
		}
	}

	public byte getTileID(int x, int y) {
		if (x >= 0 & x < width & y >= 0 & y < height) {
			return spritesheet[x][y];
		} else {
			return 5;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte[][] getSpritesheet() {
		return spritesheet;
	}

	public void setSpritesheet(byte[][] spritesheet) {
		this.spritesheet = spritesheet;
		width = spritesheet.length;
		height = spritesheet[0].length;
	}

	public boolean isBlock(int x, int y) {
		int id = getTileID(x, y);
		return id > 0 & id < 10;
	}

	public boolean isSolidTile(int x, int y) {
		int id = getTileID(x, y);
		if (id < 32) {
			return id > 0 & id < 10;
		} else {
			return objects[id - 32].isSolid();
		}

	}

	public void save(String name) {
		JAXB.marshal(this, new File("res/level/" + name + ".xml"));
	}

	public static LevelMap loadLevel(String name) {
		return JAXB.unmarshal(new File("res/level/" + name + ".xml"), LevelMap.class);
	}

	public MapObject[] getObjects() {
		return objects;
	}

	public void setObjects(MapObject[] objects) {
		this.objects = objects;
	}

}
