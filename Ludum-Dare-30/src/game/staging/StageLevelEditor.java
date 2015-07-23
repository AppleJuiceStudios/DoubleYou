package game.staging;

import game.level.TileSet;
import game.level.mapobject.MapObject;
import game.levelEditor.LevelMapEditable;
import game.levelEditor.tool.ToolbarWorld;
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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXB;

public class StageLevelEditor extends Stage {

	private File levelFile;
	private LevelMapEditable map;
	private ControlListener controls;

	private int scale = 3;
	private double xOffset = 0;
	private double yOffset = 0;

	private BufferedImage background;
	private BufferedImage mountains;
	private BufferedImage player;
	private TileSet tileSet;

	private ToolbarWorld toolbarWorld;

	public StageLevelEditor(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		loadMap(data);
		background = ResourceManager.getImage("/backgrounds/Mars-Background.png");
		mountains = ResourceManager.getImage("/planets/mars/Mars-Mountains.png");
		player = ResourceManager.getImage("/model/player/Player-Model.png");
		tileSet = new TileSet("/planets/mars/Mars-TileSet.png");

		toolbarWorld = new ToolbarWorld();

		controls = new ControlListener();
		getStageManager().setMouseListener(controls);
		getStageManager().setKeyListener(controls);
		getStageManager().setMouseMotionListener(controls);
		getStageManager().setMouseWheelListener(controls);
	}

	private void loadMap(Map<String, String> data) {
		File file = new File(data.get("file"));
		levelFile = file;
		if (file.exists()) {
			map = JAXB.unmarshal(file, LevelMapEditable.class);
		} else {
			throw new IllegalArgumentException("File not found: " + file.getPath());
		}
	}

	public void draw(Graphics2D g2) {

		drawBackground(g2, background);
		double mountainsOffset = 0;
		for (int i = (int) -mountainsOffset / (260 * 3); i <= ((int) -mountainsOffset + GameCanvas.WIDTH) / (260 * 3); i++) {
			g2.drawImage(mountains, i * 260 * 3 + (int) mountainsOffset, GameCanvas.HEIGHT - 260 * 3, 260 * 3, 260 * 3, null);
		}

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

		// Player Spawn
		g2.drawImage(player, map.getPlayerSpawnX() * spriteSize / 16, map.getPlayerSpawnY() * spriteSize / 16, spriteSize, spriteSize * 2, null);

		// Border
		g2.setColor(Color.YELLOW);
		for (int i = 1; i <= 1 + scale; i++) {
			g2.drawRect(0 - i, 0 - i, map.getWidth() * spriteSize + i * 2 - 1, map.getHeight() * spriteSize + i * 2 - 1);
		}

		// Objects
		map.drawObjects(g2, spriteSize);

		g2.setTransform(new AffineTransform());

		toolbarWorld.draw(g2, controls.mouse_X, controls.mouse_Y);
	}

	public void stop() {

	}

	public void scaleUp() {
		if (scale < 6) {
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
			if (e.getKeyCode() == KeyEvent.VK_PLUS) {
				scaleUp();
			} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				scaleDown();
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				getStageManager().exitGame();
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

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isMiddleMouseButton(e)) {
				xOffset += mouse_X - e.getX();
				yOffset += mouse_Y - e.getY();
			}
			mouse_X = e.getX();
			mouse_Y = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouse_X = e.getX();
			mouse_Y = e.getY();
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
