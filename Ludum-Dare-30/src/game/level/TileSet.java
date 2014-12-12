package game.level;

import game.res.ResourceManager;

import java.awt.image.BufferedImage;

public class TileSet {

	public static final byte TILE_AIR = 0;
	public static final byte TILE_NORTH_WEST = 1;
	public static final byte TILE_NORTH = 2;
	public static final byte TILE_NORTH_EAST = 3;
	public static final byte TILE_WEST = 4;
	public static final byte TILE_CENTER = 5;
	public static final byte TILE_EAST = 6;
	public static final byte TILE_SOUTH_WEST = 7;
	public static final byte TILE_SOUTH = 8;
	public static final byte TILE_SOUTH_EAST = 9;

	private BufferedImage[] sprites;

	public static final int SPRITE_SIZE = 16;

	public TileSet(String path) {
		sprites = new BufferedImage[9];
		BufferedImage image = ResourceManager.getImage(path);
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				sprites[y * 3 + x] = image.getSubimage(x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
			}
		}
	}

	public BufferedImage getSprite(int id) {
		if (id > 0 & id < 10) {
			return sprites[id - 1];
		} else {
			return null;
		}

	}
}
