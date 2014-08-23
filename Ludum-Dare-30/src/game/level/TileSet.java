package game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileSet {

	private BufferedImage[] sprites;

	public static final int SPRITE_SIZE = 16;

	public TileSet() {
		try {
			sprites = new BufferedImage[9];
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Mars-TileSet.png"));
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					sprites[y * 3 + x] = image.getSubimage(x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
