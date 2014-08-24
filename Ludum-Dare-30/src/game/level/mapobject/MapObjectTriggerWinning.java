package game.level.mapobject;

import game.level.entity.EntityPlayer;
import game.level.map.LevelMap;

public class MapObjectTriggerWinning extends MapObjectTrigger {

	public MapObjectTriggerWinning(byte id, int x, int y, int width, int height) {
		super(id, x, y, width, height);
	}

	protected void action(EntityPlayer player, LevelMap map) {
		map.hasWon();
	}

}
