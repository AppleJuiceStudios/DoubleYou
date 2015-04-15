package game.level.entity;

import game.level.LevelMap;

import java.awt.image.BufferedImage;

public class EntityMob extends Entity {

	protected double xMovement;
	protected double yMovement;

	protected double xForce;
	protected double yForce;

	protected boolean onGround;

	public EntityMob(double x, double y, double width, double height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	public void update(LevelMap map, double timeFactor) {
		colision(map, timeFactor);
	}

	protected void colision(LevelMap map, double timeFactor) {
		if (yForce <= -0.07) {
			yForce += 0.07;
		} else {
			yMovement += 0.07;
		}
		xMovement += xForce;
		yMovement += yForce;

		if (xMovement > 0) {
			lookLeft = false;
			int xright = (int) ((x + width + xMovement * timeFactor) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (colideWithBlock(map, xright, ytop) | colideWithBlock(map, xright, ycenter) | colideWithBlock(map, xright, ybottom)) {
				double newxMovement = (xright * 16) - (x + width);
				if (newxMovement < xMovement - xForce) {
					xMovement = newxMovement - xForce;
					xForce = 0;
				} else {
					xForce = (xMovement - xForce) - newxMovement;
				}
				xMovement = newxMovement;
			}
		} else if (xMovement < 0) {
			lookLeft = true;
			int xleft = (int) ((x + xMovement * timeFactor) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (colideWithBlock(map, xleft, ytop) | colideWithBlock(map, xleft, ycenter) | colideWithBlock(map, xleft, ybottom)) {
				double newxMovement = ((xleft + 1) * 16) - x;
				if (newxMovement < xMovement - xForce) {
					xMovement = newxMovement - xForce;
					xForce = 0;
				} else {
					xForce = (xMovement - xForce) - newxMovement;
				}
				xMovement = newxMovement;
			}
		}
		x += xMovement * timeFactor;

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
				double newyMovement = (ybottom * 16) - (y + height);
				if (newyMovement < yMovement - yForce) {
					yMovement = newyMovement - yForce;
					yForce = 0;
				} else {
					yForce = (yMovement - yForce) - newyMovement;
				}
				yMovement = newyMovement;
				onGround = true;
			}
		} else if (yMovement < 0) {
			int ytop = (int) ((y + yMovement * timeFactor) / 16);
			int xright = (int) ((x + width) / 16 - 0.00001);
			int xleft = (int) (x / 16 + 0.00001);
			if (colideWithBlock(map, xleft, ytop) | colideWithBlock(map, xright, ytop)) {
				double newyMovement = ((ytop + 1) * 16) - y;
				if (newyMovement < yMovement - yForce) {
					yMovement = newyMovement - yForce;
					yForce = 0;
				} else {
					yForce = (yMovement - yForce) - newyMovement;
				}
				yMovement = newyMovement;
			}
		}
		y += yMovement * timeFactor;

		xMovement -= xForce;
		yMovement -= yForce;
		xForce = 0;
		yForce = 0;
	}

	public boolean colideWithBlock(LevelMap map, int x, int y) {
		return map.isSolidTile(x, y);
	}

	public void pushX(double amount) {
		xForce += amount;
	}

	public void pushY(double amount) {
		yForce += amount;
	}

}
