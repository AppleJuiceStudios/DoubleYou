package game.level.mapobject;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;
import game.level.entity.EntityPlayer;

@XmlRootElement
public class MapObjectTrigger extends MapObject {

	public MapObjectTrigger(int id, int x, int y, int width, int height) {
		super(id, x, y, width, height, false);
	}

	public MapObjectTrigger() {

	}

	public void updateTriger(EntityPlayer[] player, LevelMap map) {
		if (!power) {
			int pX = (int) ((player[0].getXPos() + (player[0].getWidth() / 2)) / 16);
			int pY = (int) ((player[0].getYPos() + player[0].getHeight() - 1) / 16);
			if (pX >= x & pX < x + width & pY >= y & pY < y + height) {
				power = true;
				action(player[0], map);
			}
		}
	}

	public void drawLogic(Graphics2D g2, int size) {
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(x * size, y * size, width * size, height * size);
		drawIO(g2, size);
	}

	protected void drawIO(Graphics2D g2, int size) {
		drawOutput(g2, width * size / 2 - (int) (1.5 * size / 16) + size * x, (y + height) * size - size * 3 / 16, size / 16 * 3, false);
	}

	protected void action(EntityPlayer player, LevelMap map) {

	}

}
