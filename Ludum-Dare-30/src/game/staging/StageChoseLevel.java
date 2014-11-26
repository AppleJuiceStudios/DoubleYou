package game.staging;

import game.main.GameCanvas;
import game.res.ResourceManager;
import game.res.SaveGame;
import game.res.SoundManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class StageChoseLevel extends Stage {

	private int nextLevel;

	// Buttons
	private Rectangle[] btnsLevel;

	// Images
	private BufferedImage imgLock;
	private BufferedImage[] imgsLevel;

	// Background
	private BufferedImage imgBGS1;
	private BufferedImage imgBGS2;
	private BufferedImage imgBGS3;
	private BufferedImage imgBGS4;

	public StageChoseLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);

		if (!SoundManager.isLoaded("Space Commando")) {
			SoundManager.loadClipInCache("Space Commando", "space_commando.wav");
			SoundManager.play("Space Commando", true);
		}

		nextLevel = !GameCanvas.IS_APPLET ? SaveGame.saveGame.getNextLevel() : 16;

		btnsLevel = new Rectangle[16];
		imgsLevel = new BufferedImage[16];

		initMouse();
		initKey();
		initRecs();
		loadTextures();
	}

	private void initMouse() {
		getStageManager().setMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point point = new Point(x, y);

				for (int i = 1; i <= btnsLevel.length; i++)
					if (btnsLevel[i - 1].contains(point) && nextLevel >= i)
						send(Integer.toString(i));
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	private void initKey() {
		getStageManager().setKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
				}
			}
		});
	}

	private void initRecs() {
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++) {
				int current = (x % 2) + (x / 2) * 4 + (y % 2) * 2 + (y / 2) * 8;
				int xx = 100 + (x / 2) * 400 + (x % 2) * 150;
				int yy = 66 + (y / 2) * 298 + (y % 2) * 116;
				btnsLevel[current] = new Rectangle(xx, yy, 50, 50);
			}
	}

	private void loadTextures() {
		imgLock = ResourceManager.getImage("/buttons/Lock.png");

		for (int i = 1; i <= imgsLevel.length; i++)
			imgsLevel[i - 1] = ResourceManager.getImage("/buttons/Level-" + i + ".png");

		// Backgrounds
		imgBGS1 = ResourceManager.getImage("/backgrounds/Mars-Background.png");
		imgBGS2 = ResourceManager.getImage("/backgrounds/Saturn-Background.png");
		imgBGS3 = ResourceManager.getImage("/backgrounds/Menu-Background.png");
		imgBGS4 = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	@Override
	public void draw(Graphics2D g2) {
		// Backgrounds
		g2.drawImage(imgBGS1, 0, 0, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS2, GameCanvas.WIDTH / 2, 0, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS3, 0, GameCanvas.HEIGHT / 2, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS4, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		// Border
		g2.setColor(Color.BLACK);
		g2.fillRect(397, 0, 6, 600);
		g2.fillRect(0, 297, 800, 6);

		// Level & Overlay
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++) {
				int current = (x % 2) + (x / 2) * 4 + (y % 2) * 2 + (y / 2) * 8;
				int xx = 100 + (x / 2) * 400 + (x % 2) * 150;
				int yy = 66 + (y / 2) * 298 + (y % 2) * 116;
				g2.drawImage(imgsLevel[current], xx, yy, 50, 50, null);
				if (current >= nextLevel)
					g2.drawImage(imgLock, xx, yy, 50, 50, null);
			}
	}

	private void send(String level) {
		Map<String, String> send = new HashMap<String, String>();
		send.put("level", level);
		getStageManager().setStage(StageManager.STAGE_LEVEL, send);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}
}