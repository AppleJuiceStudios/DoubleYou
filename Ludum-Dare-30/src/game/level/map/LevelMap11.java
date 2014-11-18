package game.level.map;

import game.level.Textbox;

public class LevelMap11 extends LevelMap {

	public void init() {
		// objects = new MapObject[8];
		// objects[0] = new MapObjectLasergate((byte) 32, 23, 16, 3, true);
		// objects[1] = new MapObjectGroundswtich((byte) 33, 20, 18, (byte) 32, true, true);
		// objects[2] = new MapObjectLasergate((byte) 34, 32, 18, 2, true);
		// objects[3] = new MapObjectGroundswtich((byte) 35, 32, 15, (byte) 34, true, true);
		// objects[4] = new MapObjectGroundswtich((byte) 36, 54, 14, (byte) 37, true, true);
		// objects[5] = new MapObjectLasergateHorizontal((byte) 37, 49, 15, 4, true);
		// objects[6] = new MapObjectTriggerTextbox((byte) 38, 64, 12, 1, 4, new Textbox("Congratulations!\nSee you in the next level!"));
		// objects[7] = new MapObjectTriggerWinning((byte) 39, 68, 12, 1, 4);
	}

	public void start() {
		getStageLevel().textbox = new Textbox("Hey!\nGlad you made it!\n            (Press enter to continue...)",
				"You are currently on Mars so ahead go and \nexperiment with the low gravity...\nUse W,A,D to move around.",
				"If you see red laser beams those are doors!\nUsually there is a plate to open them...");
		getStageLevel().isCloneAllowed = false;
	}

}
