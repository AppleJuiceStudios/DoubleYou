package util.hud;

import game.res.ResourceManager;

public class LogicHud extends Hud {

	public LogicHud() {
		super();
		items = new HudItem[1];

		items[0] = new HudItem(ResourceManager.getImage("/level/object/Lasergate.png"), null);
	}
}