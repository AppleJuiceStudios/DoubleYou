package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.Entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectTrigger extends MapObject {

	private boolean wasPowered;
	private int targetID;

	public MapObjectTrigger(int id, int x, int y, int width, int height, int targetID) {
		super(id, x, y, width, height, false);
		this.targetID = targetID;
	}

	public MapObjectTrigger() {

	}

	public void updateTriger(LevelMap map, Entity... entities) {
		power = false;
		int pX = (int) ((entities[0].getXPos() + (entities[0].getWidth() / 2)) / 16);
		int pY = (int) ((entities[0].getYPos() + entities[0].getHeight() - 1) / 16);
		if (pX >= x & pX < x + width & pY >= y & pY < y + height) {
			power = true;
		}
		if (power && !wasPowered) {
			map.powerObject(targetID, true);
			wasPowered = true;
		}
		if (!power && wasPowered) {
			map.powerObject(targetID, false);
			wasPowered = false;
		}
	}

}
