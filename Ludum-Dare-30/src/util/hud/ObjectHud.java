package util.hud;

import game.level.mapobject.MapObjectDecorationFinishFlag;
import game.level.mapobject.MapObjectGroundswtich;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectLasergateClone;
import game.level.mapobject.MapObjectLasergateHorizontal;
import game.level.mapobject.MapObjectLasergateHorizontalClone;
import game.level.mapobject.MapObjectSpike;
import game.res.ResourceManager;

public class ObjectHud extends Hud {

	public ObjectHud() {
		super(40, ORIENTATION_BOTTOM_CENTER);
		items = new HudItem[7];

		items[0] = new HudItem(ResourceManager.getImage("/level/object/Groundswitch.png"), SIZE, MapObjectGroundswtich.class);
		items[1] = new HudItem(ResourceManager.getImage("/level/object/Lasergate.png"), SIZE, MapObjectLasergate.class);
		items[2] = new HudItem(ResourceManager.getImage("/level/object/Lasergate-OnlyClone.png"), SIZE, MapObjectLasergateClone.class);
		items[3] = new HudItem(ResourceManager.getImage("/level/object/Lasergate-Horizontal.png"), SIZE, MapObjectLasergateHorizontal.class);
		items[4] = new HudItem(ResourceManager.getImage("/level/object/Lasergate-HorizontalOnlyClone.png"), SIZE, MapObjectLasergateHorizontalClone.class);
		items[5] = new HudItem(ResourceManager.getImage("/model/finishFlag.png"), SIZE, MapObjectDecorationFinishFlag.class);
		items[6] = new HudItem(ResourceManager.getImage("/level/object/spike.png"), SIZE, MapObjectSpike.class);
	}
}