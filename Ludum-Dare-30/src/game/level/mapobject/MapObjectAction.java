package game.level.mapobject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;
import game.res.ResourceManager;

@XmlRootElement
public abstract class MapObjectAction extends MapObject {

	private boolean wasPowered;
	private boolean repeat;

	protected BufferedImage texture;
	protected BufferedImage textureRepeat;

	public MapObjectAction(int id, int x, int y, boolean repeat) {
		super(id, x, y, 1, 1, false);
		this.repeat = repeat;
		loadTexture();
		textureRepeat = ResourceManager.getImage("/buttons/ActionRepeat.png");
	}

	public MapObjectAction() {
		loadTexture();
		textureRepeat = ResourceManager.getImage("/buttons/ActionRepeat.png");
	}

	protected void loadTexture() {

	}

	public void onStart(LevelMap map) {
		updateAction(map);
	}

	public void drawLogic(Graphics2D g2, int size) {
		g2.drawImage(texture, x * size, y * size, size, size, null);
		if (repeat) {
			g2.drawImage(textureRepeat, x * size, y * size, size, size, null);
		}
		super.drawLogic(g2, size);
	}

	public void setPower(boolean power, LevelMap map) {
		this.power = power;
		updateAction(map);
	}

	public void onEditorRightClick() {
		repeat = !repeat;
	}

	public void updateAction(LevelMap map) {
		if (power && !wasPowered) {
			action(map);
			wasPowered = true;
		}
		if (!power && wasPowered && repeat) {
			actionDeactivate(map);
			wasPowered = false;
		}
	}

	public abstract void action(LevelMap map);

	public void actionDeactivate(LevelMap map) {

	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public int inputCount() {
		return 1;
	}

}
