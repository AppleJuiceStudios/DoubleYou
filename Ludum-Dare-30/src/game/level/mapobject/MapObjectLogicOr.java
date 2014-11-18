package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.map.LevelMap;

@XmlRootElement
public class MapObjectLogicOr extends MapObjectLogic {

	private byte in1;
	private byte in2;

	public MapObjectLogicOr(byte id, byte in1, byte in2, byte targetID, boolean inverted, boolean power) {
		super(id, targetID, inverted, power);
		this.in1 = in1;
		this.in2 = in2;
	}

	public MapObjectLogicOr() {

	}

	public boolean update(LevelMap map) {
		boolean b1 = map.getMapObject(in1).getPower();
		boolean b2 = map.getMapObject(in2).getPower();
		return b1 | b2;
	}

	public byte getIn1() {
		return in1;
	}

	public void setIn1(byte in1) {
		this.in1 = in1;
	}

	public byte getIn2() {
		return in2;
	}

	public void setIn2(byte in2) {
		this.in2 = in2;
	}

}
