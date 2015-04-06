package util.hud;

import game.level.mapobject.MapObjectActionSpawner;
import game.level.mapobject.MapObjectActionTextbox;
import game.level.mapobject.MapObjectActionWinning;
import game.level.mapobject.MapObjectLogicAnd;
import game.level.mapobject.MapObjectLogicAndKeeping;
import game.level.mapobject.MapObjectLogicOr;
import game.res.ResourceManager;

public class LogicHud extends Hud {

	public LogicHud() {
		super(40, ORIENTATION_BOTTOM_CENTER);
		items = new HudItem[6];

		items[0] = new HudItem(ResourceManager.getImage("/buttons/LogicAnd.png"), SIZE, MapObjectLogicAnd.class);
		items[1] = new HudItem(ResourceManager.getImage("/buttons/LogicAndKeeping.png"), SIZE, MapObjectLogicAndKeeping.class);
		items[2] = new HudItem(ResourceManager.getImage("/buttons/LogicOr.png"), SIZE, MapObjectLogicOr.class);
		items[3] = new HudItem(ResourceManager.getImage("/buttons/ActionSpawner.png"), SIZE, MapObjectActionSpawner.class);
		items[4] = new HudItem(ResourceManager.getImage("/buttons/ActionTextbox.png"), SIZE, MapObjectActionTextbox.class);
		items[5] = new HudItem(ResourceManager.getImage("/buttons/ActionWinning.png"), SIZE, MapObjectActionWinning.class);
	}
}