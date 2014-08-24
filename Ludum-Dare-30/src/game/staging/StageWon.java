package game.staging;

import game.main.GameCanvas;
import game.res.SoundManager;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;

public class StageWon extends Stage {

	/**
	 * Buttons
	 */
	private Rectangle btnLevel;
	private Rectangle btnMenu;

	/**
	 * Images
	 */
	private BufferedImage imgBackground;
	private BufferedImage imgWon;
	private BufferedImage imgLevel;
	private BufferedImage imgMenu;

	public StageWon(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		SoundManager.stopAll();
		SoundManager.clearCache();
		SoundManager.loadClipInCache("Won", "you_win.wav");
		SoundManager.play("Won");

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

				if (btnMenu.contains(point)) {
					menu();
				} else if (btnLevel.contains(point)) {
					level();
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
		btnLevel = new Rectangle(190, 500, 200, 40);
		btnMenu = new Rectangle(410, 500, 200, 40);
	}

	private void loadTextures() {
		try {
			imgBackground = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Menu-Background.png"));
			imgWon = ImageIO.read(getClass().getResourceAsStream("/Contributors.png"));
			imgLevel = ImageIO.read(getClass().getResourceAsStream("/buttons/Play-Button.png"));
			imgMenu = ImageIO.read(getClass().getResourceAsStream("/buttons/Back-Button.png"));
		} catch (Exception e) {
			System.out.println("[Main Menue] Texture loading failed!");
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(imgBackground, 0, 0, imgBackground.getWidth(), imgBackground.getHeight(), null);
		g2.drawImage(imgWon, 20, 20, GameCanvas.WIDTH - 40, GameCanvas.HEIGHT - 200, null);
		g2.drawImage(imgLevel, btnLevel.x, btnLevel.y, btnLevel.width, btnLevel.height, null);
		g2.drawImage(imgMenu, btnMenu.x, btnMenu.y, btnMenu.width, btnMenu.height, null);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}

	private void menu() {
		getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
	}

	private void level() {
		getStageManager().setStage(StageManager.STAGE_CHOOSE_LEVEL);
	}
}
