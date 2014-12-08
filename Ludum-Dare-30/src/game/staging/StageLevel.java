package game.staging;

import game.level.LevelMap;
import game.level.Textbox;
import game.level.TileSet;
import game.level.entity.EntityPlayer;
import game.level.entity.EntityPlayerClone;
import game.level.entity.EntityPlayerRecord;
import game.main.GameCanvas;
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

import util.log.Log;
import de.Auch.Monitoring;

public class StageLevel extends Stage {

	public static final int SCALE = 3;
	private double xOffset = 0;
	private double yOffset = 0;
	private double maxXOffset;
	private double maxYOffset;

	private BufferedImage background;
	private BufferedImage mountains;
	private BufferedImage healthbar;

	private BufferedImage[] chooseClone;

	private LevelMap map;
	private TileSet tileSet;

	private Timer updateTimer;

	private boolean isCloneAllowed;

	private boolean isRecording;
	private boolean isCloneMoving;
	private EntityPlayer player;
	private EntityPlayerRecord playerRecord;
	private EntityPlayerClone playerClone;
	private int selectedClone;

	public Textbox textbox;
	private BufferedImage imgTextbox;
	private boolean hasTextbox;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		try {
			if (data.get("level").matches("\\d*"))
				map = LevelMap.loadLevel(Integer.parseInt(data.get("level")));
			else
				map = LevelMap.loadLevel(new File(data.get("level")));

			Log.info("Loaded Level " + data.get("level"));
		} catch (IllegalArgumentException e) {
			Log.error("Error loading Level: " + data.get("level"));
			throw new IllegalArgumentException(e);
		}
		map.setStageLevel(this);
		map.start();

		SoundManager.loadClipInCache("soundTrack", map.getSoundTrack());
		SoundManager.play("soundTrack", true);

		isCloneAllowed = map.getIsCloneAllowed();
		textbox = map.getStartTextbox();

		System.out.println(Integer.parseInt(data.get("level")));
		if (Integer.parseInt(data.get("level")) <= 4) {
			tileSet = new TileSet("/planets/mars/Mars-TileSet.png");
			background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
			mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
		} else {
			tileSet = new TileSet("/planets/saturn/Saturn-TileSet.png");
			background = ResourceManager.getImage("/backgrounds/Saturn-Background.png");
			mountains = ResourceManager.getImage("/planets/saturn/Saturn-Outlands.png");
		}
		healthbar = ResourceManager.getImage("/gui/Healthbar.png");
		imgTextbox = ResourceManager.getImage("/backgrounds/Textbox.png");

		chooseClone = new BufferedImage[4];
		for (int i = 0; i < 4; i++)
			chooseClone[i] = ResourceManager.getImage("/gui/CloneChoose" + (i + 1) + ".png");
		selectedClone = 1;

		player = new EntityPlayer(map.getPlayerSpawnX(), map.getPlayerSpawnY());

		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				update();
			}
		}, 0, 1000 / 60);

		initListeners();

		maxXOffset = (map.getWidth() * TileSet.SPRITE_SIZE * SCALE) - GameCanvas.WIDTH;
		maxYOffset = (map.getHeight() * TileSet.SPRITE_SIZE * SCALE) - GameCanvas.HEIGHT;
	}

	public void draw(Graphics2D g2) {
		if (isRecording) {
			xOffset += (playerRecord.getXPos() * SCALE - (GameCanvas.WIDTH / 2) - xOffset) * 0.2;
			yOffset += (playerRecord.getYPos() * SCALE - (GameCanvas.HEIGHT / 3) - yOffset) * 0.2;
		} else {
			xOffset += (player.getXPos() * SCALE - (GameCanvas.WIDTH / 2) - xOffset) * 0.2;
			yOffset += (player.getYPos() * SCALE - (GameCanvas.HEIGHT / 3) - yOffset) * 0.2;
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
		g2.drawImage(background, 0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT, null);
		double mountainsOffset = -xOffset * 0.3;
		g2.drawImage(mountains, (int) (mountainsOffset % GameCanvas.WIDTH) + GameCanvas.WIDTH, -190, GameCanvas.WIDTH, GameCanvas.WIDTH, null);
		g2.drawImage(mountains, (int) (mountainsOffset % GameCanvas.WIDTH), -190, GameCanvas.WIDTH, GameCanvas.WIDTH, null);
		AffineTransform at = new AffineTransform();
		at.translate((int) -xOffset, (int) -yOffset);
		g2.setTransform(at);
		int spriteSize = TileSet.SPRITE_SIZE * SCALE;

		int xStart = (int) (xOffset / spriteSize);
		int yStart = (int) (yOffset / spriteSize);
		int xEnd = xStart + GameCanvas.WIDTH / spriteSize + 2;
		int yEnd = yStart + GameCanvas.HEIGHT / spriteSize + 2;

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
		} catch (NullPointerException e) {
		}
		map.drawObjects(g2, spriteSize);
		g2.setTransform(new AffineTransform());

		// GUI
		BufferedImage health = healthbar.getSubimage(0, (player.health - 1) * 20, healthbar.getWidth(), 20);
		g2.drawImage(health, 10, 10, health.getWidth() * 2, health.getHeight() * 2, null);
		g2.drawImage(chooseClone[selectedClone], GameCanvas.WIDTH - chooseClone[selectedClone].getWidth() * 2 - 10, 10,
				chooseClone[selectedClone].getWidth() * 2, chooseClone[selectedClone].getHeight() * 2, null);
		drawTextbox(g2);
	}

	private void drawTextbox(Graphics2D g2) {
		if (textbox != null) {
			if (textbox.hasNextPage()) {
				hasTextbox = true;

				g2.drawImage(imgTextbox, 100, 425, GameCanvas.HEIGHT, 150, null);
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
		Monitoring.start(2);
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
		} catch (NullPointerException e) {
		}
		Monitoring.stop(2);
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
