package util.hud;

import game.level.mapobject.MapObjectLogicAnd;
import game.level.mapobject.MapObjectLogicAndKeeping;
import game.level.mapobject.MapObjectLogicOr;
import game.res.ResourceManager;

public class LogicHud extends Hud {

	public LogicHud() {
		super(50);
		items = new HudItem[3];

		items[0] = new HudItem(ResourceManager.getImage("/buttons/LogicAnd.png"), SIZE, MapObjectLogicAnd.class);
		items[1] = new HudItem(ResourceManager.getImage("/buttons/LogicAndKeeping.png"), SIZE, MapObjectLogicAndKeeping.class);
		items[2] = new HudItem(ResourceManager.getImage("/buttons/LogicOr.png"), SIZE, MapObjectLogicOr.class);
	}
}