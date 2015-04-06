package game.level.mapobject;

import game.level.LevelMap;
import game.level.Textbox;
import game.res.ResourceManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectActionTextbox extends MapObjectAction {

	private Textbox textbox;

	public MapObjectActionTextbox(int id, int x, int y, boolean repeat, Textbox textbox) {
		super(id, x, y, repeat);
		this.textbox = textbox;
	}

	public MapObjectActionTextbox() {

	}

	protected void loadTexture() {
		texture = ResourceManager.getImage("/buttons/ActionTextbox.png");
	}

	public void action(LevelMap map) {
		map.getStageLevel().textbox = textbox;
	}

	public Textbox getTextbox() {
		return textbox;
	}

	public void setTextbox(Textbox textbox) {
		this.textbox = textbox;
	}

}