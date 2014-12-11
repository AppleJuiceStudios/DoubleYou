package game.staging;

import game.level.LevelMapEditor;
import game.level.TileSet;
import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectLasergateHorizontal;
import game.level.mapobject.MapObjectLogic;
import game.level.mapobject.MapObjectTrigger;
import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXB;

import util.hud.Hud;
import util.hud.LogicHud;
import util.hud.ObjectHud;

public class StageEditor extends Stage {

	private int scale = 3;
	private double xOffset = 0;
	private double yOffset = 0;
	private double movementSpeed = 5;

	private ControlListener controls;
	private Timer updateTimer;

	private BufferedImage background;
	private BufferedImage mountains;
	private LevelMapEditor map;
	private TileSet tileSet;

	private int selectedX;
	private int selectedY;
	private int lastSelectionX;
	private int lastSelectionY;
	private boolean isSelecting;
	// EDITMODE_OBJECT
	private MapObject selectedMapObject;

	private int nextID = 30000;

	public static final int EDITMODE_MAP = 0;
	public static final int EDITMODE_OBJECT = 1;
	public static final int EDITMODE_LOGIC = 2;
	private int editMode = EDITMODE_MAP;

	private Hud logicHud;
	private Hud objectHud;

	// region Stage

	public StageEditor(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		loadMap(data);
		background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
		mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
		tileSet = new TileSet();
		controls = new ControlListener();
		getStageManager().setMouseListener(controls);
		getStageManager().setKeyListener(controls);
		getStageManager().setMouseMotionListener(controls);
		getStageManager().setMouseWheelListener(controls);
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				update();
			}
		}, 0, 1000 / 60);

		logicHud = new LogicHud();
		objectHud = new ObjectHud();
	}

	private void loadMap(Map<String, String> data) {
		File file = new File(data.get("file"));
		if (file.exists()) {
			map = JAXB.unmarshal(file, LevelMapEditor.class);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 800, 600, null);
		g2.drawImage(mountains, 0, -190, 800, 800, null);
		AffineTransform at = new AffineTransform();
		at.translate((int) -xOffset, (int) -yOffset);
		g2.setTransform(at);
		int spriteSize = TileSet.SPRITE_SIZE * scale;

		// Map
		int xStart = (int) (xOffset / (spriteSize));
		int yStart = (int) (yOffset / (spriteSize));
		int xEnd = xStart + GameCanvas.WIDTH / (spriteSize) + 2;
		int yEnd = yStart + GameCanvas.HEIGHT / (spriteSize) + 2;
		xStart = Math.max(0, xStart);
		yStart = Math.max(0, yStart);
		xEnd = Math.min(map.getWidth(), xEnd);
		yEnd = Math.min(map.getHeight(), yEnd);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				BufferedImage image = tileSet.getSprite(map.getTileID(x, y));
				if (image != null) {
					g2.drawImage(image, x * spriteSize, y * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}

		// Border
		g2.setColor(Color.YELLOW);
		for (int i = 1; i <= 1 + scale; i++) {
			g2.drawRect(0 - i, 0 - i, map.getWidth() * spriteSize + i * 2 - 1, map.getHeight() * spriteSize + i * 2 - 1);
		}

		// Selection
		if (editMode == EDITMODE_MAP) {
			g2.setColor(Color.BLUE);
		} else {
			g2.setColor(Color.LIGHT_GRAY);
		}
		if (isSelecting) {
			int selectionStartX = Math.min(selectedX, lastSelectionX);
			int selectionStartY = Math.min(selectedY, lastSelectionY);
			int selectionWidth = Math.abs(selectedX - lastSelectionX) + 1;
			int selectionHeight = Math.abs(selectedY - lastSelectionY) + 1;
			for (int i = 1; i <= 1 + scale; i++) {
				g2.drawRect(selectionStartX * spriteSize - i, selectionStartY * spriteSize - i, selectionWidth * spriteSize + i * 2 - 1, selectionHeight
						* spriteSize + i * 2 - 1);
			}
		} else {
			for (int i = 1; i <= 1 + scale; i++) {
				g2.drawRect(selectedX * spriteSize - i, selectedY * spriteSize - i, spriteSize + i * 2 - 1, spriteSize + i * 2 - 1);
			}
		}
		if (editMode == EDITMODE_OBJECT) {
			if (selectedMapObject != null) {
				g2.setColor(Color.BLUE);
				int selectionStartX = selectedMapObject.getX();
				int selectionStartY = selectedMapObject.getY();
				int selectionWidth = selectedMapObject.getWidth();
				int selectionHeight = selectedMapObject.getHeight();
				for (int i = 1; i <= 1 + scale; i++) {
					g2.drawRect(selectionStartX * spriteSize - i, selectionStartY * spriteSize - i, selectionWidth * spriteSize + i * 2 - 1, selectionHeight
							* spriteSize + i * 2 - 1);
				}
			}
		}

		// Objects
		map.drawObjects(g2, spriteSize);
		if (editMode == EDITMODE_LOGIC) {
			map.drawLogicObjects(g2, spriteSize);
			map.drawLogicConnections(g2, spriteSize);
		}
		g2.setTransform(new AffineTransform());

		// GUI
		if (editMode == EDITMODE_LOGIC) {
			logicHud.draw(g2);
		} else if (editMode == EDITMODE_OBJECT) {
			objectHud.draw(g2);
		}
	}

	public void update() {
		double speed = this.movementSpeed;
		if (controls.Key_Shift) {
			speed *= 3;
		}
		if (controls.Key_W) {
			yOffset -= speed;
		}
		if (controls.Key_A) {
			xOffset -= speed;
		}
		if (controls.Key_S) {
			yOffset += speed;
		}
		if (controls.Key_D) {
			xOffset += speed;
		}
	}

	public void stop() {
		updateTimer.cancel();
	}

	// endregion Stage

	// region ObjectPlacement

	public void placeObject(MapObject object) {
		object.setId(getNextID());
		object.setX(selectedX);
		object.setY(selectedY);
		object.setWidth(1);
		object.setHeight(1);
		if (object instanceof MapObjectTrigger) {
			object.setX(Math.min(selectedX, lastSelectionX));
			object.setY(Math.min(selectedY, lastSelectionY));
			object.setWidth(Math.abs(selectedX - lastSelectionX) + 1);
			object.setHeight(Math.abs(selectedY - lastSelectionY) + 1);
		} else if (object instanceof MapObjectLasergate) {
			object.setY(Math.min(selectedY, lastSelectionY));
			object.setHeight(Math.abs(selectedY - lastSelectionY) + 1);
		} else if (object instanceof MapObjectLasergateHorizontal) {
			object.setX(Math.min(selectedX, lastSelectionX));
			object.setWidth(Math.abs(selectedX - lastSelectionX) + 1);
		}
		map.addMapObject(object);
	}

	// endregion ObjectPlacement

	// region Utility

	public void swichEditMode() {
		selectedMapObject = null;
		isSelecting = false;
		if (editMode == EDITMODE_MAP) {
			editMode = EDITMODE_OBJECT;
		} else if (editMode == EDITMODE_OBJECT) {
			editMode = EDITMODE_LOGIC;
		} else {
			editMode = EDITMODE_MAP;
		}
	}

	public void scaleUp() {
		if (scale < 4) {
			xOffset = (xOffset + controls.mouse_X) * (scale + 1) / scale - controls.mouse_X;
			yOffset = (yOffset + controls.mouse_Y) * (scale + 1) / scale - controls.mouse_Y;
			scale++;
		}
	}

	public void scaleDown() {
		if (scale > 1) {
			xOffset = (xOffset + controls.mouse_X) * (scale - 1) / scale - controls.mouse_X;
			yOffset = (yOffset + controls.mouse_Y) * (scale - 1) / scale - controls.mouse_Y;
			scale--;
		}
	}

	public boolean isLogicMapObject(MapObject object) {
		return object instanceof MapObjectLogic || object instanceof MapObjectTrigger;
	}

	public int getNextID() {
		while (map.getMapObject(nextID) != null) {
			nextID++;
		}
		return nextID;
	}

	// endregion Utility

	private class ControlListener implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {

		private double mouse_X;
		private double mouse_Y;

		private boolean Key_W;
		private boolean Key_A;
		private boolean Key_S;
		private boolean Key_D;
		private boolean Key_Shift;

		// region Keys

		@Override
		public void keyPressed(KeyEvent e) {
			if (editMode == EDITMODE_MAP) {
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_AIR);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_SOUTH_WEST);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_SOUTH);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_SOUTH_EAST);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_WEST);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_CENTER);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_EAST);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_NORTH_WEST);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_NORTH);
				} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
					map.setTileID(selectedX, selectedY, TileSet.TILE_NORTH_EAST);
				} else if (e.getKeyCode() == KeyEvent.VK_X) {
					map.expandMapX(10);
				} else if (e.getKeyCode() == KeyEvent.VK_Y) {
					map.expandMapY(10);
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					map.rescaleMap();
				}
			} else if (editMode == EDITMODE_OBJECT) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (selectedMapObject != null) {
						map.removeMapObject(selectedMapObject);
						selectedMapObject = null;
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_PLUS) {
				scaleUp();
			} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				scaleDown();
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				swichEditMode();
			}
			keyUpdate(e.getKeyCode(), true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keyUpdate(e.getKeyCode(), false);
		}

		private void keyUpdate(int keyCode, boolean pressed) {
			if (keyCode == KeyEvent.VK_W) {
				Key_W = pressed;
			} else if (keyCode == KeyEvent.VK_A) {
				Key_A = pressed;
			} else if (keyCode == KeyEvent.VK_S) {
				Key_S = pressed;
			} else if (keyCode == KeyEvent.VK_D) {
				Key_D = pressed;
			} else if (keyCode == KeyEvent.VK_SHIFT) {
				Key_Shift = pressed;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		// endregion Keys

		// region Mouse

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getY() > GameCanvas.HEIGHT - logicHud.getHeight()) {
				if (editMode == EDITMODE_LOGIC)
					logicHud.mousePressed(e);
				if (editMode == EDITMODE_OBJECT)
					objectHud.mousePressed(e);
			} else {
				if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
					lastSelectionX = selectedX;
					lastSelectionY = selectedY;
					isSelecting = true;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getY() > GameCanvas.HEIGHT - logicHud.getHeight()) {
				if (editMode == EDITMODE_LOGIC)
					logicHud.mouseReleased(e);
				if (editMode == EDITMODE_OBJECT)
					objectHud.mouseReleased(e);
				return;
			}

			isSelecting = false;
			if (editMode == EDITMODE_MAP) {
				if (selectedX == lastSelectionX && selectedY == lastSelectionY) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						map.setTile(selectedX, selectedY);
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						map.removeTile(selectedX, selectedY);
					}
				} else {
					if (e.getButton() == MouseEvent.BUTTON1) {
						map.fillRect(selectedX, selectedY, lastSelectionX, lastSelectionY);
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						map.clearRect(selectedX, selectedY, lastSelectionX, lastSelectionY);
					}
				}
			} else if (editMode == EDITMODE_OBJECT) {
				selectedMapObject = null;
				MapObject[] objects = map.getMapObjects();
				for (int i = 0; i < objects.length; i++) {
					if (selectedX >= objects[i].getX() && selectedX < objects[i].getX() + objects[i].getWidth()) {
						if (selectedY >= objects[i].getY() && selectedY < objects[i].getY() + objects[i].getHeight()) {
							if (!isLogicMapObject(objects[i])) {
								selectedMapObject = objects[i];
							}
						}
					}
				}
				if (selectedMapObject == null) {
					placeObject(objectHud.getObject());
				}
			} else if (editMode == EDITMODE_LOGIC) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					selectedMapObject = null;
					MapObject[] objects = map.getMapObjects();
					for (int i = 0; i < objects.length; i++) {
						if (selectedX >= objects[i].getX() && selectedX < objects[i].getX() + objects[i].getWidth()) {
							if (selectedY >= objects[i].getY() && selectedY < objects[i].getY() + objects[i].getHeight()) {
								if (isLogicMapObject(objects[i])) {
									selectedMapObject = objects[i];
								}
							}
						}
					}
					if (selectedMapObject == null) {
						placeObject(logicHud.getObject());
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isMiddleMouseButton(e)) {
				xOffset += mouse_X - e.getX();
				yOffset += mouse_Y - e.getY();
			}
			mouse_X = e.getX();
			mouse_Y = e.getY();
			updateSelection();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouse_X = e.getX();
			mouse_Y = e.getY();
			updateSelection();
		}

		private void updateSelection() {
			int spriteSize = TileSet.SPRITE_SIZE * scale;
			selectedX = (int) ((controls.mouse_X + xOffset) / (spriteSize));
			selectedY = (int) ((controls.mouse_Y + yOffset) / (spriteSize));
			if (selectedX < 0) {
				selectedX = 0;
			} else if (selectedX >= map.getWidth()) {
				selectedX = map.getWidth() - 1;
			}
			if (selectedY < 0) {
				selectedY = 0;
			} else if (selectedY >= map.getHeight()) {
				selectedY = map.getHeight() - 1;
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int rotation = e.getWheelRotation();
			if (rotation < 0)
				scaleUp();
			if (rotation > 0)
				scaleDown();
		}

		// endregion Mouse

	}

}
