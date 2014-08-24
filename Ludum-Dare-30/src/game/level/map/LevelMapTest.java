package game.level.map;

import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectGroundswtich;
import game.level.mapobject.MapObjectLogicOr;

public class LevelMapTest extends LevelMap {

	public void init() {
		objects = new MapObject[6];
		objects[0] = new MapObjectLasergate((byte) 32, 10, 12, 3, true);
		objects[1] = new MapObjectGroundswtich((byte) 33, 6, 14, (byte) 32, true, true);
		objects[2] = new MapObjectLasergate((byte) 34, 27, 8, 5, true);
		objects[3] = new MapObjectGroundswtich((byte) 35, 23, 12, (byte) 37, false, false);
		objects[4] = new MapObjectGroundswtich((byte) 36, 30, 12, (byte) 37, false, true);
		objects[5] = new MapObjectLogicOr((byte) 37, (byte) 35, (byte) 36, (byte) 34, true, false);
	}

}
