package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;

@XmlRootElement
public class MapObjectLogicOr extends MapObjectLogic {

	private int[] in;

	public MapObjectLogicOr(int id, int targetID, boolean inverted, boolean power, int... in) {
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

	public int[] getIn() {
		return in;
	}

	public void setIn(int[] in) {
		this.in = in;
	}

}
