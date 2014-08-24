package game.level.mapobject;

import game.level.entity.EntityPlayer;
import game.level.map.LevelMap;

public class MapObjectTrigger extends MapObject {

	public MapObjectTrigger(byte id, int x, int y, int width, int height) {
		super(id, x, y, width, height, false);
	}

	public void updateTriger(EntityPlayer[] player, LevelMap map) {
		if (!power) {
			int pX = (int) ((player[0].getXPos() + (player[0].getWidth() / 2)) / 16);
			int pY = (int) ((player[0].getYPos() + player[0].getHeight() - 1) / 16);
			if (pX >= x & pX < x + width & pY >= y & pY < y + height) {
				power = true;
				action(player[0], map);
			}
		}
	}

	protected void action(EntityPlayer player, LevelMap map) {

	}

}
