package game.staging;

import game.level.TileSet;
import game.levelEditor.LevelMapEditable;
import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import javax.xml.bind.JAXB;

public class StageLevelEditor extends Stage {

	private File levelFile;
	private LevelMapEditable map;

	private int scale = 3;
	private double xOffset = 0;
	private double yOffset = 0;

	private BufferedImage background;
	private BufferedImage mountains;
	private BufferedImage player;
	private TileSet tileSet;

	public StageLevelEditor(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		loadMap(data);
		background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
		mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
		player = ResourceManager.getImage("/model/player/Player-Model.png");
		tileSet = new TileSet("/planets/mars/Mars-TileSet.png");
	}

	private void loadMap(Map<String, String> data) {
		File file = new File(data.get("file"));
		levelFile = file;
		if (file.exists()) {
			map = JAXB.unmarshal(file, LevelMapEditable.class);
		} else {
			throw new IllegalArgumentException("File not found: " + file.getPath());
		}
	}

	public void draw(Graphics2D g2) {

		drawBackground(g2, background);
		double mountainsOffset = 0;
		for (int i = (int) -mountainsOffset / (260 * 3); i <= ((int) -mountainsOffset + GameCanvas.WIDTH) / (260 * 3); i++) {
			g2.drawImage(mountains, i * 260 * 3 + (int) mountainsOffset, GameCanvas.HEIGHT - 260 * 3, 260 * 3, 260 * 3, null);
		}

		AffineTransform at = new AffineTransform();
		at.translate((int) -xOffset, (int) -yOffset);
		g2.setTransform(at);
		int spriteSize = TileSet.SPRITE_SIZE * scale;

		// Map
		int xStart = (int) (xOffset / (spriteSize));
		int yStart = (int) (yOffset / (spriteSize));
		int xEnd = xStart + GameCanvas.WIDTH / (spriteSize) + 2;
		int yEnd = yStart + GameCanvas.HEIGHT / (spriteSize) + 2;
		xStart = Math.max(0, xStart);
		yStart = Math.max(0, yStart);
		xEnd = Math.min(map.getWidth(), xEnd);
		yEnd = Math.min(map.getHeight(), yEnd);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				BufferedImage image = tileSet.getSprite(map.getTileID(x, y));
				if (image != null) {
					g2.drawImage(image, x * spriteSize, y * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}

		// Player Spawn
		g2.drawImage(player, map.getPlayerSpawnX() * spriteSize / 16, map.getPlayerSpawnY() * spriteSize / 16, spriteSize, spriteSize * 2, null);

		// Border
		g2.setColor(Color.YELLOW);
		for (int i = 1; i <= 1 + scale; i++) {
			g2.drawRect(0 - i, 0 - i, map.getWidth() * spriteSize + i * 2 - 1, map.getHeight() * spriteSize + i * 2 - 1);
		}

		// Objects
		map.drawObjects(g2, spriteSize);

		g2.setTransform(new AffineTransform());
	}

	public void stop() {

	}

}
