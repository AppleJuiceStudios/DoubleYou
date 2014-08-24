package game.level.map;

import game.level.EntityPlayer;
import game.level.mapobject.MapObject;

import java.awt.Graphics2D;
import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelMap {

	private int playerSpawnX;
	private int playerSpawnY;

	private byte[][] spritesheet;
	private int width;
	private int height;

	protected MapObject[] objects;

	public LevelMap() {
		init();
	}

	public void init() {
		objects = new MapObject[0];
	}

	public void drawObjects(Graphics2D g2) {
		for (int i = 0; i < objects.length; i++) {
			objects[i].draw(g2);
		}
	}

	public void updateTriger(EntityPlayer... player) {
		for (int i = 0; i < objects.length; i++) {
			objects[i].updateTriger(player, this);
		}
	}

	public void powerObject(byte id, boolean power) {
		objects[id - 32].setPower(power, this);
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
		JAXB.marshal(this, new File("/res/level/" + name + ".xml"));
	}

	public static LevelMap loadLevel(String name) {
		if (name.equals("S1L1")) {
			return JAXB.unmarshal(LevelMap.class.getResourceAsStream("/level/level11.xml"), LevelMap11.class);
		} else {
			return JAXB.unmarshal(LevelMap.class.getResourceAsStream("/level/test.xml"), LevelMapTest.class);
		}
	}

	public MapObject getMapObject(byte id) {
		return objects[id - 32];
	}

	public int getPlayerSpawnX() {
		return playerSpawnX;
	}

	public void setPlayerSpawnX(int playerSpawnX) {
		this.playerSpawnX = playerSpawnX;
	}

	public int getPlayerSpawnY() {
		return playerSpawnY;
	}

	public void setPlayerSpawnY(int playerSpawnY) {
		this.playerSpawnY = playerSpawnY;
	}

}
