package game.staging;

import game.level.LevelMap;
import game.level.TileSet;
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

		int selectedX = (int) ((controls.mouse_X + xOffset) / (spriteSize));
		int selectedY = (int) ((controls.mouse_Y + yOffset) / (spriteSize));
		g2.setColor(Color.BLUE);
		for (int i = 1; i <= 1 + scale; i++) {
			g2.drawRect(selectedX * (spriteSize) - i, selectedY * (spriteSize) - i, (spriteSize) + i * 2 - 1, (spriteSize) + i * 2 - 1);
		}

		map.drawObjects(g2, spriteSize);
		g2.setTransform(new AffineTransform());
		/**
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

	private class ControlListener implements MouseListener, KeyListener, MouseMotionListener {

		private double mouse_X;
		private double mouse_Y;

		private boolean Key_W;
		private boolean Key_A;
		private boolean Key_S;
		private boolean Key_D;
		private boolean Key_Shift;

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_PLUS) {
				scaleUp();
			} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				scaleDown();
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
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouse_X = e.getX();
			mouse_Y = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouse_X = e.getX();
			mouse_Y = e.getY();
		}

	}

}
