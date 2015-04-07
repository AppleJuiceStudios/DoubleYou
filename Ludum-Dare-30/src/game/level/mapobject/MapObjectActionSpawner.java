package game.level.mapobject;

import game.level.LevelMap;
import game.level.TileSet;
import game.level.entity.EntityEnemyMars;
import game.res.ResourceManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectActionSpawner extends MapObjectAction {

	public MapObjectActionSpawner(int id, int x, int y, boolean repeat) {
		super(id, x, y, repeat);
	}

	public MapObjectActionSpawner() {

	}

	protected void loadTexture() {
		texture = ResourceManager.getImage("/buttons/ActionSpawner.png");
	}

	public void action(LevelMap map) {
		map.getStageLevel().spawnEntity(new EntityEnemyMars(x * TileSet.SPRITE_SIZE, y * TileSet.SPRITE_SIZE));
	}

}
