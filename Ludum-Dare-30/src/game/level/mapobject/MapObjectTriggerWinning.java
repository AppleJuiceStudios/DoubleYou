package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;
import game.level.entity.EntityPlayer;

@XmlRootElement
public class MapObjectTriggerWinning extends MapObjectTrigger {

	public MapObjectTriggerWinning(byte id, int x, int y, int width, int height) {
		super(id, x, y, width, height);
	}

	public MapObjectTriggerWinning() {

	}

	protected void action(EntityPlayer player, LevelMap map) {
		map.hasWon();
	}

}
