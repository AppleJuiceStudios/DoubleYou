package util.hud;

import game.level.mapobject.MapObject;
import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

public abstract class Hud {

	private final Color HUD_COLOR;

	protected HudItem[] items;
	private int selected;
	private final int OFFSET;
	protected final int SIZE;

	private final int X_STEP;
	private int xStart;
	private final int Y;
	private final int CORNER_WIDTH;
	private final int CORNER_HEIGHT;

	private final int ITEM_HEIGHT;

	public Hud(int size, int cornerOffset) {
		this.SIZE = size;
		HUD_COLOR = new Color(255, 255, 255, 200);
		selected = -1;

		OFFSET = (int) (size * 0.25);
		X_STEP = size + OFFSET;
		Y = GameCanvas.HEIGHT - size - OFFSET;

		CORNER_WIDTH = size * 2;
		CORNER_HEIGHT = X_STEP * 2;

		ITEM_HEIGHT = GameCanvas.HEIGHT - size - (OFFSET / 2);

		if (cornerOffset < 0) {
			xStart = -1;
		} else {
			xStart = cornerOffset + size;
		}
	}

	public Hud(int size) {
		this(size, -1);
	}

	public void draw(Graphics2D g2) {
		if (xStart < 0) {
			xStart = (GameCanvas.WIDTH - items.length * X_STEP) / 2;
		}

		g2.setColor(HUD_COLOR);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.fillArc(xStart - SIZE, Y, CORNER_WIDTH, CORNER_HEIGHT, 90, 90);
		g2.fillArc(GameCanvas.WIDTH - (xStart - SIZE + CORNER_WIDTH), Y, CORNER_WIDTH, CORNER_HEIGHT, 0, 90);

		g2.fillRect(xStart, Y, GameCanvas.WIDTH - (2 * xStart), X_STEP);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				int pos = (X_STEP * i) + xStart;
				items[i].draw(g2, pos, ITEM_HEIGHT);
				if (i == selected) {
					g2.setColor(Color.RED);
					g2.drawRect(pos, ITEM_HEIGHT, SIZE, SIZE);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		for (int i = 0; i < items.length; i++) {
			int pos = (X_STEP * i) + xStart;
			if (x > pos && x < (X_STEP * (i + 1)) + xStart) {
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