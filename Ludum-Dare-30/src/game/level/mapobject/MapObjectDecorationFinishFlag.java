package game.level.mapobject;

import game.res.ResourceManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObjectDecorationFinishFlag extends MapObjectDecoration {

	protected void loadTexture() {
		image = ResourceManager.getImage("/model/finishFlag.png");
	}

}
