package game.level.mapobject;

import game.level.LevelMap;
import game.res.ResourceManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectActionWinning extends MapObjectAction {

	public MapObjectActionWinning(int id, int x, int y, boolean repeat) {
		super(id, x, y, repeat);
	}

	public MapObjectActionWinning() {

	}

	protected void loadTexture() {
		texture = ResourceManager.getImage("/buttons/ActionWinning.png");
	}

	public void action(LevelMap map) {
		map.hasWon();
	}

}
