package game.level.mapobject;

import game.level.Textbox;
import game.level.entity.EntityPlayer;
import game.level.map.LevelMap;

public class MapObjectTriggerLevel12 extends MapObject {

	private boolean detect;
	private int remainingTime;

	public MapObjectTriggerLevel12(byte id, int x, int y, int width, int height) {
		super(id, x, y, width, height, false);
	}

	public void updateTriger(EntityPlayer[] player, LevelMap map) {
		remainingTime--;
		int pX = (int) ((player[0].getXPos() + (player[0].getWidth() / 2)) / 16);
		int pY = (int) ((player[0].getYPos() + player[0].getHeight() - 1) / 16);
		if (pX >= x & pX < x + width & pY >= y & pY < y + height & !map.getStageLevel().isCloneMoving()) {
			if (detect) {
				if (remainingTime <= 0) {
					action(player[0], map);
				}
			} else {
				detect = true;
				remainingTime = 120;
			}
		} else {
			detect = false;
		}
	}

	protected void action(EntityPlayer player, LevelMap map) {
		map.getStageLevel().textbox = new Textbox("That's not the way to do it!\nMaybe you could time you're clone\nto open both doors for you...");
		player.setX(43 * 16);
		player.setY(13 * 16);
	}

}
