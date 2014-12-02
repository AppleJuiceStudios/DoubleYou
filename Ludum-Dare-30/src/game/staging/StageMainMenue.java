package game.staging;

import game.main.GameCanvas;
import game.main.GameFrame;
import game.res.Button;
import game.res.ResourceManager;
import game.res.SoundManager;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
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
	private Button btnPlay;
	private Button btnOptions;
	private Button btnCredits;
	private Button btnExit;

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

		initMouse();
		initButtons();
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

	private void initButtons() {
		btnPlay = new Button("PLAY", 30, 350);
		btnOptions = new Button("OPTIONS", 30, 400);
		btnCredits = new Button("CREDITS", 30, 450);
		btnExit = new Button("EXIT", 30, 500);
	}

	private void loadTextures() {
		imgBackground = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(imgBackground, 0, 0, imgBackground.getWidth(), imgBackground.getHeight(), null);

		btnPlay.draw(g2);
		btnOptions.draw(g2);
		btnCredits.draw(g2);
		btnExit.draw(g2);

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

	// region Actions
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
	// endregion Actions
}