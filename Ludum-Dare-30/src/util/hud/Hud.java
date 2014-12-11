package util.hud;

import game.level.mapobject.MapObject;
import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class Hud {

	protected HudItem[] items;
	private int selected;
	private final int OFFSET;
	protected final int SIZE;

	public Hud(int size) {
		this.SIZE = size;
		selected = -1;
		OFFSET = (int) (SIZE * 0.25);
	}

	public void draw(Graphics2D g2) {
		g2.setColor(new Color(46, 49, 51, 200));
		g2.fillRect(0, GameCanvas.HEIGHT - SIZE - OFFSET, GameCanvas.WIDTH, SIZE + OFFSET);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				items[i].draw(g2, (SIZE + OFFSET) * i, GameCanvas.HEIGHT - SIZE - (OFFSET / 2));
				if (i == selected) {
					g2.setColor(Color.RED);
					g2.drawRect((SIZE + OFFSET) * i, GameCanvas.HEIGHT - SIZE - (OFFSET / 2), SIZE, SIZE);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		for (int i = 0; i < items.length; i++) {
			if (x > (SIZE + OFFSET) * i && x < (SIZE + OFFSET) * (i + 1)) {
				selected = i;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public int getHeight() {
		return SIZE;
	}

	public MapObject getObject() {
		if (selected == -1) {
			return null;
		}
		MapObject res = items[selected].getObject();
		selected = -1;
		return res;
	}
}