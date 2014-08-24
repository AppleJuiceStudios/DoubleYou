package game.level.map;

import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectGroundswtich;
import game.level.mapobject.MapObjectLasergate;

public class LevelMap11 extends LevelMap {

	public void init() {
		objects = new MapObject[8];
		objects[0] = new MapObjectLasergate((byte) 32, 23, 16, 3, true);
		objects[1] = new MapObjectGroundswtich((byte) 33, 20, 18, (byte) 32, true, true);
		objects[2] = new MapObjectLasergate((byte) 34, 32, 18, 2, true);
		objects[3] = new MapObjectGroundswtich((byte) 35, 32, 15, (byte) 34, true, true);
		objects[4] = new MapObjectGroundswtich((byte) 36, 54, 14, (byte) 37, true, true);
		objects[5] = new MapObjectLasergate((byte) 37, 49, 15, 4, true);
		// Dumy
		objects[6] = new MapObjectGroundswtich((byte) 38, 64, 12, (byte) 32, true, true);
		objects[7] = new MapObjectGroundswtich((byte) 39, 68, 12, (byte) 32, true, true);
	}

}
