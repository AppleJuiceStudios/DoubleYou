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
