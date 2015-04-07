package game.level;

import game.level.entity.Entity;
import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectActionSpawner;
import game.level.mapobject.MapObjectActionTextbox;
import game.level.mapobject.MapObjectActionWinning;
import game.level.mapobject.MapObjectGroundswtich;
import game.level.mapobject.MapObjectLasergate;
import game.level.mapobject.MapObjectLasergateClone;
import game.level.mapobject.MapObjectLasergateHorizontal;
import game.level.mapobject.MapObjectLasergateHorizontalClone;
import game.level.mapobject.MapObjectLogicAnd;
import game.level.mapobject.MapObjectLogicAndKeeping;
import game.level.mapobject.MapObjectLogicOr;
import game.level.mapobject.MapObjectTrigger;
import game.level.mapobject.MapObjectTriggerLevel12;
import game.main.GameCanvas;
import game.res.SaveGame;
import game.staging.StageLevel;
import game.staging.StageManager;

import java.awt.Graphics2D;
import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "levelMap")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LevelMap {

	private StageLevel stageLevel;
	private int levelID;
	private String soundTrack;

	private boolean isCloneAllowed;
	private Textbox startTextbox;

	private int playerSpawnX;
	private int playerSpawnY;

	protected byte[][] spritesheet;
	private int width;
	private int height;

	protected MapObject[] objects;

	public LevelMap() {
		init();
	}

	public void init() {
		objects = new MapObject[0];
	}

	public void start() {
		for (int i = 0; i < objects.length; i++) {
			objects[i].onStart(this);
		}
	}

	public void drawObjects(Graphics2D g2, int size) {
		for (int i = 0; i < objects.length; i++) {
			objects[i].draw(g2, size);
		}
	}

	public void updateTriger(Entity... entities) {
		for (int i = 0; i < objects.length; i++) {
			objects[i].updateTriger(this, entities);
		}
	}

	public void powerObject(int id, boolean power) {
		if (id >= 32)
			objects[id - 32].setPower(power, this);
	}

	public byte getTileID(int x, int y) {
		if (x >= 0 & x < width & y >= 0 & y < height) {
			return spritesheet[x][y];
		} else {
			return 5;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte[][] getSpritesheet() {
		return spritesheet;
	}

	public void setSpritesheet(byte[][] spritesheet) {
		this.spritesheet = spritesheet;
		width = spritesheet.length;
		height = spritesheet[0].length;
	}

	public boolean isBlock(int x, int y) {
		int id = getTileID(x, y);
		return id > 0 & id < 10;
	}

	public boolean isSolidTile(int x, int y) {
		int id = getTileID(x, y);
		if (id < 32) {
			return id > 0 & id < 10;
		} else {
			return objects[id - 32].isSolid();
		}
	}

	public boolean isCloneSolid(int x, int y) {
		int id = getTileID(x, y);
		if (id < 32) {
			return id > 0 & id < 10;
		} else {
			return objects[id - 32].isCloneSolid();
		}
	}

	public void save(String name) {
		JAXB.marshal(this, new File("res/level/" + name + ".xml"));
	}

	public static LevelMap loadLevel(int levelID) {
		LevelMap map = JAXB.unmarshal(LevelMap.class.getResourceAsStream("/level/level" + levelID + ".xml"), LevelMap.class);
		map.levelID = levelID;
		return map;
	}

	public static LevelMap loadLevel(File level) {
		LevelMap map = JAXB.unmarshal(level, LevelMap.class);
		map.levelID = -1;
		return map;
	}

	public MapObject getMapObject(int id) {
		return objects[id - 32];
	}

	public int getPlayerSpawnX() {
		return playerSpawnX;
	}

	public void setPlayerSpawnX(int playerSpawnX) {
		this.playerSpawnX = playerSpawnX;
	}

	public int getPlayerSpawnY() {
		return playerSpawnY;
	}

	public void setPlayerSpawnY(int playerSpawnY) {
		this.playerSpawnY = playerSpawnY;
	}

	public void setStageLevel(StageLevel stageLevel) {
		this.stageLevel = stageLevel;
	}

	public StageLevel getStageLevel() {
		return stageLevel;
	}

	public String getSoundTrack() {
		return soundTrack;
	}

	public void setSoundTrack(String soundTrack) {
		this.soundTrack = soundTrack;
	}

	public boolean getIsCloneAllowed() {
		return isCloneAllowed;
	}

	public void setIsCloneAllowed(boolean isCloneAllowed) {
		this.isCloneAllowed = isCloneAllowed;
	}

	public Textbox getStartTextbox() {
		return startTextbox;
	}

	public void setStartTextbox(Textbox startTextbox) {
		this.startTextbox = startTextbox;
	}

	@XmlElementWrapper(name = "mapObjects")
	@XmlElementRefs({ @XmlElementRef(type = MapObjectGroundswtich.class), @XmlElementRef(type = MapObjectLasergate.class),
			@XmlElementRef(type = MapObject.class), @XmlElementRef(type = MapObjectLasergateClone.class),
			@XmlElementRef(type = MapObjectLasergateHorizontal.class), @XmlElementRef(type = MapObjectLasergateHorizontalClone.class),
			@XmlElementRef(type = MapObjectLogicAndKeeping.class), @XmlElementRef(type = MapObjectLogicOr.class),
			@XmlElementRef(type = MapObjectLogicAnd.class), @XmlElementRef(type = MapObjectTriggerLevel12.class),
			@XmlElementRef(type = MapObjectTrigger.class), @XmlElementRef(type = MapObjectActionTextbox.class),
			@XmlElementRef(type = MapObjectActionWinning.class), @XmlElementRef(type = MapObjectActionSpawner.class) })
	public MapObject[] getMapObjects() {
		return objects;
	}

	public void setMapObjects(MapObject[] objects) {
		this.objects = objects;
	}

	public void hasWon() {
		if (!GameCanvas.IS_APPLET) {
			int nextLevel = SaveGame.saveGame.getNextLevel();
			if (nextLevel == levelID)
				SaveGame.saveGame.setNextLevel(nextLevel + 1);
			SaveGame.save();
		}
		stageLevel.getStageManager().setStage(StageManager.STAGE_WON);
	}

}
