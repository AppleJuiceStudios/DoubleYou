package game.level.mapobject;

import game.level.EntityPlayer;
import game.level.map.LevelMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapObjectGroundswich extends MapObject {

	private byte targetID;

	private BufferedImage[] images;
	private boolean triger;

	public MapObjectGroundswich(byte id, int x, int y, byte targetID) {
		super(id, x, y, 1, 1, true);
		this.targetID = targetID;
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/level/object/Groundswich.png"));
			images = new BufferedImage[2];
			images[0] = image.getSubimage(0, 0, 16, 16);
			images[1] = image.getSubimage(0, 16, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int scale = 16 * 3;
		if (triger) {
			g2.drawImage(images[1], x * scale, y * scale, scale, scale, null);
		} else {
			g2.drawImage(images[0], x * scale, y * scale, scale, scale, null);
		}
	}

	public void updateTriger(EntityPlayer[] player, LevelMap map) {
		for (int i = 0; i < player.length; i++) {
			if (x == (int) ((player[i].getXPos() + (player[i].getWidth() / 2)) / 16)
					& y == (int) ((player[i].getYPos() + player[i].getHeight() - 1) / 16)) {
				if (triger == false) {
					triger = true;
					map.powerObject(targetID, true ^ power);
				}
			} else {
				if (triger) {
					triger = false;
					map.powerObject(targetID, false ^ power);
				}
			}
		}
	}

}
