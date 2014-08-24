package game.level;

import java.awt.image.BufferedImage;

public class EntityMob extends Entity {

	protected double xMovement;
	protected double yMovement;

	protected boolean onGround;

	public EntityMob(double x, double y, double width, double height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	public void update(LevelMap map) {
		colision(map);
	}

	protected void colision(LevelMap map) {
		yMovement += 0.07;
		if (xMovement > 0) {
			lookLeft = false;
			int xright = (int) ((x + width + xMovement) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (map.isSolidTile(xright, ytop) | map.isSolidTile(xright, ycenter) | map.isSolidTile(xright, ybottom)) {
				xMovement = (xright * 16) - (x + width);
			}
		} else if (xMovement < 0) {
			lookLeft = true;
			int xleft = (int) ((x + xMovement) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (map.isSolidTile(xleft, ytop) | map.isSolidTile(xleft, ycenter) | map.isSolidTile(xleft, ybottom)) {
				xMovement = ((xleft + 1) * 16) - x;
			}
		}
		x += xMovement;

		if (yMovement > 10) {
			yMovement = 10;
		}
		if (yMovement < -10) {
			yMovement = -10;
		}
		onGround = false;
		if (yMovement > 0) {
			int ybottom = (int) ((y + height + yMovement) / 16);
			int xright = (int) ((x + width) / 16 - 0.00001);
			int xleft = (int) (x / 16 + 0.00001);
			if (map.isSolidTile(xleft, ybottom) | map.isSolidTile(xright, ybottom)) {
				yMovement = (ybottom * 16) - (y + height);
				onGround = true;
			}
		} else if (yMovement < 0) {
			int ytop = (int) ((y + yMovement) / 16);
			int xright = (int) ((x + width) / 16 - 0.00001);
			int xleft = (int) (x / 16 + 0.00001);
			if (map.isSolidTile(xleft, ytop) | map.isSolidTile(xright, ytop)) {
				yMovement = ((ytop + 1) * 16) - y;
			}
		}
		y += yMovement;
	}

}
