package game.level.mapobject;

import java.beans.Transient;

import game.level.LevelMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectLogicAnd extends MapObjectLogic {

	private int[] in;

	public MapObjectLogicAnd(int id, int targetID, boolean inverted, boolean power, int... in) {
		super(id, targetID, inverted, power);
		this.in = in;
		this.name = "&";
	}

	public MapObjectLogicAnd() {
		in = new int[0];
		this.name = "&";
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

	@Transient
	public int[] getInputs() {
		return in;
	}

	public boolean moreInputs() {
		return true;
	}

	public void setInput(int index, int id) {
		if (index == in.length) {
			int[] newIn = new int[in.length + 1];
			for (int i = 0; i < in.length; i++) {
				newIn[i] = in[i];
			}
			newIn[in.length] = id;
			in = newIn;
		} else if (id == 0 || id == -1) {
			int[] newIn = new int[in.length - 1];
			for (int i = 0; i < newIn.length; i++) {
				newIn[i] = i < index ? in[i] : in[i + 1];
			}
			in = newIn;
		} else {
			in[index] = id;
		}
	}

	public int[] getIn() {
		return in;
	}

	public void setIn(int[] in) {
		this.in = in;
	}

}
