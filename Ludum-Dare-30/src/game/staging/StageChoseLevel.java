package game.staging;

import game.main.GameCanvas;
import game.res.Button;
import game.res.Preferences;
import game.res.ResourceManager;
import game.res.SoundManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class StageChoseLevel extends Stage {

	private int nextLevel;

	// Buttons
	private Button[] btns;
	private int selectedLevel;

	// Images
	private BufferedImage imgLock;

	// Background
	private BufferedImage imgBGS1;
	private BufferedImage imgBGS2;
	private BufferedImage imgBGS3;
	private BufferedImage imgBGS4;

	public StageChoseLevel(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);

		if (!SoundManager.isLoaded("Space Commando")) {
			SoundManager.loadClipInCache("Space Commando", "space_commando.wav");
			SoundManager.play("Space Commando", true);
		}

		nextLevel = Preferences.getNextLevel();

		btns = new Button[16];
		selectedLevel = 0;

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

				for (int i = 1; i <= btns.length; i++)
					if (btns[i - 1].contains(point) && nextLevel >= i)
						send(Integer.toString(i));
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
		getStageManager().setMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point point = new Point(x, y);

				for (int i = 0; i < btns.length; i++) {
					if (btns[i].contains(point)) {
						btns[i].setHighlighted(true);
						selectedLevel = i;
					} else
						btns[i].setHighlighted(false);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {

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
					if (nextLevel >= selectedLevel && selectedLevel > 0)
						send(Integer.toString(selectedLevel));
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_S) {
					selectedLevel %= 16;
					selectedLevel++;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_W) {
					selectedLevel--;
					if (selectedLevel <= 0)
						selectedLevel = 16;
				}
				for (Button button : btns) {
					button.setHighlighted(false);
				}
				if (selectedLevel > 0)
					btns[selectedLevel - 1].setHighlighted(true);
			}
		});
	}

	private void initButtons() {
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++) {
				int current = (x % 2) + (x / 2) * 4 + (y % 2) * 2 + (y / 2) * 8;
				int xx = (int) (GameCanvas.WIDTH * 0.125) + (x / 2) * (GameCanvas.WIDTH / 2) + (x % 2) * (int) (GameCanvas.WIDTH * 0.1875);
				int yy = (int) (GameCanvas.HEIGHT * 0.1) + (y / 2) * GameCanvas.HEIGHT / 2 + (y % 2) * (int) (GameCanvas.HEIGHT * 0.2);
				Rectangle rec = new Rectangle();
				rec.width = (int) (GameCanvas.WIDTH * 0.08);
				rec.height = (int) (GameCanvas.WIDTH * 0.08);
				Button btn = new Button("", rec, xx, yy);
				btn.setImage(ResourceManager.getImage("/buttons/Level" + (current % 4 + 1) + ".png"));
				btn.setImageHighlight(ResourceManager.getImage("/buttons/selectedLevel.png"));
				btn.setHighlightReplaces(false);
				btn.setHighlighted(false);
				btns[current] = btn;
			}
	}

	private void loadTextures() {
		imgLock = ResourceManager.getImage("/buttons/Lock.png");

		// Backgrounds
		imgBGS1 = ResourceManager.getImage("/backgrounds/Mars-Background.png");
		imgBGS2 = ResourceManager.getImage("/backgrounds/Saturn-Background.png");
		imgBGS3 = ResourceManager.getImage("/backgrounds/Menu-Background.png");
		imgBGS4 = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	public void draw(Graphics2D g2) {
		// Backgrounds
		g2.drawImage(imgBGS1, 0, 0, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS2, GameCanvas.WIDTH / 2, 0, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS3, 0, GameCanvas.HEIGHT / 2, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		g2.drawImage(imgBGS4, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2, null);
		// Border
		g2.setColor(Color.WHITE);
		g2.fillRect(GameCanvas.WIDTH / 2 - 3, 0, 6, GameCanvas.HEIGHT);
		g2.fillRect(0, GameCanvas.HEIGHT / 2 - 3, GameCanvas.WIDTH, 6);

		// Level & Overlay
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++) {
				int current = (x % 2) + (x / 2) * 4 + (y % 2) * 2 + (y / 2) * 8;
				int xx = (int) (GameCanvas.WIDTH * 0.125) + (x / 2) * (GameCanvas.WIDTH / 2) + (x % 2) * (int) (GameCanvas.WIDTH * 0.1875);
				int yy = (int) (GameCanvas.HEIGHT * 0.1) + (y / 2) * GameCanvas.HEIGHT / 2 + (y % 2) * (int) (GameCanvas.HEIGHT * 0.2);
				btns[current].draw(g2);
				if (current >= nextLevel)
					g2.drawImage(imgLock, xx, yy, (int) (GameCanvas.WIDTH * 0.08), (int) (GameCanvas.WIDTH * 0.08), null);
			}
	}

	private void send(String level) {
		Map<String, String> send = new HashMap<String, String>();
		send.put("level", level);
		getStageManager().setStage(StageManager.STAGE_LEVEL, send);
	}

	public void stop() {

	}
}