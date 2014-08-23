package game.level;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityPlayer extends EntityMob {

	private boolean key_A;
	private boolean key_D;
	private boolean key_SPACE;

	public int health;

	private Animation animation;

	public EntityPlayer(double x, double y) {
		super(x, y, 14d, 31d, null);
		health = 3;
		animation = new Animation();
		animation.load("/model/player/Run-Animation.png", 2, 150);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/model/player/Player-Model.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(LevelMap map) {
		xMovement = 0;
		if (key_A) {
			xMovement = -1;
		}
		if (key_D) {
			xMovement = 1;
		}
		if (key_SPACE & onGround) {
			yMovement = -2.5;
		}
		super.update(map);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			key_A = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			key_D = true;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE | e.getKeyCode() == KeyEvent.VK_W) {
			key_SPACE = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			key_A = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			key_D = false;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE | e.getKeyCode() == KeyEvent.VK_W) {
			key_SPACE = false;
		}
	}

	protected BufferedImage getImage() {
		if (key_A || key_D) return animation.getImage();
		else
			return image;
	}
}
