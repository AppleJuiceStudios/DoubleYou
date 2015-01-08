package game.staging;

import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

public class StageLoading extends Stage {

	private Thread loadingThread;

	public StageLoading(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		loadingThread = new Thread(new Runnable() {
			public void run() {

				ResourceManager.load();
			}
		});
		loadingThread.start();
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.MAGENTA);
		g2.fillRect(0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT);

		if (!loadingThread.isAlive())
			getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}

}
