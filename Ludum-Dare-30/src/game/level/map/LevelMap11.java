package game.level.map;

import game.level.MapObjectDoor;
import game.level.MapObjectPlate;
import game.level.mapobject.MapObject;

public class LevelMap11 extends LevelMap {

	public void init() {
		objects = new MapObject[4];
		objects[0] = new MapObjectDoor((byte) 32, 10, 12, 3, true);
		objects[1] = new MapObjectPlate((byte) 33, 6, 14, (byte) 32);
		objects[2] = new MapObjectDoor((byte) 34, 21, 8, 5, true);
		objects[3] = new MapObjectPlate((byte) 35, 17, 12, (byte) 34);
	}

}
