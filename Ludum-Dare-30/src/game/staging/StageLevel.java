package game.staging;

import game.level.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

public class StageLevel extends Stage {

	private BufferedImage background;
	private BufferedImage mountains;
	private LevelMap map;
	private TileSet tileSet;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		tileSet = new TileSet();
		map = new LevelMap();
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/Space-Background.png"));
			mountains = ImageIO.read(getClass().getResourceAsStream("/Mars-Mountains.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1600, 800, null);
		g2.drawImage(mountains, 0, -200, 800, 800, null);
		int spriteSize = TileSet.SPRITE_SIZE * 3;
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				BufferedImage image = tileSet.getSprite(map.getTileID(x, y));
				if (image != null) {
					g2.drawImage(image, x * spriteSize, y * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}
	}

	public void update() {

	}

	public void stop() {

	}

}
