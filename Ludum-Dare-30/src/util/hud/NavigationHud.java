package util.hud;

import game.res.ResourceManager;

public class NavigationHud extends Hud {

	public NavigationHud() {
		super(40, ORIENTATION_TOP_RIGHT);

		items = new HudItem[1];

		items[0] = new HudItem(ResourceManager.getImage("/buttons/switch.png"), SIZE);
	}
}