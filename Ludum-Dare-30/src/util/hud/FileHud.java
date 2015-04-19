package util.hud;

import game.res.ResourceManager;

public class FileHud extends Hud {

	public FileHud() {
		super(40, ORIENTATION_TOP_LEFT);

		items = new HudItem[3];

		items[0] = new HudItem(ResourceManager.getImage("/buttons/load.png"), SIZE);
		items[1] = new HudItem(ResourceManager.getImage("/buttons/save.png"), SIZE);
		items[2] = new HudItem(ResourceManager.getImage("/buttons/close.png"), SIZE);
	}

}
