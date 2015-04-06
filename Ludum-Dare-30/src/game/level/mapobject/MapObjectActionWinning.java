package game.level.mapobject;

import game.level.LevelMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectActionWinning extends MapObjectAction {

	public MapObjectActionWinning(int id, int x, int y, boolean repeat) {
		super(id, x, y, repeat);
	}

	public MapObjectActionWinning() {

	}

	public void action(LevelMap map) {
		map.hasWon();
	}

}
