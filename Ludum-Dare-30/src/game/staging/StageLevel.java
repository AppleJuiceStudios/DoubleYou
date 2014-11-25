package game.staging;

import game.level.LevelMap;
import game.level.Textbox;
import game.level.TileSet;
import game.level.entity.EntityPlayer;
import game.level.entity.EntityPlayerClone;
import game.level.entity.EntityPlayerRecord;
import game.main.GameCanvas;
import game.main.Monitoring;
import game.res.ResourceManager;
import game.res.SoundManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class StageLevel extends Stage {

	public static final int SCALE = 3;
	private double xOffset = 0;
	private double yOffset = 0;
	private double maxXOffset;
	private double maxYOffset;

	private BufferedImage background;
	private BufferedImage mountains;
	private BufferedImage healthbar;
	private LevelMap map;
	private TileSet tileSet;

	private Timer updateTimer;

	private boolean isCloneAllowed;

	private boolean isRecording;
	private boolean isCloneMoving;
	private EntityPlayer player;
	private EntityPlayerRecord playerRecord;
	private EntityPlayerClone playerClone;

	public Textbox textbox;
	private BufferedImage imgTextbox;
	private boolean hasTextbox;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		tileSet = new TileSet();
		try {
			if (data.get("level").matches("\\d*")) {
				map = LevelMap.loadLevel(Integer.parseInt(data.get("level")));
			} else {
				map = LevelMap.loadLevel(new File(data.get("level")));
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
		map.setStageLevel(this);
		map.start();
		SoundManager.loadClipInCache("soundTrack", map.getSoundTrack());
		SoundManager.play("soundTrack", true);
		isCloneAllowed = map.getIsCloneAllowed();
		textbox = map.getStartTextbox();
		background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
		mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
		healthbar = ResourceManager.getImage("/Healthbar.png");
		imgTextbox = ResourceManager.getImage("/backgrounds/Textbox.png");
		player = new EntityPlayer(map.getPlayerSpawnX(), map.getPlayerSpawnY());
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
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
		g2.drawImage(background, 0, 0, 800, 600, null);
		double mountainsOffset = -xOffset * 0.3;
		g2.drawImage(mountains, (int) (mountainsOffset % 800) + 800, -190, 800, 800, null);
		g2.drawImage(mountains, (int) (mountainsOffset % 800), -190, 800, 800, null);
		AffineTransform at = new AffineTransform();
		at.translate((int) -xOffset, (int) -yOffset);
		g2.setTransform(at);
		int spriteSize = TileSet.SPRITE_SIZE * SCALE;

		int xStart = (int) (xOffset / (TileSet.SPRITE_SIZE * SCALE));
		int yStart = (int) (yOffset / (TileSet.SPRITE_SIZE * SCALE));
		int xEnd = xStart + GameCanvas.WIDTH / (TileSet.SPRITE_SIZE * SCALE) + 2;
		int yEnd = yStart + GameCanvas.HEIGHT / (TileSet.SPRITE_SIZE * SCALE) + 2;

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
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
		drawTextbox(g2);
	}

	private void drawTextbox(Graphics2D g2) {
		if (textbox != null) {
			if (textbox.hasNextPage()) {
				hasTextbox = true;

				g2.drawImage(imgTextbox, 100, 425, 600, 150, null);
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Garamond", Font.BOLD, 30));
				String[] line = textbox.getPage();
				for (int i = 0; i < line.length; i++) {
					g2.drawString(line[i], 130, 475 + (i * 32));
				}
			} else {
				hasTextbox = false;
				textbox = null;
			}
		}
	}

	public void update() {
		Monitoring.startPhysics();
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
		Monitoring.stopPhysics();
	}

	public void stop() {
		updateTimer.cancel();
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
				if (e.getKeyCode() == KeyEvent.VK_SPACE & isCloneAllowed) {
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER && hasTextbox) {
					textbox.nextPage();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					getStageManager().setStage(StageManager.STAGE_CHOOSE_LEVEL);
				}
			}
		});
	}

	public boolean isRecording() {
		return isRecording;
	}

	public boolean isCloneMoving() {
		return isCloneMoving;
	}

}
