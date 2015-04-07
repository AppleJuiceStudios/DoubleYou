package game.level.entity;

import game.level.LevelMap;

import java.awt.image.BufferedImage;

public class EntityMob extends Entity {

	protected double xMovement;
	protected double yMovement;

	protected boolean onGround;

	public EntityMob(double x, double y, double width, double height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	public void update(LevelMap map, double timeFactor) {
		colision(map, timeFactor);
	}

	protected void colision(LevelMap map, double timeFactor) {
		yMovement += 0.07 * timeFactor;
		if (xMovement > 0) {
			lookLeft = false;
			int xright = (int) ((x + width + xMovement * timeFactor) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (colideWithBlock(map, xright, ytop) | colideWithBlock(map, xright, ycenter) | colideWithBlock(map, xright, ybottom)) {
				xMovement = (xright * 16) - (x + width);
			}
		} else if (xMovement < 0) {
			lookLeft = true;
			int xleft = (int) ((x + xMovement * timeFactor) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (colideWithBlock(map, xleft, ytop) | colideWithBlock(map, xleft, ycenter) | colideWithBlock(map, xleft, ybottom)) {
				xMovement = ((xleft + 1) * 16) - x;
			}
		}
		x += xMovement * timeFactor;
		// xMovement /= timeFactor;

		if (yMovement > 10) {
			yMovement = 10;
		}
		if (yMovement < -10) {
			yMovement = -10;
		}
		onGround = false;
		if (yMovement > 0) {
			int ybottom = (int) ((y + height + yMovement * timeFactor) / 16);
			int xright = (int) ((x + width) / 16 - 0.00001);
			int xleft = (int) (x / 16 + 0.00001);
			if (colideWithBlock(map, xleft, ybottom) | colideWithBlock(map, xright, ybottom)) {
				yMovement = (ybottom * 16) - (y + height);
				onGround = true;
			}
		} else if (yMovement < 0) {
			int ytop = (int) ((y + yMovement * timeFactor) / 16);
			int xright = (int) ((x + width) / 16 - 0.00001);
			int xleft = (int) (x / 16 + 0.00001);
			if (colideWithBlock(map, xleft, ytop) | colideWithBlock(map, xright, ytop)) {
				yMovement = ((ytop + 1) * 16) - y;
			}
		}
		y += yMovement * timeFactor;
		// yMovement /= timeFactor;
	}

	public boolean colideWithBlock(LevelMap map, int x, int y) {
		return map.isSolidTile(x, y);
	}

}
