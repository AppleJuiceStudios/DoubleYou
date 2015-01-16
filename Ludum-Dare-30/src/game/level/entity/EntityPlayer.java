package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EntityPlayer extends EntityMob {

	protected boolean key_A;
	protected boolean key_D;
	protected boolean key_W;

	public int health;

	protected Animation animationRun;
	protected Animation animationJump;

	public EntityPlayer(double x, double y) {
		super(x, y, 14d, 31d, null);
		health = 3;
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		animationRun.load("/model/player/Run-Animation.png", 2, 150);
		animationJump.load("/model/player/Jump-Animation.png", 2, 150);
		image = ResourceManager.getImage("/model/player/Player-Model.png");
	}

	public void update(LevelMap map) {
		xMovement = 0;
		if (key_A) {
			xMovement = -2;
		}
		if (key_D) {
			xMovement = 2;
		}
		if (key_W & onGround) {
			yMovement = -2.75;
		}
		super.update(map);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			key_A = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			key_D = true;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			key_W = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			key_A = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			key_D = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			key_W = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			key_A = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			key_D = false;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE | e.getKeyCode() == KeyEvent.VK_W) {
			key_W = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			key_A = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			key_D = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			key_W = false;
		}
	}

	protected BufferedImage getImage(boolean animated) {
		if (animated) {
			if (!onGround) {
				return animationJump.getImage();
			} else if (key_A || key_D) {
				return animationRun.getImage();
			}
		}
		return image;
	}

	public EntityPlayerRecord createRecord() {
		EntityPlayerRecord rec = new EntityPlayerRecord(x, y);
		rec.key_A = key_A;
		rec.key_D = key_D;
		rec.key_W = key_W;
		return rec;
	}

	public int getHealth() {
		return health;
	}

}
