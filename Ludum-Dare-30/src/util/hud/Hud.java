package util.hud;

import game.level.mapobject.MapObject;
import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class Hud {

	private int height;
	protected HudItem[] items;
	private int selected;

	public Hud() {
		height = 50;
		selected = -1;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(new Color(46, 49, 51, 200));
		g2.fillRect(0, GameCanvas.HEIGHT - height, GameCanvas.WIDTH, height);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				items[i].draw(g2, (height + 10) * i, GameCanvas.HEIGHT - height + 5);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getX() > items.length * HudItem.SIZE) {

		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public int getHeight() {
		return height;
	}

	public MapObject getObject() {
		if (selected == -1) {
			return null;
		}
		return items[selected].getObject();
	}
}