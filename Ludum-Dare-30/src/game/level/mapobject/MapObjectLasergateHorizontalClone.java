package game.level.mapobject;

import game.res.ResourceManager;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectLasergateHorizontalClone extends MapObjectLasergateHorizontal {

	public MapObjectLasergateHorizontalClone(int id, int x, int y, int width, boolean closed) {
		super(id, x, y, width, closed);
	}

	public MapObjectLasergateHorizontalClone() {

	}

	protected void loadTexture() {
		BufferedImage image = ResourceManager.getImage("/level/object/Lasergate-HorizontalOnlyClone.png");
		images = new BufferedImage[6];
		images[0] = image.getSubimage(0, 0, 16, 16);
		images[1] = image.getSubimage(16, 0, 16, 16);
		images[2] = image.getSubimage(32, 0, 16, 16);
		images[3] = image.getSubimage(0, 16, 16, 16);
		images[4] = image.getSubimage(16, 16, 16, 16);
		images[5] = image.getSubimage(32, 16, 16, 16);
	}

	public boolean isCloneSolid() {
		return false;
	}

}
