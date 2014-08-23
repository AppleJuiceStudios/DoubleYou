package game.staging;

import java.awt.Color;
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
	private BufferedImage background;

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
			background = ImageIO.read(getClass().getResourceAsStream("/Space-Background.png"));
		} catch (Exception e) {
			System.out.println("[Main Menue] Texture loading failed!");
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fillRect(0, 0, 800, 600); //Background
		g2.setColor(Color.GREEN);
		g2.fill(btnPlay);
		g2.fill(btnOptions);
		g2.fill(btnCredits);
		g2.fill(btnExit);
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
		System.out.println("Options");
	}

	private void credits() {
		System.out.println("Credits");
	}

	private void exit() {
		System.exit(0);
	}
}