package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;

@XmlRootElement
public class MapObjectLogicAndKeeping extends MapObjectLogic {

	private int[] in;
	private boolean islocked;

	public MapObjectLogicAndKeeping(int id, int targetID, boolean inverted, boolean power, int... in) {
		super(id, targetID, inverted, power);
		this.in = in;
	}

	public MapObjectLogicAndKeeping() {

	}

	public boolean update(LevelMap map) {
		boolean b = true;
		for (int i = 0; i < in.length; i++) {
			if (!map.getMapObject(in[i]).getPower()) {
				b = false;
			}
		}
		if (b) {
			islocked = true;
		}
		return islocked;
	}

	public int[] getIn() {
		return in;
	}

	public void setIn(int[] in) {
		this.in = in;
	}

}
