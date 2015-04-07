package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.Transient;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectTrigger extends MapObject {

	private boolean wasPowered;
	private int targetID;

	public MapObjectTrigger(int id, int x, int y, int width, int height, int targetID) {
		super(id, x, y, width, height, false);
		this.targetID = targetID;
	}

	public MapObjectTrigger() {

	}

	public void updateTriger(LevelMap map, Entity... entities) {
		power = false;
		int pX = (int) ((entities[0].getXPos() + (entities[0].getWidth() / 2)) / 16);
		int pY = (int) ((entities[0].getYPos() + entities[0].getHeight() - 1) / 16);
		if (pX >= x & pX < x + width & pY >= y & pY < y + height) {
			power = true;
			action(map);
		}
	}

	public boolean hasOutput() {
		return true;
	}

	@Transient
	public int getOutput() {
		return targetID;
	}

	public void setOutput(int id) {
		targetID = id;
	}

	public void drawLogic(Graphics2D g2, int size) {
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(x * size, y * size, width * size, height * size);
		drawIO(g2, size);
	}

	protected void action(LevelMap map) {
		if (power && !wasPowered) {
			map.powerObject(targetID, true);
			wasPowered = true;
		}
		if (!power && wasPowered) {
			map.powerObject(targetID, false);
			wasPowered = false;
		}
	}

	public int getTargetID() {
		return targetID;
	}

	public void setTargetID(int targetID) {
		this.targetID = targetID;
	}

}
