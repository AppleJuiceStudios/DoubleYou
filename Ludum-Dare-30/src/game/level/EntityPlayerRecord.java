package game.level;

import game.level.map.LevelMap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class EntityPlayerRecord extends EntityPlayer {

	private static BufferedImage imageIdel;
	private static BufferedImage imageRun;
	private static BufferedImage imageJump;

	private List<Byte> recording;

	public EntityPlayerRecord(double x, double y) {
		super(x, y);
		if (imageIdel == null) {
			try {
				imageIdel = ImageIO.read(getClass().getResourceAsStream("/model/clone/Player-Model.png"));
				imageRun = ImageIO.read(getClass().getResourceAsStream("/model/clone/Run-Animation.png"));
				imageJump = ImageIO.read(getClass().getResourceAsStream("/model/clone/Jump-Animation.png"));
			} catch (IOException e) {

			}
		}
		image = imageIdel;
		animationRun = new Animation();
		animationJump = new Animation();
		animationRun.load(imageRun, 2, 150);
		animationJump.load(imageJump, 2, 150);
		recording = new ArrayList<Byte>();
	}

	public void update(LevelMap map) {
		byte movement = 0;
		if (key_D) {
			movement = 1;
		} else if (key_A) {
			movement = 2;
		}
		if (key_W) {
			movement += 3;
		}
		recording.add(movement);
		super.update(map);
	}

	public Byte[] getRecording() {
		return recording.toArray(new Byte[recording.size()]);
	}

	protected void colision(LevelMap map) {
		yMovement += 0.07;
		if (xMovement > 0) {
			lookLeft = false;
			int xright = (int) ((x + width + xMovement) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (map.isBlock(xright, ytop) | map.isBlock(xright, ycenter) | map.isBlock(xright, ybottom)) {
				xMovement = (xright * 16) - (x + width);
			}
		} else if (xMovement < 0) {
			lookLeft = true;
			int xleft = (int) ((x + xMovement) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (map.isBlock(xleft, ytop) | map.isBlock(xleft, ycenter) | map.isBlock(xleft, ybottom)) {
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
