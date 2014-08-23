package game.staging;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;

public class StageMainMenue extends Stage {

	/**
	 * Buttons
	 */
	private Rectangle btnPlay;
	private Rectangle btnOptions;
	private Rectangle btnCredits;
	private Rectangle btnExit;

	/**
	 * Images
	 */
	private BufferedImage imgBackground;
	private BufferedImage imgPlay;
	private BufferedImage imgOptions;
	private BufferedImage imgCredits;
	private BufferedImage imgExit;

	public StageMainMenue(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
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

				if (btnPlay.contains(point)) {
					play();
				} else if (btnOptions.contains(point)) {
					options();
				} else if (btnCredits.contains(point)) {
					credits();
				} else if (btnExit.contains(point)) {
					exit();
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
		btnPlay = new Rectangle(30, 350, 200, 40);
		btnOptions = new Rectangle(30, 400, 200, 40);
		btnCredits = new Rectangle(30, 450, 200, 40);
		btnExit = new Rectangle(30, 500, 200, 40);
	}

	private void loadTextures() {
		try {
			imgBackground = ImageIO.read(getClass().getResourceAsStream("/Space-Background.png"));
			imgPlay = ImageIO.read(getClass().getResourceAsStream("/buttons/Play-Button.png"));
			imgOptions = ImageIO.read(getClass().getResourceAsStream("/buttons/Settings-Button.png"));
			imgCredits = ImageIO.read(getClass().getResourceAsStream("/buttons/Credits-Button.png"));
			imgExit = ImageIO.read(getClass().getResourceAsStream("/buttons/Exit-Button.png"));
		} catch (Exception e) {
			System.out.println("[Main Menue] Texture loading failed!");
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(imgBackground, 0, 0, imgBackground.getWidth(), imgBackground.getHeight(), null);
		g2.drawImage(imgPlay, btnPlay.x, btnPlay.y, btnPlay.width, btnPlay.height, null);
		g2.drawImage(imgOptions, btnOptions.x, btnOptions.y, btnOptions.width, btnOptions.height, null);
		g2.drawImage(imgCredits, btnCredits.x, btnCredits.y, btnCredits.width, btnCredits.height, null);
		g2.drawImage(imgExit, btnExit.x, btnExit.y, btnExit.width, btnExit.height, null);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}

	/**
	 * Actions
	 */
	private void play() {
		getStageManager().setStage(StageManager.STAGE_LEVEL);
	}

	private void options() {
		getStageManager().setStage(StageManager.STAGE_OPTIONS);
	}

	private void credits() {
		getStageManager().setStage(StageManager.STAGE_CREDITS);
	}

	private void exit() {
		System.exit(0);
	}
}