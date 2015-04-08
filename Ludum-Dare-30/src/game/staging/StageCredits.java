package game.staging;

import game.main.GameCanvas;
import game.main.GameFrame;
import game.res.Button;
import game.res.ResourceManager;
import game.res.ScrollingAnimation;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class StageCredits extends Stage {

	// Buttons
	private Button[] btns;
	private int selectedButton;

	// Images
	private BufferedImage imgBackground;

	private ScrollingAnimation scroller;

	public StageCredits(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		initMouse();
		initKey();
		initButtons();
		loadTextures();
		scroller = new ScrollingAnimation("/credits.txt", 30.0, (int) (GameCanvas.WIDTH * 0.2), (int) (GameCanvas.HEIGHT * 0.26),
				(int) (GameCanvas.WIDTH * 0.6), (int) (GameCanvas.WIDTH * 0.25));
	}

	private void initMouse() {
		getStageManager().setMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point point = new Point(x, y);

				for (int i = 0; i < btns.length; i++) {
					if (btns[i].contains(point)) {
						btns[i].setHighlighted(true);
						selectedButton = i;
					} else
						btns[i].setHighlighted(false);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});
		getStageManager().setMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point point = new Point(x, y);

				if (btns[0].contains(point)) {
					back();
				} else if (btns[1].contains(point)) {
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
				if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (selectedButton <= 0) {
						back();
					} else if (selectedButton == 1) {
						website();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_S) {
					selectedButton++;
					selectedButton %= 2;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_W) {
					selectedButton--;
					if (selectedButton < 0)
						selectedButton = 1;
				}
				for (Button button : btns) {
					button.setHighlighted(false);
				}
				if (selectedButton > -1)
					btns[selectedButton].setHighlighted(true);
			}
		});
	}

	private void initButtons() {
		selectedButton = -1;
		btns = new Button[2];

		int buttonWidth = (int) (GameCanvas.WIDTH * 0.25);
		int buttonY = (int) (GameCanvas.HEIGHT * 0.83);
		int buttonGap = (int) (GameCanvas.HEIGHT * 0.1);

		btns[0] = new Button(ResourceManager.getString("gui.back"), GameCanvas.WIDTH / 2 - (buttonWidth + buttonGap / 2), buttonY);
		btns[1] = new Button(ResourceManager.getString("gui.website"), GameCanvas.WIDTH / 2 + buttonGap / 2, buttonY);

		for (Button button : btns)
			button.setHighlighted(false);
	}

	private void loadTextures() {
		imgBackground = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	public void draw(Graphics2D g2) {
		drawBackground(g2, imgBackground);
		scroller.draw(g2);

		for (Button button : btns)
			button.draw(g2);
	}

	public void stop() {

	}

	// Actions
	private void back() {
		getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
	}

	private void website() {
		URL url = null;
		try {
			url = new URL(GameFrame.GAME_URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(url.toURI());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}