package game.staging;

import game.main.GameCanvas;
import game.main.GameFrame;
import game.res.ResourceManager;
import game.res.SoundManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Scanner;

import util.log.GeneralUtils;
import util.log.Log;

public class StageMainMenue extends Stage {
	// region Variables
	// Buttons
	private Rectangle btnPlay;
	private Rectangle btnOptions;
	private Rectangle btnCredits;
	private Rectangle btnExit;

	// Images
	private BufferedImage imgBackground;
	private BufferedImage imgButton;

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

		initMouse();
		initRecs();
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
		imgBackground = ResourceManager.getImage("/backgrounds/Menu-Background.png");
		imgButton = ResourceManager.getImage("/buttons/button.png");
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(imgBackground, 0, 0, imgBackground.getWidth(), imgBackground.getHeight(), null);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Impact", Font.BOLD, 24));

		g2.drawImage(imgButton, btnPlay.x, btnPlay.y, btnPlay.width, btnPlay.height, null);
		g2.drawString("START", btnPlay.x + 20, btnPlay.y + 29);

		g2.drawImage(imgButton, btnOptions.x, btnOptions.y, btnOptions.width, btnOptions.height, null);
		g2.drawString("OPTIONS", btnOptions.x + 20, btnOptions.y + 29);

		g2.drawImage(imgButton, btnCredits.x, btnCredits.y, btnCredits.width, btnCredits.height, null);
		g2.drawString("CREDITS", btnCredits.x + 20, btnCredits.y + 29);

		g2.drawImage(imgButton, btnExit.x, btnExit.y, btnExit.width, btnExit.height, null);
		g2.drawString("EXIT", btnExit.x + 20, btnExit.y + 29);

		if (GeneralUtils.isDevMode()) {
			g2.setFont(new Font("Dialog", Font.PLAIN, 12));
			g2.drawString("DevMODE!! | " + VERSION, GameCanvas.WIDTH - 120, GameCanvas.HEIGHT - 20);
		} else
			g2.drawString(VERSION, GameCanvas.WIDTH - 50, GameCanvas.HEIGHT - 20);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}

	// Actions
	private void play() {
		getStageManager().setStage(StageManager.STAGE_CHOOSE_LEVEL);
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
}