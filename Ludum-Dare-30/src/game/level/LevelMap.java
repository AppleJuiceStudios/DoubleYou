package game.level;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelMap {

	private byte[][] spritesheet;
	private int width;
	private int height;

	private LevelMap() {

	}

	public byte getTileID(int x, int y) {
		return spritesheet[x][y];
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

}
