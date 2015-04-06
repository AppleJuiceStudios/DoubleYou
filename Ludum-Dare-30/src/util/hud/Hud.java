package util.hud;

import game.level.mapobject.MapObject;
import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

public abstract class Hud {

	public final static int ORIENTATION_TOP_RIGHT = 0;
	public final static int ORIENTATION_TOP_LEFT = 1;
	public final static int ORIENTATION_TOP_CENTER = 2;
	public final static int ORIENTATION_BOTTOM_CENTER = 3;

	private final Color HUD_COLOR;

	protected HudItem[] items;
	private boolean scrolling;
	private int scrollingIndex = 0;
	private int selected;
	private final int ORIENTATION;
	private final int OFFSET;
	protected final int SIZE;

	private final int X_STEP;
	private int xStart;
	private final int Y;
	private final int CORNER_Y;
	private final int CORNER_WIDTH;
	private final int CORNER_HEIGHT;

	private final int CORNER_START_ANGLE_LEFT;
	private final int CORNER_START_ANGLE_RIGHT;

	private final int ITEM_HEIGHT;

	public Hud(int size, int orientation) {
		this.SIZE = size;
		this.ORIENTATION = orientation;
		HUD_COLOR = new Color(255, 255, 255, 150);
		selected = -1;

		OFFSET = (int) (size * 0.25);
		X_STEP = size + OFFSET;

		if (orientation == ORIENTATION_TOP_RIGHT) {
			Y = 0;
			CORNER_Y = -X_STEP;
			ITEM_HEIGHT = OFFSET / 2;
			CORNER_START_ANGLE_LEFT = 180;
			CORNER_START_ANGLE_RIGHT = 270;
		} else if (orientation == ORIENTATION_TOP_LEFT) {
			Y = 0;
			CORNER_Y = -X_STEP;
			ITEM_HEIGHT = OFFSET / 2;
			CORNER_START_ANGLE_LEFT = 180;
			CORNER_START_ANGLE_RIGHT = 270;
		} else if (orientation == ORIENTATION_TOP_CENTER) {
			Y = 0;
			CORNER_Y = -X_STEP;
			ITEM_HEIGHT = OFFSET / 2;
			CORNER_START_ANGLE_LEFT = 180;
			CORNER_START_ANGLE_RIGHT = 270;
		} else {
			Y = GameCanvas.HEIGHT - size - OFFSET;
			CORNER_Y = Y;
			ITEM_HEIGHT = GameCanvas.HEIGHT - size - (OFFSET / 2);
			CORNER_START_ANGLE_LEFT = 90;
			CORNER_START_ANGLE_RIGHT = 0;
		}

		CORNER_WIDTH = size * 2;
		CORNER_HEIGHT = X_STEP * 2;

		xStart = -1;
	}

	public void draw(Graphics2D g2) {
		if (xStart < 0) {
			if (ORIENTATION == ORIENTATION_TOP_RIGHT) {
				xStart = GameCanvas.WIDTH - (items.length * X_STEP + CORNER_WIDTH / 2);
			} else if (ORIENTATION == ORIENTATION_TOP_LEFT) {
				xStart = CORNER_WIDTH / 2;
			} else if (ORIENTATION == ORIENTATION_TOP_CENTER) {
				xStart = (GameCanvas.WIDTH - items.length * X_STEP) / 2;
			} else {
				xStart = (GameCanvas.WIDTH - items.length * X_STEP) / 2;
			}
		}

		g2.setColor(HUD_COLOR);
		if (g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING) == RenderingHints.VALUE_ANTIALIAS_OFF)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.fillArc(xStart - SIZE, CORNER_Y, CORNER_WIDTH, CORNER_HEIGHT, CORNER_START_ANGLE_LEFT, 90);
		g2.fillArc(xStart + X_STEP * items.length - CORNER_WIDTH / 2, CORNER_Y, CORNER_WIDTH, CORNER_HEIGHT, CORNER_START_ANGLE_RIGHT, 90);

		g2.fillRect(xStart, Y, X_STEP * items.length, X_STEP);

		if (items != null) {
			if (items.length > 14)
				scrolling = true;
			else
				scrolling = false;

			if (scrolling) {
				// drawArrows;
				g2.drawImage(ResourceManager.getImage("/gui/arrow.png"), xStart + SIZE - 10, ITEM_HEIGHT, (int) (-SIZE * 0.6), SIZE, null);
				g2.drawImage(ResourceManager.getImage("/gui/arrow.png"), (X_STEP * 14) + xStart + 10, ITEM_HEIGHT, (int) (SIZE * 0.6), SIZE, null);

				for (int i = 1; i < 14; i++) {
					int pos = (X_STEP * i) + xStart;
					items[i + scrollingIndex].draw(g2, pos, ITEM_HEIGHT);
					if (i + scrollingIndex == selected) {
						g2.setColor(Color.RED);
						g2.drawRect(pos, ITEM_HEIGHT, SIZE, SIZE);
					}
				}
			} else {
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

	public int getY() {
		return Y;
	}

	public int getLeftBorder() {
		if (xStart - SIZE < 0)
			return 0;
		return xStart - SIZE;
	}

	public int getRightBorder() {
		return xStart + X_STEP * items.length + CORNER_WIDTH / 2;
	}

	public boolean contains(Point p) {
		return p.getY() > getY() && p.getY() < getY() + getHeight() && p.getX() < getRightBorder() && p.getX() > getLeftBorder();
	}

	public MapObject getObject() {
		if (selected == -1) {
			return null;
		}
		MapObject res = items[selected].getObject();
		selected = -1;
		return res;
	}

	public int getSelected() {
		int res = selected;
		selected = -1;
		return res;
	}
}