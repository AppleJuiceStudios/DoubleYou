package game.staging;

import java.awt.Graphics2D;
import java.util.Map;

public abstract class Stage {

	private StageManager stageManager;

	public Stage(StageManager stageManager, Map<String, String> data) {
		this.stageManager = stageManager;
	}

	public StageManager getStageManager() {
		return stageManager;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void stop();

}
