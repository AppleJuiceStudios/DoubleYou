package game.staging;

import game.level.LevelMap;
import game.level.Textbox;
import game.level.TileSet;
import game.level.entity.Entity;
import game.level.entity.EntityPlayer;
import game.level.entity.EntityPlayerClone;
import game.level.entity.EntityPlayerCloneJump;
import game.level.entity.EntityPlayerRecord;
import game.level.entity.EntityPlayerRecordJump;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import util.Log;
import de.Auch.Monitoring;

public class StageLevel extends Stage {

	public static int SCALE = 3;
	public static final double SLOW_TIME_FACTOR = 0.1;
	private double xOffset = 0;
	private double yOffset = 0;
	private double maxXOffset;
	private double maxYOffset;

	private boolean lose;
	private long loseTime;

	private BufferedImage background;
	private BufferedImage mountains;
	private BufferedImage healthbar;

	private BufferedImage[] chooseClone;

	private LevelMap map;
	private String level;
	private TileSet tileSet;

	private Timer updateTimer;

	private boolean[] isCloneAllowed;

	private boolean isRecording;
	private boolean[] isCloneMoving;
	private EntityPlayer player;
	private EntityPlayerRecord playerRecord;
	private EntityPlayerClone[] playerClone;
	private int selectedClone;

	protected List<Entity> entities;

	public Textbox textbox;
	private BufferedImage imgTextbox;
	private boolean hasTextbox;

	public StageLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		int minXScale = GameCanvas.WIDTH / (20 * TileSet.SPRITE_SIZE) + 1;
		int minYScale = GameCanvas.HEIGHT / (15 * TileSet.SPRITE_SIZE) + 1;
		SCALE = Math.min(minXScale, minYScale);
		try {
			level = data.get("level");
			if (level.matches("\\d*"))
				map = LevelMap.loadLevel(Integer.parseInt(level));
			else
				map = LevelMap.loadLevel(new File(level));

			Log.info("Loaded Level " + level);
		} catch (IllegalArgumentException e) {
			Log.error("Error loading Level: " + level);
			throw new IllegalArgumentException(e);
		}
		map.setStageLevel(this);

		SoundManager.loadClipInCache("soundTrack", map.getSoundTrack());
		SoundManager.play("soundTrack", true);

		selectedClone = 0;
		isCloneAllowed = new boolean[4];
		isCloneMoving = new boolean[4];
		playerClone = new EntityPlayerClone[4];

		isCloneAllowed[0] = map.getIsCloneAllowed();
		isCloneAllowed[1] = map.getIsCloneAllowed();
		isCloneAllowed[2] = false;
		isCloneAllowed[3] = true;
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

		map.start();
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
		drawBackground(g2, background);
		// g2.drawImage(background, 0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT, null);
		// Montains
		double mountainsOffset = -xOffset * 0.3;
		for (int i = (int) -mountainsOffset / (260 * SCALE); i <= ((int) -mountainsOffset + GameCanvas.WIDTH) / (260 * SCALE); i++) {
			g2.drawImage(mountains, i * 260 * SCALE + (int) mountainsOffset, GameCanvas.HEIGHT - 260 * SCALE, 260 * SCALE, 260 * SCALE, null);
		}
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
				player.draw(g2, SLOW_TIME_FACTOR);
				playerRecord.draw(g2, 1.0);
			} else {
				player.draw(g2, 1.0);
			}
			for (int i = 0; i < playerClone.length; i++) {
				if (isCloneMoving[i]) {
					playerClone[i].draw(g2, 1.0);
				}
			}
		} catch (NullPointerException e) {
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2, 1.0);
		}

		map.drawObjects(g2, spriteSize);
		g2.setTransform(new AffineTransform());

		// GUI
		if (player.health > 0) {
			BufferedImage health = healthbar.getSubimage(0, (player.health - 1) * 20, healthbar.getWidth(), 20);
			g2.drawImage(health, 10, 10, health.getWidth() * 2, health.getHeight() * 2, null);
		}
		g2.drawImage(chooseClone[selectedClone], GameCanvas.WIDTH - chooseClone[selectedClone].getWidth() * 2 - 10, 10,
				chooseClone[selectedClone].getWidth() * 2, chooseClone[selectedClone].getHeight() * 2, null);
		drawTextbox(g2);

		if (lose) {
			float density = (System.currentTimeMillis() - loseTime) / (float) 6000;
			density = (float) Math.sqrt(density) + 0.05f;
			g2.setColor(new Color(0f, 0f, 0f, density > 0.99 ? 0.99f : density));
			g2.fillRect(0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT);
			density = (System.currentTimeMillis() - loseTime) / (float) 3000 * 2;
			g2.setColor(new Color(1f, 0f, 0f, density > 1.0 ? 1.0f : density));
			g2.setFont(new Font("Segoe Print", Font.BOLD, 72));
			g2.drawString("You lose...", GameCanvas.WIDTH / 2 - 200, 200);
		}
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
		double timeFactor = isRecording ? SLOW_TIME_FACTOR : 1;
		try {
			if (isRecording) {
				playerRecord.update(map, 1.0);
			}
			player.update(map, timeFactor);
			for (int i = 0; i < isCloneMoving.length; i++) {
				if (isCloneMoving[i]) {
					playerClone[i].update(map, timeFactor);
					if (playerClone[i].isDead()) {
						isCloneMoving[i] = false;
						playerClone[i] = null;
					}
				}
			}
			map.updateTriger(player, isCloneMoving[0] ? playerClone[0] : null, isCloneMoving[1] ? playerClone[1] : null, isCloneMoving[2] ? playerClone[2]
					: null, isCloneMoving[3] ? playerClone[3] : null);
		} catch (NullPointerException e) {
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(map, timeFactor);
		}

		// region Entity Interaction

		// Entity Player
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.getXPos() < player.getXPos() + player.getWidth() //
					&& player.getXPos() < entity.getXPos() + entity.getWidth() //
					&& entity.getYPos() < player.getYPos() + player.getHeight() //
					&& player.getYPos() < entity.getYPos() + entity.getHeight()) {
				player.interaction(entity, map);
				entity.interaction(player, map);
			}
		}

		// Entity Clone
		for (int i = 0; i < entities.size(); i++) {
			for (int j = 0; j < playerClone.length; j++) {
				EntityPlayerClone clone = playerClone[j];
				if (clone != null) {
					Entity entity = entities.get(i);
					if (entity.getXPos() < clone.getXPos() + clone.getWidth() //
							&& clone.getXPos() < entity.getXPos() + entity.getWidth() //
							&& entity.getYPos() < clone.getYPos() + clone.getHeight() //
							&& clone.getYPos() < entity.getYPos() + entity.getHeight()) {
						clone.interaction(entity, map);
						entity.interaction(clone, map);
					}
				}
			}
		}

		// Entity Entity
		for (int i = 0; i < entities.size(); i++) {
			for (int j = i + 1; j < entities.size(); j++) {
				Entity entity1 = entities.get(i);
				Entity entity2 = entities.get(j);
				if (entity1.getXPos() < entity2.getXPos() + entity2.getWidth() //
						&& entity2.getXPos() < entity1.getXPos() + entity1.getWidth() //
						&& entity1.getYPos() < entity2.getYPos() + entity2.getHeight() //
						&& entity2.getYPos() < entity1.getYPos() + entity1.getHeight()) {
					entity1.interaction(entity2, map);
					entity2.interaction(entity1, map);
				}
			}
		}
		// endregion Entity Interaction

		if (lose) {
			if (System.currentTimeMillis() - loseTime > 3000) {
				reloadStage();
			}
		}

		Monitoring.stop(2);
	}

	public void stop() {
		updateTimer.cancel();
	}

	private void reloadStage() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("level", level);
		getStageManager().setStage(StageManager.STAGE_LEVEL, data);
	}

	public void lose() {
		if (!lose) {
			loseTime = System.currentTimeMillis();
			lose = true;
		}
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
				if (lose && System.currentTimeMillis() - loseTime > 500) {
					reloadStage();
				}
				if (isRecording) {
					playerRecord.keyPressed(e);
				} else {
					player.keyPressed(e);
				}
				int clonenumber = -1;
				if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_H) {
					clonenumber = 0;
				} else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_J) {
					clonenumber = 1;
				} else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3 || e.getKeyCode() == KeyEvent.VK_K) {
					clonenumber = 2;
				} else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_L) {
					clonenumber = 3;
				}
				if (clonenumber != -1) {
					int lastclonenumber = -1;
					if (isRecording) {
						lastclonenumber = selectedClone;
						if (selectedClone == 0) {
							playerClone[0] = new EntityPlayerClone(player.getXPos(), player.getYPos(), playerRecord.getRecording());
						} else if (selectedClone == 1) {
							playerClone[1] = new EntityPlayerCloneJump(player.getXPos(), player.getYPos(), playerRecord.getRecording());
						} else if (selectedClone == 2) {

						} else if (selectedClone == 3) {

						}
						isRecording = false;
						isCloneMoving[selectedClone] = true;
						playerRecord = null;
					}
					if (clonenumber != lastclonenumber) {
						if (isCloneMoving[clonenumber]) {
							isCloneMoving[clonenumber] = false;
							playerClone[clonenumber] = null;
						} else if (isCloneAllowed[clonenumber]) {
							if (clonenumber == 0) {
								playerRecord = player.createRecord(new EntityPlayerRecord(player.getXPos(), player.getYPos()));
							} else if (clonenumber == 1) {
								playerRecord = player.createRecord(new EntityPlayerRecordJump(player.getXPos(), player.getYPos()));
							} else if (clonenumber == 2) {

							} else if (clonenumber == 3) {

							}
							isRecording = true;
							selectedClone = clonenumber;
							player.resetKeys();
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

	public void spawnEntity(Entity entity) {
		entities.add(entity);
	}

}
