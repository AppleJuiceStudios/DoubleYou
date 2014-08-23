package game.level;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityPlayer extends EntityMob {

	private boolean key_A;
	private boolean key_D;
	private boolean key_SPACE;

	public EntityPlayer(double x, double y) {
		super(x, y, 14d, 31d, null);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Player-Model.png"));
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
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			key_SPACE = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			key_A = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			key_D = false;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			key_SPACE = false;
		}
	}

}
