package game.staging;

import game.level.EntityPlayer;
import game.level.LevelMap;
import game.level.TileSet;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class StageLevel extends Stage {

	public static final int SCALE = 3;

	private BufferedImage background;
	private BufferedImage mountains;
	private LevelMap map;
	private TileSet tileSet;

	private Timer updateTimer;

	private EntityPlayer player;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		tileSet = new TileSet();
		map = new LevelMap();
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/Space-Background.png"));
			mountains = ImageIO.read(getClass().getResourceAsStream("/planets/mars/Mars-Mountains.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = new EntityPlayer(97, 145);
		updateTimer = new Timer();
		updateTimer.schedule(new TimerTask() {
			public void run() {
				update();
			}
		}, 0, 1000 / 60);
		initListeners();
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1600, 800, null);
		g2.drawImage(mountains, 0, -190, 800, 800, null);
		int spriteSize = TileSet.SPRITE_SIZE * SCALE;
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				BufferedImage image = tileSet.getSprite(map.getTileID(x, y));
				if (image != null) {
					g2.drawImage(image, x * spriteSize, y * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}
		player.draw(g2);
	}

	public void update() {
		player.update(map);
	}

	public void stop() {

	}

	private void initListeners() {
		getStageManager().setKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {
				player.keyReleased(e);
			}

			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
			}
		});
	}

}
