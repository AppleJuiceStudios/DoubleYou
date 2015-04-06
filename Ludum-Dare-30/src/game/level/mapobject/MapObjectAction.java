package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.LevelMap;

@XmlRootElement
public abstract class MapObjectAction extends MapObject {

	private boolean wasPowered;
	private boolean repeat;

	public MapObjectAction(int id, int x, int y, boolean repeat) {
		super(id, x, y, 0, 0, false);
		this.repeat = repeat;
	}

	public MapObjectAction() {

	}

	public void onStart(LevelMap map) {
		updateAction(map);
	}

	public void setPower(boolean power, LevelMap map) {
		this.power = power;
		updateAction(map);
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

}
