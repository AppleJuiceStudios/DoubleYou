package game.level.map;

import game.level.EntityPlayer;
import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectGroundswtich;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectLasergateHorizontal;
import game.level.mapobject.MapObjectLogicAndKeeping;

public class LevelMap12 extends LevelMap {

	public void init() {
		objects = new MapObject[15];
		objects[0] = new MapObjectLasergate((byte) 32, 13, 16, 3, true);
		objects[1] = new MapObjectLasergate((byte) 33, 23, 18, 2, true);
		objects[2] = new MapObjectGroundswtich((byte) 34, 23, 15, (byte) 33, true, false);
		objects[3] = new MapObjectGroundswtich((byte) 35, 31, 18, (byte) 46, false, false);
		objects[4] = new MapObjectGroundswtich((byte) 36, 34, 18, (byte) 46, false, false);
		objects[14] = new MapObjectLogicAndKeeping((byte) 46, (byte) 35, (byte) 36, (byte) 37, true, false);
		objects[5] = new MapObjectLasergate((byte) 37, 37, 16, 3, true);
		objects[6] = new MapObjectGroundswtich((byte) 38, 48, 14, (byte) 40, true, false);
		objects[7] = new MapObjectGroundswtich((byte) 39, 50, 14, (byte) 41, true, false);
		objects[8] = new MapObjectLasergate((byte) 40, 48, 17, 2, true);
		objects[9] = new MapObjectLasergate((byte) 41, 50, 17, 2, true);
		objects[11] = new MapObjectGroundswtich((byte) 43, 69, 11, (byte) 44, true, false);
		objects[12] = new MapObjectLasergateHorizontal((byte) 44, 62, 16, 3, true);
		// Dumy
		objects[10] = new MapObjectGroundswtich((byte) 42, 49, 18, (byte) 32, true, false);
		objects[13] = new MapObjectGroundswtich((byte) 45, 49, 18, (byte) 32, true, false);
	}

	public void updateTriger(EntityPlayer... player) {
		if (getStageLevel().isCloneMoving()) {
			getMapObject((byte) 32).setPower(false);
		}
		super.updateTriger(player);
	}

}
