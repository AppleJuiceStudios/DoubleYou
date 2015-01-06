package game.level.mapobject;

import game.level.LevelMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectLogic extends MapObject {

	private boolean inverted;
	private int targetID;
	protected String name;

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
		if (isPower()) {
			g2.setColor(Color.GREEN);
		} else {
			g2.setColor(Color.RED);
		}
		g2.fillRect(x * size, y * size + (size / 16 * 3), size, size * 10 / 16);
		g2.setColor(Color.BLACK);
		g2.drawRect(x * size, y * size + (size / 16 * 3), size - 1, size * 10 / 16 - 1);
		g2.drawRect(x * size + 1, y * size + (size / 16 * 3) + 1, size - 3, size * 10 / 16 - 3);

		g2.setFont(new Font("Dialog", Font.PLAIN, size / 2));
		g2.drawString(name, x * size + size / 8, (y + 1) * size - size / 4);

		drawIO(g2, size);
	}

	public boolean hasOutput() {
		return true;
	}

	public int getOutput() {
		return targetID;
	}

	public boolean isOutputInverted() {
		return inverted;
	}

	public void invertOutput() {
		inverted = !inverted;
	}

	public void setOutput(int id) {
		targetID = id;
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
