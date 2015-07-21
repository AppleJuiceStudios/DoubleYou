package game.staging;

import game.main.GameCanvas;
import game.main.GameFrame;
import game.res.Button;
import game.res.Preferences;
import game.res.ResourceManager;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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

public class StageOptions extends Stage {

	// Buttons
	private Button[] btns;
	private int selectedButton;

	// Languages
	private Button[] langs;
	private int selectedLang;

	// Images
	private BufferedImage background;

	public StageOptions(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		initMouse();
		initKey();
		initButtons();
		loadTextures();
		initLang();
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

				for (int i = 0; i < langs.length; i++) {
					if (langs[i].contains(point)) {
						langs[i].highlight();
						Preferences.setLang(langs[i].getText());
						Preferences.save();
						ResourceManager.reloadLang();
						initButtons();
						selectedLang = i;
					} else {
						langs[i].deHighlight();
					}
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
				if (e.getKeyCode() == Preferences.getKeyBinding("key_back")) {
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
		background = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	private void initLang() {
		String lang = Preferences.getLang();

		if (lang.contains("de_DE"))
			selectedLang = 1;
		else if (lang.contains("es_ES"))
			selectedLang = 2;
		else if (lang.contains("pl_PL"))
			selectedLang = 3;
		else
			selectedLang = 0;

		langs = new Button[4];

		int buttonY = (int) (GameCanvas.WIDTH * 0.15);
		int buttonWidth = (int) (GameCanvas.WIDTH * 0.1);
		Rectangle original = new Rectangle(buttonWidth, buttonWidth);

		Button btn = new Button("en_US", new Rectangle(original), buttonWidth, buttonY);
		btn.setImage(ResourceManager.getImage("/buttons/gb.png"));
		btn.setImageHighlight(ResourceManager.getImage("/buttons/selectedLang.png"));
		btn.setHighlightReplaces(false);
		btn.setTextIsHidden(true);
		langs[0] = btn;

		Button btn2 = new Button("de_DE", new Rectangle(original), buttonWidth * 3, buttonY);
		btn2.setImage(ResourceManager.getImage("/buttons/ger.png"));
		btn2.setImageHighlight(ResourceManager.getImage("/buttons/selectedLang.png"));
		btn2.setHighlightReplaces(false);
		btn2.setTextIsHidden(true);
		langs[1] = btn2;

		Button btn3 = new Button("es_ES", new Rectangle(original), buttonWidth * 5, buttonY);
		btn3.setImage(ResourceManager.getImage("/buttons/es.png"));
		btn3.setImageHighlight(ResourceManager.getImage("/buttons/selectedLang.png"));
		btn3.setHighlightReplaces(false);
		btn3.setTextIsHidden(true);
		langs[2] = btn3;

		Button btn4 = new Button("pl_PL", new Rectangle(original), buttonWidth * 7, buttonY);
		btn4.setImage(ResourceManager.getImage("/buttons/pl.png"));
		btn4.setImageHighlight(ResourceManager.getImage("/buttons/selectedLang.png"));
		btn4.setHighlightReplaces(false);
		btn4.setTextIsHidden(true);
		langs[3] = btn4;

	}

	public void draw(Graphics2D g2) {
		drawBackgroundTopAligned(g2, background);

		for (Button button : btns)
			button.draw(g2);

		for (int i = 0; i < langs.length; i++) {
			langs[i].draw(g2);
			if (i == selectedLang)
				langs[i].highlight();
			else
				langs[i].deHighlight();
		}
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