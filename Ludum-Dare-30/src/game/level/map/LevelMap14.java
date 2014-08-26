package game.level.map;

import game.level.Textbox;
import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectGroundswtich;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectLasergateClone;
import game.level.mapobject.MapObjectLasergateHorizontalClone;
import game.level.mapobject.MapObjectLogicOr;
import game.level.mapobject.MapObjectTriggerWinning;

public class LevelMap14 extends LevelMap {

	public void init() {
		objects = new MapObject[20];
		objects[0] = new MapObjectLasergateClone((byte) 32, 13, 14, 3, true);
		objects[1] = new MapObjectGroundswtich((byte) 33, 16, 16, (byte) 32, true, true);
		objects[2] = new MapObjectLasergateHorizontalClone((byte) 34, 22, 16, 5, true);
		objects[3] = new MapObjectGroundswtich((byte) 35, 24, 18, (byte) 34, true, false);
		objects[4] = new MapObjectLasergateClone((byte) 36, 31, 12, 3, true);
		objects[5] = new MapObjectLasergate((byte) 37, 38, 15, 3, true);
		objects[6] = new MapObjectGroundswtich((byte) 38, 29, 14, (byte) 37, true, false);
		objects[7] = new MapObjectLasergateClone((byte) 39, 44, 17, 2, true);
		objects[8] = new MapObjectLasergate((byte) 40, 46, 17, 2, true);
		objects[9] = new MapObjectGroundswtich((byte) 41, 46, 14, (byte) 40, true, false);
		objects[10] = new MapObjectLasergate((byte) 42, 49, 12, 3, true);
		objects[11] = new MapObjectGroundswtich((byte) 43, 49, 18, (byte) 42, true, false);
		objects[12] = new MapObjectLasergate((byte) 44, 58, 13, 2, true);
		objects[13] = new MapObjectGroundswtich((byte) 45, 58, 18, (byte) 51, true, false);
		objects[14] = new MapObjectLasergate((byte) 46, 65, 17, 2, true);
		objects[15] = new MapObjectGroundswtich((byte) 47, 64, 11, (byte) 46, true, false);
		objects[16] = new MapObjectLasergateHorizontalClone((byte) 48, 60, 12, 3, true);
		objects[17] = new MapObjectTriggerWinning((byte) 49, 68, 15, 1, 4);
		objects[18] = new MapObjectGroundswtich((byte) 50, 64, 11, (byte) 51, true, false);
		objects[19] = new MapObjectLogicOr((byte) 51, (byte) 45, (byte) 50, (byte) 44, true, true);
	}

	public void start() {
		getStageLevel().textbox = new Textbox(
				"These blue doors are different!\nYou're clone can walk through them\neven when you are not programming him...");
	}

}
