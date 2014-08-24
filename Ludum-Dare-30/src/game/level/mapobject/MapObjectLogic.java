package game.level.mapobject;

import game.level.map.LevelMap;

public class MapObjectLogic extends MapObject {

	private boolean inverted;
	private byte targetID;

	public MapObjectLogic(byte id, byte targetID, boolean inverted, boolean power) {
		super(id, 0, 0, 0, 0, power);
		this.targetID = targetID;
		this.inverted = inverted;
	}

	public void setPower(boolean power, LevelMap map) {

		power = update(map) ^ inverted;

		map.getMapObject(targetID).setPower(power, map);
	}

	public boolean update(LevelMap map) {
		return false;
	}

}
