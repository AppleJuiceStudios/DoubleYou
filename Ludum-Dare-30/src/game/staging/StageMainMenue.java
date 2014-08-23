package game.staging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

public class StageMainMenue extends Stage {

	public StageMainMenue(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.fillRect(10, 10, 50, 50);
	}

	@Override
	public void update() {

	}

	@Override
	public void stop() {

	}

}
