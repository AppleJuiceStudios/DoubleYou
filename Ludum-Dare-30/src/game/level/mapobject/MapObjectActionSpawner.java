package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.EntityEnemyMars;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectActionSpawner extends MapObjectAction {

	public MapObjectActionSpawner(int id, int x, int y, boolean repeat) {
		super(id, x, y, repeat);
	}

	public MapObjectActionSpawner() {

	}

	public void action(LevelMap map) {
		map.getStageLevel().spawnEntity(new EntityEnemyMars(x, y));
	}

}