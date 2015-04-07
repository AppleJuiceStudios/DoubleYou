package game.staging;

import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class StageLose extends Stage {

	private long duration = 3000;
	private long startTime = 0;

	private Map<String, String> data;

	public StageLose(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		this.data = data;
		initListeners();
		startTime = System.currentTimeMillis();
	}

	public void draw(Graphics2D g2) {
		if (System.currentTimeMillis() - startTime > duration) {
			getStageManager().setStage(StageManager.STAGE_LEVEL, data);
		}
		g2.setColor(new Color(0f, 0f, 0f, 0.01f));
		g2.fillRect(0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT);
		g2.setColor(new Color(1f, 0f, 0f, 0.05f));
		g2.setFont(new Font("Segoe Print", Font.BOLD, 72));
		g2.drawString("You lose...", GameCanvas.WIDTH / 2 - 200, 200);
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
				startTime = 0;
			}
		});
	}
}
