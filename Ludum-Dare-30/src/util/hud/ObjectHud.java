package util.hud;

import game.res.ResourceManager;

public class ObjectHud extends Hud {

	public ObjectHud() {
		super();
		items = new HudItem[5];

		items[0] = new HudItem(ResourceManager.getImage("/level/object/Groundswitch.png"), null);
		items[1] = new HudItem(ResourceManager.getImage("/level/object/Lasergate.png"), null);
		items[2] = new HudItem(ResourceManager.getImage("/level/object/Lasergate-Horizontal.png"), null);
		items[3] = new HudItem(ResourceManager.getImage("/level/object/Lasergate-HorizontalOnlyClone.png"), null);
		items[4] = new HudItem(ResourceManager.getImage("/level/object/Lasergate-OnlyClone.png"), null);
	}
}