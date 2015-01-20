package game.staging;

import game.level.LevelMap;
import game.level.Textbox;
import game.level.TileSet;
import game.level.entity.Entity;
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
import java.util.ArrayList;
import java.util.List;
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

	private boolean[] isCloneAllowed;

	private boolean isRecording;
	private boolean[] isCloneMoving;
	private EntityPlayer player;
	private EntityPlayerRecord[] playerRecord;
	private EntityPlayerClone[] playerClone;
	private int selectedClone;

	protected List<Entity> entities;

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

		selectedClone = 0;
		isCloneAllowed = new boolean[4];
		isCloneMoving = new boolean[4];
		playerRecord = new EntityPlayerRecord[4];
		playerClone = new EntityPlayerClone[4];

		isCloneAllowed[0] = map.getIsCloneAllowed();
		textbox = map.getStartTextbox();

		try {
			if (Integer.parseInt(data.get("level")) <= 4) {
				tileSet = new TileSet("/planets/mars/Mars-TileSet.png");
				background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
				mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
			} else {
				tileSet = new TileSet("/planets/saturn/Saturn-TileSet.png");
				background = ResourceManager.getImage("/backgrounds/Saturn-Background.png");
				mountains = ResourceManager.getImage("/planets/saturn/Saturn-Outlands.png");
			}

		} catch (NumberFormatException ex) {
			tileSet = new TileSet("/planets/mars/Mars-TileSet.png");
			background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
			mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
		}
		healthbar = ResourceManager.getImage("/gui/Healthbar.png");
		imgTextbox = ResourceManager.getImage("/backgrounds/Textbox.png");

		chooseClone = new BufferedImage[4];
		for (int i = 0; i < 4; i++)
			chooseClone[i] = ResourceManager.getImage("/gui/CloneChoose" + (i + 1) + ".png");

		player = new EntityPlayer(map.getPlayerSpawnX(), map.getPlayerSpawnY());
		entities = new ArrayList<>();

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
			xOffset += (playerRecord[selectedClone].getXPos() * SCALE - (GameCanvas.WIDTH / 2) - xOffset) * 0.2;
			yOffset += (playerRecord[selectedClone].getYPos() * SCALE - (GameCanvas.HEIGHT / 3) - yOffset) * 0.2;
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
				playerRecord[selectedClone].draw(g2, true);
			} else {
				player.draw(g2, true);
			}
			for (int i = 0; i < playerClone.length; i++) {
				if (isCloneMoving[i]) {
					playerClone[i].draw(g2, true);
				}
			}
		} catch (NullPointerException e) {}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2, true);
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
				playerRecord[selectedClone].update(map);
			} else {
				player.update(map);
				for (int i = 0; i < isCloneMoving.length; i++) {
					if (isCloneMoving[i]) {
						playerClone[i].update(map);
						if (playerClone[i].isDead()) {
							isCloneMoving[i] = false;
							playerClone[i] = null;
						}
					}
				}
			}
			map.updateTriger(player, isCloneMoving[0] ? playerClone[0] : null, isCloneMoving[1] ? playerClone[1] : null, isCloneMoving[2] ? playerClone[2] : null,
					isCloneMoving[3] ? playerClone[3] : null);
		} catch (NullPointerException e) {}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(map);
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
					playerRecord[selectedClone].keyReleased(e);
				}
			}

			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
				if (isRecording) {
					playerRecord[selectedClone].keyPressed(e);
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE & isCloneAllowed[selectedClone]) {
					if (isRecording) {
						playerClone[selectedClone] = new EntityPlayerClone(player.getXPos(), player.getYPos(), playerRecord[selectedClone].getRecording());
						isRecording = false;
						isCloneMoving[selectedClone] = true;
						playerRecord[selectedClone] = null;
					} else {
						if (isCloneMoving[selectedClone]) {
							isCloneMoving[selectedClone] = false;
							playerClone[selectedClone] = null;
						} else {
							playerRecord[selectedClone] = player.createRecord();
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
		for (int i = 0; i < isCloneMoving.length; i++) {
			if (isCloneMoving[i]) {
				return true;
			}
		}
		return false;
	}

}
