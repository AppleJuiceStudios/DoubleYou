package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.ResourceManager;

import java.awt.image.BufferedImage;

public class EntityPlayerClone extends EntityPlayer {

	private static BufferedImage imageIdel;
	private static BufferedImage imageRun;
	private static BufferedImage imageJump;

	private Byte[] recording;
	private int frame = 0;
	private boolean isDead = false;

	public EntityPlayerClone(double x, double y, Byte[] recording) {
		super(x, y);
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		if (imageIdel == null) {
			imageIdel = ResourceManager.getImage("/model/player/Player-Model.png");
			imageRun = ResourceManager.getImage("/model/player/Run-Animation.png");
			imageJump = ResourceManager.getImage("/model/player/Jump-Animation.png");
		}
		image = imageIdel;
		animationRun.load(imageRun, 2, 150);
		animationJump.load(imageJump, 2, 150);
		this.recording = recording;
	}

	public void update(LevelMap map) {
		if (!isDead) {
			byte movement = recording[frame];
			key_W = movement > 2;
			key_A = (movement % 3) == 2;
			key_D = (movement % 3) == 1;
			super.update(map);
			frame++;
			if (frame >= recording.length) {
				isDead = true;
			}
		}
	}

	public boolean isDead() {
		return isDead;
	}

	protected void colision(LevelMap map) {
		yMovement += 0.07;
		if (xMovement > 0) {
			lookLeft = false;
			int xright = (int) ((x + width + xMovement) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (map.isCloneSolid(xright, ytop) | map.isCloneSolid(xright, ycenter) | map.isCloneSolid(xright, ybottom)) {
				xMovement = (xright * 16) - (x + width);
			}
		} else if (xMovement < 0) {
			lookLeft = true;
			int xleft = (int) ((x + xMovement) / 16);
			int ytop = (int) (y / 16 + 0.00001);
			int ycenter = (int) ((y + height / 2) / 16);
			int ybottom = (int) ((y + height) / 16 - 0.00001);
			if (map.isCloneSolid(xleft, ytop) | map.isCloneSolid(xleft, ycenter) | map.isCloneSolid(xleft, ybottom)) {
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
			if (map.isCloneSolid(xleft, ybottom) | map.isCloneSolid(xright, ybottom)) {
				yMovement = (ybottom * 16) - (y + height);
				onGround = true;
			}
		} else if (yMovement < 0) {
			int ytop = (int) ((y + yMovement) / 16);
			int xright = (int) ((x + width) / 16 - 0.00001);
			int xleft = (int) (x / 16 + 0.00001);
			if (map.isCloneSolid(xleft, ytop) | map.isCloneSolid(xright, ytop)) {
				yMovement = ((ytop + 1) * 16) - y;
			}
		}
		y += yMovement;
	}

}
