package game.level.mapobject;

import game.level.entity.EntityPlayer;
import game.level.map.LevelMap;
import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectGroundswtich extends MapObject {

	private byte targetID;

	private BufferedImage[] images;
	private boolean keep;
	private boolean inverted;

	public MapObjectGroundswtich(byte id, int x, int y, byte targetID, boolean inverted, boolean keep) {
		super(id, x, y, 1, 1, false);
		this.targetID = targetID;
		this.inverted = inverted;
		this.keep = keep;
		loadTextures();
	}

	public MapObjectGroundswtich() {
		loadTextures();
	}

	protected void loadTextures() {
		BufferedImage image = ResourceManager.getImage("/level/object/Groundswitch.png");
		images = new BufferedImage[2];
		images[0] = image.getSubimage(0, 0, 16, 16);
		images[1] = image.getSubimage(0, 16, 16, 16);
	}

	public void draw(Graphics2D g2) {
		int scale = 16 * 3;
		if (power) {
			g2.drawImage(images[1], x * scale, y * scale, scale, scale, null);
		} else {
			g2.drawImage(images[0], x * scale, y * scale, scale, scale, null);
		}
	}

	public void updateTriger(EntityPlayer[] player, LevelMap map) {
		boolean t = false;
		for (int i = 0; i < player.length; i++) {
			if (x == (int) ((player[i].getXPos() + (player[i].getWidth() / 2)) / 16)
					& y == (int) ((player[i].getYPos() + player[i].getHeight() - 1) / 16)) {
				t = true;
			}
		}
		if (t) {
			if (power == false) {
				power = true;
				map.powerObject(targetID, !inverted);
			}
		} else {
			if (power & !keep) {
				power = false;
				map.powerObject(targetID, inverted);
			}
		}
	}

	public byte getTargetID() {
		return targetID;
	}

	public void setTargetID(byte targetID) {
		this.targetID = targetID;
	}

	public boolean isKeep() {
		return keep;
	}

	public void setKeep(boolean keep) {
		this.keep = keep;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

}
