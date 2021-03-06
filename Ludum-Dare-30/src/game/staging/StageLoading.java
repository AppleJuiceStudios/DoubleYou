package game.staging;

import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import util.GeneralUtils;

public class StageLoading extends Stage {

	private Thread loadingThread;
	private BufferedImage image;
	private Color color;
	private Font font;

	private List<String> lines;
	private String message;
	private int messageSpeed;
	private long lastMsgChange;
	private boolean finished = false;

	public StageLoading(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		initListeners();

		loadingThread = new Thread(new Runnable() {
			public void run() {
				ResourceManager.load();
				finished = true;
			}
		});
		loadingThread.start();

		// Load message
		lines = new ArrayList<>();
		Scanner scanner = new Scanner(ResourceManager.class.getResourceAsStream("/loading.txt"));
		while (scanner.hasNextLine())
			lines.add(scanner.nextLine());
		scanner.close();
		nextMessage();
		messageSpeed = 5000;

		// Load Image
		try {
			image = ImageIO.read(ResourceManager.class.getResourceAsStream("/backgrounds/Menu-Background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set Color and Font
		color = new Color(1f, 1f, 1f, 0.5f);
		font = GeneralUtils.scaleFont(message, new Rectangle(GameCanvas.WIDTH / 8 + 20, (int) (GameCanvas.HEIGHT * 0.75) + 20, GameCanvas.WIDTH
				- GameCanvas.WIDTH / 4 - 40, (int) (GameCanvas.HEIGHT * 0.08) - 40), new Font("Dialog", Font.BOLD, 16));
	}

	public void draw(Graphics2D g2) {
		drawBackgroundTopAligned(g2, image);

		g2.setColor(color);
		g2.fillRect(GameCanvas.WIDTH / 8, (int) (GameCanvas.HEIGHT * 0.75), GameCanvas.WIDTH - GameCanvas.WIDTH / 4, (int) (GameCanvas.HEIGHT * 0.08));

		g2.setColor(Color.WHITE);
		g2.setFont(font);
		g2.drawString(message, GameCanvas.WIDTH / 8 + 20, (int) (GameCanvas.HEIGHT * 0.83) - 20);

		if (finished) {
			g2.drawString("Press any key to continue.", GameCanvas.WIDTH / 8 + 20, (int) (GameCanvas.HEIGHT * 0.93));
		}
		if (finished && GeneralUtils.isDevMode())
			getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);

		if (System.currentTimeMillis() - lastMsgChange > messageSpeed)
			nextMessage();
	}

	private void nextMessage() {
		message = "Please Wait... " + lines.get((int) (Math.random() * lines.size()));
		font = GeneralUtils.scaleFont(message, new Rectangle(GameCanvas.WIDTH / 8 + 20, (int) (GameCanvas.HEIGHT * 0.75) + 20, GameCanvas.WIDTH
				- GameCanvas.WIDTH / 4 - 40, (int) (GameCanvas.HEIGHT * 0.08) - 40), new Font("Dialog", Font.BOLD, 16));
		lastMsgChange = System.currentTimeMillis();
	}

	public void update() {

	}

	public void stop() {

	}

	private void initListeners() {
		getStageManager().setKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (finished) {
					getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
				}
			}
		});
		getStageManager().setMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (finished) {
					getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
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
}