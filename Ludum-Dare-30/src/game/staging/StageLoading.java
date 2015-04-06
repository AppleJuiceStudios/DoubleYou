package game.staging;

import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class StageLoading extends Stage {

	private Thread loadingThread;
	private BufferedImage image;
	private Color color;
	private Font font;

	private List<String> lines;
	private String message;
	private int messageSpeed;
	private long lastMsgChange;

	public StageLoading(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		loadingThread = new Thread(new Runnable() {
			public void run() {
				ResourceManager.load();
				Map<String, String> data = new HashMap<String, String>();
				data.put("file", "res/level/levelEmpty.xml");
				getStageManager().setStage(StageManager.STAGE_LEVEL_EDITOR, data);
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
		font = new Font("Dialog", Font.BOLD, 16);
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(image, 0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT, null);

		g2.setColor(color);
		g2.fillRect(100, 450, 600, 50);

		g2.setColor(Color.WHITE);
		g2.setFont(font);
		g2.drawString(message, 110, 490);

		if (System.currentTimeMillis() - lastMsgChange > messageSpeed)
			nextMessage();
	}

	private void nextMessage() {
		message = "Please Wait... " + lines.get((int) (Math.random() * lines.size()));
		lastMsgChange = System.currentTimeMillis();
	}

	public void update() {

	}

	public void stop() {

	}
}