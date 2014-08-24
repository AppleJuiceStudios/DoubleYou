package game.staging;

import game.main.GameCanvas;
import game.res.SaveGame;
import game.res.SoundManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class StageChoseLevel extends Stage {

	private int nextLevel;

	/**
	 * Buttons
	 */
	//Stage 1
	private Rectangle btnS1L1;
	private Rectangle btnS1L2;
	private Rectangle btnS1L3;
	private Rectangle btnS1L4;
	//Stage 2
	private Rectangle btnS2L1;
	private Rectangle btnS2L2;
	private Rectangle btnS2L3;
	private Rectangle btnS2L4;
	//Stage 3
	private Rectangle btnS3L1;
	private Rectangle btnS3L2;
	private Rectangle btnS3L3;
	private Rectangle btnS3L4;
	//Stage 4
	private Rectangle btnS4L1;
	private Rectangle btnS4L2;
	private Rectangle btnS4L3;
	private Rectangle btnS4L4;

	/**
	 * Images
	 */
	private BufferedImage imgLock;
	//Stage 1
	private BufferedImage imgS1L1;
	private BufferedImage imgS1L2;
	private BufferedImage imgS1L3;
	private BufferedImage imgS1L4;
	//Stage 2
	private BufferedImage imgS2L1;
	private BufferedImage imgS2L2;
	private BufferedImage imgS2L3;
	private BufferedImage imgS2L4;
	//Stage 3
	private BufferedImage imgS3L1;
	private BufferedImage imgS3L2;
	private BufferedImage imgS3L3;
	private BufferedImage imgS3L4;
	//Stage 4
	private BufferedImage imgS4L1;
	private BufferedImage imgS4L2;
	private BufferedImage imgS4L3;
	private BufferedImage imgS4L4;
	//Background
	private BufferedImage imgBGS1;
	private BufferedImage imgBGS2;
	private BufferedImage imgBGS3;
	private BufferedImage imgBGS4;

	public StageChoseLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);

		nextLevel = SaveGame.saveGame.getNextLevel();
		initMouse();
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

				if (btnS1L1.contains(point)) { //Stage 1
					send("S1L1");
				} else if (btnS1L2.contains(point) && nextLevel >= 2) {
					send("S1L2");
				} else if (btnS1L3.contains(point) && nextLevel >= 3) {
					send("S1L3");
				} else if (btnS1L4.contains(point) && nextLevel >= 4) {
					send("S1L4");
				} else if (btnS2L1.contains(point) && nextLevel >= 5) { //Stage 2
					send("S2L1");
				} else if (btnS2L2.contains(point) && nextLevel >= 6) {
					send("S2L2");
				} else if (btnS2L3.contains(point) && nextLevel >= 7) {
					send("S2L3");
				} else if (btnS2L4.contains(point) && nextLevel >= 8) {
					send("S2L4");
				} else if (btnS3L1.contains(point) && nextLevel >= 9) { //Stage 3
					send("S3L1");
				} else if (btnS3L2.contains(point) && nextLevel >= 10) {
					send("S3L2");
				} else if (btnS3L3.contains(point) && nextLevel >= 11) {
					send("S3L3");
				} else if (btnS3L4.contains(point) && nextLevel >= 12) {
					send("S3L4");
				} else if (btnS4L1.contains(point) && nextLevel >= 13) { //Stage 4
					send("S4L1");
				} else if (btnS4L2.contains(point) && nextLevel >= 14) {
					send("S4L2");
				} else if (btnS4L3.contains(point) && nextLevel >= 15) {
					send("S4L3");
				} else if (btnS4L4.contains(point) && nextLevel >= 16) {
					send("S4L4");
				}
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

	private void initRecs() {
		//Stage 1
		btnS1L1 = new Rectangle(120, 80, 50, 50);
		btnS1L2 = new Rectangle(290, 80, 50, 50);
		btnS1L3 = new Rectangle(120, 210, 50, 50);
		btnS1L4 = new Rectangle(290, 210, 50, 50);
		//Stage 2
		btnS2L1 = new Rectangle(460, 80, 50, 50);
		btnS2L2 = new Rectangle(630, 80, 50, 50);
		btnS2L3 = new Rectangle(460, 210, 50, 50);
		btnS2L4 = new Rectangle(630, 210, 50, 50);
		//Stage 3
		btnS3L1 = new Rectangle(120, 340, 50, 50);
		btnS3L2 = new Rectangle(290, 340, 50, 50);
		btnS3L3 = new Rectangle(120, 470, 50, 50);
		btnS3L4 = new Rectangle(290, 470, 50, 50);
		//Stage 4
		btnS4L1 = new Rectangle(460, 340, 50, 50);
		btnS4L2 = new Rectangle(630, 340, 50, 50);
		btnS4L3 = new Rectangle(460, 470, 50, 50);
		btnS4L4 = new Rectangle(630, 470, 50, 50);
	}

	private void loadTextures() {
		try {
			imgLock = ImageIO.read(getClass().getResourceAsStream("/buttons/Lock.png"));
			//Stage 1
			imgS1L1 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-1.png"));
			imgS1L2 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-2.png"));
			imgS1L3 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-3.png"));
			imgS1L4 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-4.png"));
			//Stage 2
			imgS2L1 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-1.png"));
			imgS2L2 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-2.png"));
			imgS2L3 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-3.png"));
			imgS2L4 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-4.png"));
			//Stage 3
			imgS3L1 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-1.png"));
			imgS3L2 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-2.png"));
			imgS3L3 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-3.png"));
			imgS3L4 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-4.png"));
			//Stage 4
			imgS4L1 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-1.png"));
			imgS4L2 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-2.png"));
			imgS4L3 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-3.png"));
			imgS4L4 = ImageIO.read(getClass().getResourceAsStream("/buttons/Mars-4.png"));
			//Backgrounds
			imgBGS1 = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Mars-Background.png"));
			imgBGS2 = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Saturn-Background.png"));
			imgBGS3 = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Menu-Background.png"));
			imgBGS4 = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Menu-Background.png"));
		} catch (Exception e) {
			System.out.println("[Level Selection] Texture loading failed!");
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		//Backgrounds
		g2.drawImage(imgBGS1, 0, 0, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS2, GameCanvas.WIDTH / 2, 0, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS3, 0, GameCanvas.HEIGHT / 2, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS4, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		//Border
		g2.setColor(Color.BLACK);
		g2.fillRect(398, 0, 4, 600);
		g2.fillRect(0, 298, 800, 4);
		//Stage 1
		g2.drawImage(imgS1L1, 120, 80, 50, 50, null);
		g2.drawImage(imgS1L2, 290, 80, 50, 50, null);
		g2.drawImage(imgS1L3, 120, 210, 50, 50, null);
		g2.drawImage(imgS1L4, 290, 210, 50, 50, null);
		//Stage 2
		g2.drawImage(imgS2L1, 460, 80, 50, 50, null);
		g2.drawImage(imgS2L2, 630, 80, 50, 50, null);
		g2.drawImage(imgS2L3, 460, 210, 50, 50, null);
		g2.drawImage(imgS2L4, 630, 210, 50, 50, null);
		//Stage 3
		g2.drawImage(imgS3L1, 120, 340, 50, 50, null);
		g2.drawImage(imgS3L2, 290, 340, 50, 50, null);
		g2.drawImage(imgS3L3, 120, 470, 50, 50, null);
		g2.drawImage(imgS3L4, 290, 470, 50, 50, null);
		//Stage 4
		g2.drawImage(imgS4L1, 460, 340, 50, 50, null);
		g2.drawImage(imgS4L2, 630, 340, 50, 50, null);
		g2.drawImage(imgS4L3, 460, 470, 50, 50, null);
		g2.drawImage(imgS4L4, 630, 470, 50, 50, null);
		//Overlay
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				int current = (x % 2) + (x / 2) * 4 + (y % 2) * 2 + (y / 2) * 8;
				int xx = x * 50 + (x + 1) * 120;
				int yy = y * 50 + (y + 1) * 80;
				if (current >= nextLevel) {
					g2.drawImage(imgLock, xx, yy, 50, 50, null);
				}
			}
		}
	}

	private void send(String level) {
		Map<String, String> send = new HashMap<String, String>();
		send.put("level", level);
		SoundManager.stopAll();
		SoundManager.clearCache();
		SoundManager.loadClipInCache("Mars 1", "mars_1.wav");
		SoundManager.play("Mars 1", true);
		getStageManager().setStage(StageManager.STAGE_LEVEL, send);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}

}
