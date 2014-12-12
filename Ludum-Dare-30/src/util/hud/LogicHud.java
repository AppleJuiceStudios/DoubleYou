package util.hud;

import game.level.mapobject.MapObjectLogicAnd;
import game.res.ResourceManager;

public class LogicHud extends Hud {

	public LogicHud() {
		super(50);
		items = new HudItem[1];

		items[0] = new HudItem(ResourceManager.getImage("/level/object/Lasergate.png"), SIZE, MapObjectLogicAnd.class);
	}
}