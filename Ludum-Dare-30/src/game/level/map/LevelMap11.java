package game.level.map;

import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectGroundswich;

public class LevelMap11 extends LevelMap {

	public void init() {
		objects = new MapObject[5];
		objects[0] = new MapObjectLasergate((byte) 32, 10, 12, 3, true);
		objects[1] = new MapObjectGroundswich((byte) 33, 6, 14, (byte) 32, true);
		objects[2] = new MapObjectLasergate((byte) 34, 27, 8, 5, true);
		objects[3] = new MapObjectGroundswich((byte) 35, 23, 12, (byte) 34, false);
		objects[4] = new MapObjectGroundswich((byte) 36, 30, 12, (byte) 34, true);
	}

}
