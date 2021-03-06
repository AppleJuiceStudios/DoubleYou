package game.staging;

import game.main.GameCanvas;
import game.res.Button;
import game.res.Preferences;
import game.res.ResourceManager;
import game.res.SoundManager;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

public class StageWon extends Stage {

	// Buttons
	private Button btnLevel;
	private Button btnMenu;

	// Images
	private BufferedImage imgBackground;
	private BufferedImage imgContributors;

	public StageWon(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		SoundManager.loadClipInCache("Won", "you_win.wav");
		SoundManager.play("Won");

		initMouse();
		initKey();
		initButtons();
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					getStageManager().setStage(StageManager.STAGE_CHOOSE_LEVEL);
				}
			}
		});
	}

	private void initButtons() {
		int buttonWidth = (int) (GameCanvas.WIDTH * 0.25);
		int buttonY = (int) (GameCanvas.HEIGHT * 0.83);
		int buttonGap = (int) (GameCanvas.HEIGHT * 0.1);

		btnLevel = new Button(ResourceManager.getString("gui.chooseLevel"), GameCanvas.WIDTH / 2 - (buttonWidth + buttonGap / 2), buttonY);
		btnMenu = new Button(ResourceManager.getString("gui.menue"), GameCanvas.WIDTH / 2 + buttonGap / 2, buttonY);
	}

	private void loadTextures() {
		imgBackground = ResourceManager.getImage("/backgrounds/Menu-Background.png");
		imgContributors = ResourceManager.getImage("/Contributors.png");
	}

	public void draw(Graphics2D g2) {
		drawBackgroundTopAligned(g2, imgBackground);
		g2.drawImage(imgContributors, 20, 20, GameCanvas.WIDTH - 40, GameCanvas.HEIGHT - 200, null);

		btnLevel.draw(g2);
		btnMenu.draw(g2);
	}

	public void stop() {

	}

	private void menu() {
		getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
	}

	private void level() {
		getStageManager().setStage(StageManager.STAGE_CHOOSE_LEVEL);
	}
}
