package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;

@XmlRootElement
public class MapObjectLogicOr extends MapObjectLogic {

	private byte[] in;

	public MapObjectLogicOr(byte id, byte targetID, boolean inverted, boolean power, byte... in) {
		super(id, targetID, inverted, power);
		this.in = in;
	}

	public MapObjectLogicOr() {

	}

	public boolean update(LevelMap map) {
		for (int i = 0; i < in.length; i++) {
			if (map.getMapObject(in[i]).getPower()) {
				return true;
			}
		}
		return false;
	}

	public byte[] getIn() {
		return in;
	}

	public void setIn(byte[] in) {
		this.in = in;
	}

}
