package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.Entity;
import game.level.entity.EntityPlayer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectTrigger extends MapObject {

	public MapObjectTrigger(int id, int x, int y, int width, int height) {
		super(id, x, y, width, height, false);
	}

	public MapObjectTrigger() {

	}

	public void updateTriger(LevelMap map, Entity... entities) {
		int pX = (int) ((entities[0].getXPos() + (entities[0].getWidth() / 2)) / 16);
		int pY = (int) ((entities[0].getYPos() + entities[0].getHeight() - 1) / 16);
		if (pX >= x & pX < x + width & pY >= y & pY < y + height) {
			power = true;
			action((EntityPlayer) entities[0], map);
		}

	}

	protected void action(EntityPlayer player, LevelMap map) {

	}

}
