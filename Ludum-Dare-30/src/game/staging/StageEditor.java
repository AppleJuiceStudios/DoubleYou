package game.staging;

import game.level.LevelMap;
import game.level.TileSet;
import game.level.mapobject.MapObject;
import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXB;

public class StageEditor extends Stage {

	private int scale = 3;
	private double xOffset = 0;
	private double yOffset = 0;
	private double movementSpeed = 5;

	private BufferedImage background;
	private BufferedImage mountains;
	private LevelMap map;
	private TileSet tileSet;

	private ControlListener controls;
	private Timer updateTimer;

	private int selectedX;
	private int selectedY;
	private int lastSelectionX;
	private int lastSelectionY;
	private boolean isSelecting;

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
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				update();
			}
		}, 0, 1000 / 60);

	}

	private void loadMap(Map<String, String> data) {
		File file = new File(data.get("file"));
		if (file.exists()) {
			map = JAXB.unmarshal(file, LevelMap.class);
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

		g2.setColor(Color.YELLOW);
		for (int i = 1; i <= 1 + scale; i++) {
			g2.drawRect(0 - i, 0 - i, map.getWidth() * spriteSize + i * 2 - 1, map.getHeight() * spriteSize + i * 2 - 1);
		}

		g2.setColor(Color.BLUE);
		if (isSelecting) {
			int selectionStartX = Math.min(selectedX, lastSelectionX);
			int selectionStartY = Math.min(selectedY, lastSelectionY);
			int selectionWidth = Math.abs(selectedX - lastSelectionX) + 1;
			int selectionHeight = Math.abs(selectedY - lastSelectionY) + 1;
			for (int i = 1; i <= 1 + scale; i++) {
				g2.drawRect(selectionStartX * spriteSize - i, selectionStartY * spriteSize - i, selectionWidth * spriteSize + i * 2 - 1,
						selectionHeight * spriteSize + i * 2 - 1);
			}
		} else {
			for (int i = 1; i <= 1 + scale; i++) {
				g2.drawRect(selectedX * spriteSize - i, selectedY * spriteSize - i, spriteSize + i * 2 - 1, spriteSize + i * 2 - 1);
			}
		}

		map.drawObjects(g2, spriteSize);
		g2.setTransform(new AffineTransform());
		/*
		 * GUI
		 */
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

	// region MapEdit

	public void setTileID(int x, int y, byte tileID) {
		map.getSpritesheet()[x][y] = tileID;
	}

	public void setTile(int x, int y) {
		map.getSpritesheet()[x][y] = TileSet.TILE_CENTER;
		updateTile(x, y);
		updateTile(x + 1, y);
		updateTile(x - 1, y);
		updateTile(x, y + 1);
		updateTile(x, y - 1);
	}

	public void removeTile(int x, int y) {
		map.getSpritesheet()[x][y] = TileSet.TILE_AIR;
		updateTile(x + 1, y);
		updateTile(x - 1, y);
		updateTile(x, y + 1);
		updateTile(x, y - 1);
	}

	public void fillRect(int x1, int y1, int x2, int y2) {
		int startX = Math.min(x1, x2);
		int startY = Math.min(y1, y2);
		int endX = Math.max(x1, x2);
		int endY = Math.max(y1, y2);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				map.getSpritesheet()[x][y] = TileSet.TILE_CENTER;
			}
		}
		for (int x = startX; x <= endX; x++) {
			updateTile(x, startY);
			updateTile(x, endY);
			updateTile(x, startY - 1);
			updateTile(x, endY + 1);
		}
		for (int y = startY; y <= endY; y++) {
			updateTile(startX, y);
			updateTile(endX, y);
			updateTile(startX - 1, y);
			updateTile(endX + 1, y);
		}
	}

	public void clearRect(int x1, int y1, int x2, int y2) {
		int startX = Math.min(x1, x2);
		int startY = Math.min(y1, y2);
		int endX = Math.max(x1, x2);
		int endY = Math.max(y1, y2);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				map.getSpritesheet()[x][y] = TileSet.TILE_AIR;
			}
		}
		for (int x = startX; x <= endX; x++) {
			updateTile(x, startY - 1);
			updateTile(x, endY + 1);
		}
		for (int y = startY; y <= endY; y++) {
			updateTile(startX - 1, y);
			updateTile(endX + 1, y);
		}
	}

	public void updateTile(int x, int y) {
		if (map.isBlock(x, y) && (x >= 0 & x < map.getWidth()) && (y >= 0 & y < map.getHeight())) {
			boolean north = map.isBlock(x, y - 1);
			boolean south = map.isBlock(x, y + 1);
			boolean west = map.isBlock(x - 1, y);
			boolean east = map.isBlock(x + 1, y);
			if (!north && !south && !west && !east)
				map.getSpritesheet()[x][y] = TileSet.TILE_CENTER;
			if (!north && !west && east)
				map.getSpritesheet()[x][y] = TileSet.TILE_NORTH_WEST;
			if (!north && west && !east)
				map.getSpritesheet()[x][y] = TileSet.TILE_NORTH_EAST;
			if (!north && west && east)
				map.getSpritesheet()[x][y] = TileSet.TILE_NORTH;
			if (!north && south && !west && !east)
				map.getSpritesheet()[x][y] = TileSet.TILE_NORTH;
			if (north && !south && west == east)
				map.getSpritesheet()[x][y] = TileSet.TILE_SOUTH;
			if (north && !south && !west && east)
				map.getSpritesheet()[x][y] = TileSet.TILE_SOUTH_WEST;
			if (north && !south && west && !east)
				map.getSpritesheet()[x][y] = TileSet.TILE_SOUTH_EAST;
			if (north && south && west == east)
				map.getSpritesheet()[x][y] = TileSet.TILE_CENTER;
			if (north && south && !west && east)
				map.getSpritesheet()[x][y] = TileSet.TILE_WEST;
			if (north && south && west && !east)
				map.getSpritesheet()[x][y] = TileSet.TILE_EAST;
		}
	}

	public void expandMapX(int expandX) {
		byte[][] oldSpritesheet = map.getSpritesheet();
		byte[][] spritesheet = new byte[map.getWidth() + expandX][map.getHeight()];
		for (int x = 0; x < oldSpritesheet.length; x++) {
			for (int y = 0; y < oldSpritesheet[0].length; y++) {
				spritesheet[x][y] = oldSpritesheet[x][y];
			}
		}
		map.setSpritesheet(spritesheet);
	}

	public void expandMapY(int expandY) {
		byte[][] oldSpritesheet = map.getSpritesheet();
		byte[][] spritesheet = new byte[map.getWidth()][map.getHeight() + expandY];
		for (int x = 0; x < oldSpritesheet.length; x++) {
			for (int y = 0; y < oldSpritesheet[0].length; y++) {
				spritesheet[x][y + expandY] = oldSpritesheet[x][y];
			}
		}
		MapObject[] objects = map.getObjects();
		for (int i = 0; i < objects.length; i++) {
			objects[i].setY(objects[i].getY() + expandY);
		}
		map.setSpritesheet(spritesheet);
	}

	public void rescale() {

	}

	// endregion MapEdit

	// region Scale

	public void scaleUp() {
		if (scale < 4) {
			xOffset = (xOffset + 400) * (scale + 1) / scale - 400;
			yOffset = (yOffset + 300) * (scale + 1) / scale - 300;
			scale++;
		}
	}

	public void scaleDown() {
		if (scale > 1) {
			xOffset = (xOffset + 400) * (scale - 1) / scale - 400;
			yOffset = (yOffset + 300) * (scale - 1) / scale - 300;
			scale--;
		}
	}

	// endregion Scale

	private class ControlListener implements MouseListener, KeyListener, MouseMotionListener {

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
			if (e.getKeyCode() == KeyEvent.VK_PLUS) {
				scaleUp();
			} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				scaleDown();
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
				setTileID(selectedX, selectedY, TileSet.TILE_AIR);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				setTileID(selectedX, selectedY, TileSet.TILE_SOUTH_WEST);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				setTileID(selectedX, selectedY, TileSet.TILE_SOUTH);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				setTileID(selectedX, selectedY, TileSet.TILE_SOUTH_EAST);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				setTileID(selectedX, selectedY, TileSet.TILE_WEST);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				setTileID(selectedX, selectedY, TileSet.TILE_CENTER);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				setTileID(selectedX, selectedY, TileSet.TILE_EAST);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
				setTileID(selectedX, selectedY, TileSet.TILE_NORTH_WEST);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				setTileID(selectedX, selectedY, TileSet.TILE_NORTH);
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
				setTileID(selectedX, selectedY, TileSet.TILE_NORTH_EAST);
			} else if (e.getKeyCode() == KeyEvent.VK_X) {
				expandMapX(10);
			} else if (e.getKeyCode() == KeyEvent.VK_Y) {
				expandMapY(10);
			} else {
				keyUpdate(e.getKeyCode(), true);
			}
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
			if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
				lastSelectionX = selectedX;
				lastSelectionY = selectedY;
				isSelecting = true;
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isSelecting = false;
			if (selectedX == lastSelectionX && selectedY == lastSelectionY) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					setTile(selectedX, selectedY);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					removeTile(selectedX, selectedY);
				}
			} else {
				if (e.getButton() == MouseEvent.BUTTON1) {
					fillRect(selectedX, selectedY, lastSelectionX, lastSelectionY);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					clearRect(selectedX, selectedY, lastSelectionX, lastSelectionY);
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

		// endregion Mouse

	}

}
