package game.level.mapobject;

import javax.xml.bind.annotation.XmlRootElement;

import game.level.Textbox;
import game.level.entity.EntityPlayer;
import game.level.map.LevelMap;

@XmlRootElement
public class MapObjectTriggerTextbox extends MapObjectTrigger {

	private Textbox textbox;

	public MapObjectTriggerTextbox(byte id, int x, int y, int width, int height, Textbox textbox) {
		super(id, x, y, width, height);
		this.textbox = textbox;
	}

	public MapObjectTriggerTextbox() {

	}

	@Override
	protected void action(EntityPlayer player, LevelMap map) {
		map.getStageLevel().textbox = textbox;
	}

	public Textbox getTextbox() {
		return textbox;
	}

	public void setTextbox(Textbox textbox) {
		this.textbox = textbox;
	}

}