package game.level;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelMap {

	private byte[][] spritesheet;
	private int width;
	private int height;

	public LevelMap() {
		byte[][] map = new byte[50][13];
		for (int x = 0; x < map.length; x++) {
			map[x][12] = 5;
		}
		for (int x = 0; x < map.length; x++) {
			map[x][11] = 2;
		}
		map[0][10] = 5;
		map[0][9] = 5;
		map[10][10] = 5;
		map[10][9] = 5;
		setSpritesheet(map);
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

	public boolean isSolidTile(int x, int y) {
		int id = getTileID(x, y);
		return id > 0 & id < 10;
	}

}
