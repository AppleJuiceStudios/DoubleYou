package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.Entity;

import java.awt.Graphics2D;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapObject {

	protected int id;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	protected boolean power;

	public MapObject(int id, int x, int y, int width, int height, boolean power) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.power = power;
	}

	public MapObject() {

	}

	public void draw(Graphics2D g2, int size) {

	}

	public boolean isSolid() {
		return false;
	}

	public boolean isCloneSolid() {
		return isSolid();
	}

	public void updateTriger(LevelMap map, Entity... player) {

	}

	public void onStart(LevelMap map) {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public void setPower(boolean power, LevelMap map) {
		this.power = power;
	}

	public boolean getPower() {
		return power;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
