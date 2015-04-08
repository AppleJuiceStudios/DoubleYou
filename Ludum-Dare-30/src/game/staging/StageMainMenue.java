package game.staging;

import game.main.GameCanvas;
import game.main.GameFrame;
import game.res.Button;
import game.res.ResourceManager;
import game.res.SoundManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Scanner;

import util.GeneralUtils;
import util.Log;

public class StageMainMenue extends Stage {
	// region Variables
	// Buttons
	private Button[] btns;
	private int selectedButton;

	// Images
	private BufferedImage imgBackground;

	private static String VERSION;
	private static boolean firstStart;

	// endregion Variables

	public StageMainMenue(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		if (GeneralUtils.isDevMode()) {
			SoundManager.loadMidi("Take5", "take5.mid");
			SoundManager.startMidi("Take5");
		} else {
			SoundManager.loadClipInCache("Space Commando", "space_commando.wav");
			SoundManager.play("Space Commando", true);
		}

		initButtons();
		initMouse();
		initKeyControll();
		loadTextures();

		if (firstStart == false) {
			Scanner scanner = new Scanner(ResourceManager.class.getResourceAsStream("/VERSION"));
			if (scanner.hasNextLine())
				VERSION = scanner.nextLine();
			else
				VERSION = "No Version found!";
			scanner.close();

			Log.info("Starting game took a total of " + (System.currentTimeMillis() - GameFrame.GAMESTARTTIME) + " ms!");
			firstStart = true;
		}
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

				if (btns[0].contains(point)) {
					play();
				} else if (btns[1].contains(point)) {
					options();
				} else if (btns[2].contains(point)) {
					credits();
				} else if (btns[3].contains(point)) {
					exit();
				} else if (btns[4].contains(point)) {
					custom();
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
	}

	private void initKeyControll() {
		getStageManager().setKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (selectedButton <= 0) {
						play();
					} else if (selectedButton == 1) {
						options();
					} else if (selectedButton == 2) {
						credits();
					} else if (selectedButton == 3) {
						exit();
					} else if (selectedButton == 4) {
						custom();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					selectedButton++;
					selectedButton %= btns.length;
				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					selectedButton--;
					if (selectedButton < 0)
						selectedButton = btns.length - 1;
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
		if (GeneralUtils.isDevMode())
			btns = new Button[5];
		else
			btns = new Button[4];

		int buttonX = (int) (GameCanvas.WIDTH * 0.1);
		int startY = (int) (GameCanvas.HEIGHT * 0.5);
		int buttonGap = (int) (GameCanvas.HEIGHT * 0.01);
		int buttonHeight = (int) (GameCanvas.HEIGHT * 0.066);

		btns[0] = new Button(ResourceManager.getString("gui.play"), buttonX, startY + buttonHeight * 1 + buttonGap * 1);
		btns[1] = new Button(ResourceManager.getString("gui.options"), buttonX, startY + buttonHeight * 2 + buttonGap * 2);
		btns[2] = new Button(ResourceManager.getString("gui.credits"), buttonX, startY + buttonHeight * 3 + buttonGap * 3);
		btns[3] = new Button(ResourceManager.getString("gui.exit"), buttonX, startY + buttonHeight * 4 + buttonGap * 4);

		if (GeneralUtils.isDevMode())
			btns[4] = new Button("CUSTOM MAPS", buttonX, startY + buttonHeight * 0 + buttonGap * 0);

		for (Button button : btns)
			button.setHighlighted(false);
	}

	private void loadTextures() {
		imgBackground = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	public void draw(Graphics2D g2) {
		drawBackground(g2, imgBackground);

		for (Button button : btns)
			button.draw(g2);

		g2.setColor(Color.GRAY);
		g2.setFont(new Font("Dialog", Font.PLAIN, 12));
		if (GeneralUtils.isDevMode())
			g2.drawString("DevMODE | " + VERSION, GameCanvas.WIDTH - 120, GameCanvas.HEIGHT - 20);
		else
			g2.drawString(VERSION, GameCanvas.WIDTH - 50, GameCanvas.HEIGHT - 20);
	}

	public void stop() {

	}

	// region Actions
	private void play() {
		getStageManager().setStage(StageManager.STAGE_CHOOSE_LEVEL);
	}

	private void custom() {
		getStageManager().setStage(StageManager.STAGE_CUSTOM_MAPS);
	}

	private void options() {
		getStageManager().setStage(StageManager.STAGE_OPTIONS);
	}

	private void credits() {
		getStageManager().setStage(StageManager.STAGE_CREDITS);
	}

	private void exit() {
		Log.info("Exit from MainMenue");
		getStageManager().exitGame();
	}
	// endregion Actions
}