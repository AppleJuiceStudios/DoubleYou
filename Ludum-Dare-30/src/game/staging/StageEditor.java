package game.staging;

import game.level.LevelMap;
import game.level.TileSet;
import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXB;

public class StageEditor extends Stage {

	public static final int SCALE = 3;
	private double xOffset = 0;
	private double yOffset = 0;
	private double maxXOffset;
	private double maxYOffset;

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
		maxXOffset = (map.getWidth() * TileSet.SPRITE_SIZE * SCALE) - 800;
		maxYOffset = (map.getHeight() * TileSet.SPRITE_SIZE * SCALE) - 600;
		controls = new ControlListener();
		getStageManager().setMouseListener(controls);
		getStageManager().setKeyListener(controls);
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
		double mountainsOffset = -xOffset * 0.3;
		g2.drawImage(mountains, (int) (mountainsOffset % 800) + 800, -190, 800, 800, null);
		g2.drawImage(mountains, (int) (mountainsOffset % 800), -190, 800, 800, null);
		AffineTransform at = new AffineTransform();
		at.translate((int) -xOffset, (int) -yOffset);
		g2.setTransform(at);
		int spriteSize = TileSet.SPRITE_SIZE * SCALE;

		int xStart = (int) (xOffset / (TileSet.SPRITE_SIZE * SCALE));
		int yStart = (int) (yOffset / (TileSet.SPRITE_SIZE * SCALE));
		int xEnd = xStart + GameCanvas.WIDTH / (TileSet.SPRITE_SIZE * SCALE) + 2;
		int yEnd = yStart + GameCanvas.HEIGHT / (TileSet.SPRITE_SIZE * SCALE) + 2;

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				BufferedImage image = tileSet.getSprite(map.getTileID(x, y));
				if (image != null) {
					g2.drawImage(image, x * spriteSize, y * spriteSize, spriteSize, spriteSize, null);
				}
			}
		}
		map.drawObjects(g2);
		g2.setTransform(new AffineTransform());
		/**
		 * GUI
		 */
	}

	public void update() {
		if (controls.Key_W) {
			yOffset -= 2;
		}
		if (controls.Key_A) {
			xOffset -= 2;
		}
		if (controls.Key_S) {
			yOffset += 2;
		}
		if (controls.Key_D) {
			xOffset += 2;
		}
	}

	public void stop() {
		updateTimer.cancel();
	}

	private class ControlListener implements MouseListener, KeyListener {

		private boolean Key_W;
		private boolean Key_A;
		private boolean Key_S;
		private boolean Key_D;

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("W");
			if (e.getKeyCode() == KeyEvent.VK_W) {
				Key_W = true;
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				Key_A = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				Key_S = true;
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				Key_D = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				Key_W = false;
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				Key_A = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				Key_S = false;
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				Key_D = false;
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

	}

}
