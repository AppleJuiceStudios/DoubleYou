package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;

@XmlRootElement
public class MapObjectLogic extends MapObject {

	private boolean inverted;
	private int targetID;

	public MapObjectLogic(int id, int targetID, boolean inverted, boolean power) {
		super(id, 0, 0, 0, 0, power);
		this.targetID = targetID;
		this.inverted = inverted;
	}

	public MapObjectLogic() {

	}

	public void setPower(boolean power, LevelMap map) {

		power = update(map) ^ inverted;

		map.getMapObject(targetID).setPower(power, map);
	}

	public boolean update(LevelMap map) {
		return false;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	public int getTargetID() {
		return targetID;
	}

	public void setTargetID(int targetID) {
		this.targetID = targetID;
	}

}
