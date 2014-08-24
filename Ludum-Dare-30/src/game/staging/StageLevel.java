package game.staging;

import game.level.EntityPlayer;
import game.level.EntityPlayerClone;
import game.level.EntityPlayerRecord;
import game.level.LevelMap;
import game.level.TileSet;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class StageLevel extends Stage {

	public static final int SCALE = 3;
	private double xOffset = 0;
	private double yOffset = 10;
	private double maxXOffset;
	private double maxYOffset;

	private BufferedImage background;
	private BufferedImage mountains;
	private BufferedImage healthbar;
	private LevelMap map;
	private TileSet tileSet;

	private Timer updateTimer;

	private boolean isRecording;
	private boolean isCloneMoving;
	private EntityPlayer player;
	private EntityPlayerRecord playerRecord;
	private EntityPlayerClone playerClone;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		tileSet = new TileSet();
		map = LevelMap.loadLevel("test");
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Space-Background.png"));
			mountains = ImageIO.read(getClass().getResourceAsStream("/planets/mars/Mars-Mountains.png"));
			healthbar = ImageIO.read(getClass().getResourceAsStream("/Healthbar.png"));
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
		maxXOffset = (map.getWidth() * TileSet.SPRITE_SIZE * SCALE) - 800;
		maxYOffset = (map.getHeight() * TileSet.SPRITE_SIZE * SCALE) - 600;
	}

	public void draw(Graphics2D g2) {
		if (isRecording) {
			xOffset += (playerRecord.getXPos() * SCALE - (800 / 2) - xOffset) * 0.2;
			yOffset += (playerRecord.getYPos() * SCALE - (600 / 3) - yOffset) * 0.2;
		} else {
			xOffset += (player.getXPos() * SCALE - (800 / 2) - xOffset) * 0.2;
			yOffset += (player.getYPos() * SCALE - (600 / 3) - yOffset) * 0.2;
		}
		if (xOffset > maxXOffset) {
			xOffset = maxXOffset;
		} else if (xOffset < 0) {
			xOffset = 0;
		}
		if (yOffset > maxYOffset) {
			yOffset = maxYOffset;
		} else if (yOffset < 0) {
			yOffset = 0;
		}
		g2.drawImage(background, 0, 0, 1600, 800, null);
		double mountainsOffset = -xOffset * 0.3;
		g2.drawImage(mountains, (int) (mountainsOffset % 800) + 800, -190, 800, 800, null);
		g2.drawImage(mountains, (int) (mountainsOffset % 800), -190, 800, 800, null);
		AffineTransform at = new AffineTransform();
		at.translate((int) -xOffset, (int) -yOffset);
		g2.setTransform(at);
		int spriteSize = TileSet.SPRITE_SIZE * SCALE;
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				BufferedImage image = tileSet.getSprite(map.getTileID(x, y));
				if (image != null) {
					g2.drawImage(image, x * spriteSize, y * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}
		try {
			if (isRecording) {
				player.draw(g2, false);
				playerRecord.draw(g2, true);
			} else {
				player.draw(g2, true);
			}
			if (isCloneMoving) {
				playerClone.draw(g2, true);
			}
		} catch (NullPointerException e) {}
		map.drawObjects(g2);
		g2.setTransform(new AffineTransform());
		/**
		 * GUI
		 */
		BufferedImage health = healthbar.getSubimage(0, (player.health - 1) * 20, healthbar.getWidth(), 20);
		g2.drawImage(health, 10, 10, health.getWidth() * 2, health.getHeight() * 2, null);
	}

	public void update() {
		try {
			if (isRecording) {
				playerRecord.update(map);
			} else {
				player.update(map);
				if (isCloneMoving) {
					playerClone.update(map);
					if (playerClone.isDead()) {
						isCloneMoving = false;
						playerClone = null;
					}
				}
			}
			if (isCloneMoving) {
				map.updateTriger(player, playerClone);
			} else {
				map.updateTriger(player);
			}
		} catch (NullPointerException e) {}
	}

	public void stop() {

	}

	private void initListeners() {
		getStageManager().setKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {
				player.keyReleased(e);
				if (isRecording) {
					playerRecord.keyReleased(e);
				}
			}

			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
				if (isRecording) {
					playerRecord.keyPressed(e);
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (isRecording) {
						playerClone = new EntityPlayerClone(player.getXPos(), player.getYPos(), playerRecord.getRecording());
						isRecording = false;
						isCloneMoving = true;
						playerRecord = null;
					} else {
						if (isCloneMoving) {
							isCloneMoving = false;
							playerClone = null;
						} else {
							playerRecord = player.createRecord();
							isRecording = true;
						}
					}
				}
			}
		});
	}

}
