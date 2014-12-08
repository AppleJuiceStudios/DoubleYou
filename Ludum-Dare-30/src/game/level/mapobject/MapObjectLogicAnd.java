package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;

@XmlRootElement
public class MapObjectLogicAnd extends MapObjectLogic {

	private int[] in;

	public MapObjectLogicAnd(int id, int targetID, boolean inverted, boolean power, int... in) {
		super(id, targetID, inverted, power);
		this.in = in;
	}

	public MapObjectLogicAnd() {

	}

	public boolean update(LevelMap map) {
		for (int i = 0; i < in.length; i++) {
			if (!map.getMapObject(in[i]).getPower()) {
				return false;
			}
		}
		return true;
	}

	public int inputCount() {
		return in.length;
	}

	public int[] getInputs() {
		return in;
	}

	public boolean moreInputs() {
		return true;
	}

	public void setInput(int index, int id) {
		if (id != 0 && id != -1) {
			in[index] = id;
		} else if (index == in.length) {
			int[] newIn = new int[in.length + 1];
			for (int i = 0; i < newIn.length; i++) {
				newIn[i] = in[i];
			}
			in = newIn;
		} else {
			int[] newIn = new int[in.length - 1];
			for (int i = 0; i < newIn.length; i++) {
				newIn[i] = i < index ? in[i] : in[i + 1];
			}
			in = newIn;
		}
	}

	public int[] getIn() {
		return in;
	}

	public void setIn(int[] in) {
		this.in = in;
	}

}
