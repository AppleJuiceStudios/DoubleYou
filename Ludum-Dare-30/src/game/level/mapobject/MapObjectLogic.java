package game.level.mapobject;

import java.awt.Color;
import java.awt.Graphics2D;

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

	public void drawLogic(Graphics2D g2, int size) {
		g2.setColor(Color.GREEN);
		g2.fillRect(x * size, y * size + (size / 8), size, size * 3 / 4);
		g2.setColor(Color.BLACK);
		g2.drawRect(x * size, y * size + (size / 8), size, size * 3 / 4);
		g2.drawRect(x * size + 1, y * size + (size / 8) + 1, size - 2, size * 3 / 4 - 2);
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
