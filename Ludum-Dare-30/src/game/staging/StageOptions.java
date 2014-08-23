package game.staging;

import game.main.GameApplet;
import game.main.GameCanvas;

import java.applet.AppletContext;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

public class StageOptions extends Stage {

	/**
	 * Buttons
	 */
	private Rectangle btnBack;
	private Rectangle btnWebsite;

	/**
	 * Images
	 */
	private BufferedImage background;

	public StageOptions(StageManager stageManager, Map<String, String> data) {
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
		g2.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
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
		getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
	}

	private void website() {
		URL url = null;
		try {
			url = new URL("http://philipp-auch.de/?page_id=14");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (GameCanvas.IS_APPLET) {
			AppletContext a = GameApplet.appletContext;
			a.showDocument(url, "_blank");
		} else if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(url.toURI());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}