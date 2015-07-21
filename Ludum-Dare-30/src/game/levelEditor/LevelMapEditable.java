package game.levelEditor;

import game.level.LevelMap;
import game.level.mapobject.MapObject;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "levelMap")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LevelMapEditable extends LevelMap {

	private Map<Integer, MapObject> objectsMap;

	public void init() {
		// safe parallel access
		objectsMap = new ConcurrentHashMap<Integer, MapObject>(100);
	}

	public void drawObjects(Graphics2D g2, int size) {
		for (Map.Entry<Integer, MapObject> entry : objectsMap.entrySet()) {
			entry.getValue().draw(g2, size);
		}
	}

	public void drawLogicObjects(Graphics2D g2, int size) {
		for (Map.Entry<Integer, MapObject> entry : objectsMap.entrySet()) {
			entry.getValue().drawLogic(g2, size);
		}
	}

	public MapObject getMapObject(int id) {
		return objectsMap.get(id);
	}

	public MapObject[] getMapObjects() {
		return objectsMap.values().toArray(new MapObject[0]);

	}

	public void setMapObjects(MapObject[] mapObjects) {
		objectsMap = new ConcurrentHashMap<Integer, MapObject>(mapObjects.length + 20);
		for (int i = 0; i < mapObjects.length; i++) {
			objectsMap.put(i, mapObjects[i]);
		}
	}

}
