package game.level.mapobject;

import game.level.LevelMap;
import game.level.TileSet;
import game.level.entity.EntityAirFlow;
import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectDecorationAirFlow extends MapObjectDecoration {

	protected BufferedImage imageHorizontal;

	protected double forceX;
	protected double forceY;

	public MapObjectDecorationAirFlow(int id, int x, int y, int width, int height, double forceX, double forceY) {
		super(id, x, y, 1, 2);
		this.forceX = forceX;
		this.forceY = forceY;
	}

	public MapObjectDecorationAirFlow() {

	}

	public void onStart(LevelMap map) {
		map.spawnEntity(new EntityAirFlow(x * TileSet.SPRITE_SIZE, y * TileSet.SPRITE_SIZE, width * TileSet.SPRITE_SIZE, height * TileSet.SPRITE_SIZE, forceX,
				forceY));
	}

	protected void loadTexture() {
		image = ResourceManager.getImage("/level/object/AirFlow.png");
		imageHorizontal = ResourceManager.getImage("/level/object/AirFlowHorizontal.png");
	}

	public void draw(Graphics2D g2, int size) {
		if (forceX == 0) {
			if (forceY < 0) {
				g2.drawImage(image, x * size, (y + height - 1) * size, size, size, null);
			} else {
				g2.drawImage(image, x * size, (y + 1) * size, size, -size, null);
			}
		} else {
			if (forceX > 0) {
				g2.drawImage(imageHorizontal, x * size, y * size, size, size, null);
			} else {
				g2.drawImage(imageHorizontal, (x + width) * size, y * size, -size, size, null);
			}
		}
	}

	public double getForceX() {
		return forceX;
	}

	public void setForceX(double froceX) {
		this.forceX = froceX;
	}

	public double getForceY() {
		return forceY;
	}

	public void setForceY(double forceY) {
		this.forceY = forceY;
	}

}
