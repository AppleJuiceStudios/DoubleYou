package game.staging;

import game.main.GameApplet;
import game.main.GameCanvas;
import game.res.ResourceManager;

import java.applet.AppletContext;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class StageCredits extends Stage {
	/**
	 * Buttons
	 */
	private Rectangle btnBack;
	private Rectangle btnWebsite;

	/**
	 * Images
	 */
	private BufferedImage imgBackground;
	private BufferedImage imgContributors;
	private BufferedImage imgBack;
	private BufferedImage imgWebsite;

	public StageCredits(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
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
		btnBack = new Rectangle(190, 500, 200, 40);
		btnWebsite = new Rectangle(410, 500, 200, 40);
	}

	private void loadTextures() {
		imgBackground = ResourceManager.getImage("/backgrounds/Space-Background.png");
		imgContributors = ResourceManager.getImage("/Contributors.png");
		imgBack = ResourceManager.getImage("/buttons/Back-Button.png");
		imgWebsite = ResourceManager.getImage("/buttons/Web-Button.png");
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(imgBackground, 0, 0, imgBackground.getWidth(), imgBackground.getHeight(), null);
		g2.drawImage(imgContributors, 20, 20, GameCanvas.WIDTH - 40, GameCanvas.HEIGHT - 200, null);
		g2.drawImage(imgBack, btnBack.x, btnBack.y, btnBack.width, btnBack.height, null);
		g2.drawImage(imgWebsite, btnWebsite.x, btnWebsite.y, btnWebsite.width, btnWebsite.height, null);
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