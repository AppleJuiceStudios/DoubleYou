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

public class StageCredits extends Stage {

	/**
	 * Buttons
	 */
	private Rectangle btnBack;
	private Rectangle btnWebsite;

	/**
	 * Images
	 */
	private BufferedImage background;

	public StageCredits(StageManager stageManager, Map<String, String> data) {
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

				if (btnBack.contains(point)) {
					back();
				} else if (btnWebsite.contains(point)) {
					website();
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
		btnBack = new Rectangle(190, 500, 200, 40);
		btnWebsite = new Rectangle(410, 500, 200, 40);
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
		g2.fill(btnBack);
		g2.fill(btnWebsite);
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
	private void back() {
		System.out.println("Back");
	}

	private void website() {
		System.out.println("Website");
	}
}