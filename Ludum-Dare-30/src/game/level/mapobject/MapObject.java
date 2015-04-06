package game.level.mapobject;

import game.level.LevelMap;
import game.level.entity.Entity;

import java.awt.Color;
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

	// region Draw

	public void draw(Graphics2D g2, int size) {

	}

	public void drawLogic(Graphics2D g2, int size) {
		drawIO(g2, size);
	}

	protected void drawIO(Graphics2D g2, int size) {
		// Inputs
		if (inputCount() == 1 && !moreInputs()) {
			int xPos = width * size / 2 - (int) (1.5 * size / 16) + size * x;
			int yPos = size * y;
			drawInput(g2, xPos, yPos, size / 16 * 3, power);
		} else if (inputCount() > 0 || moreInputs()) {
			int inputCount = inputCount();
			if (moreInputs()) {
				inputCount++;
			}
			for (int i = 0; i < getInputs().length; i++) {
				int xPos;
				if (width == 0) {
					xPos = (int) (x * size + size / (inputCount * 2.0) * (2 * i + 1) - size / 16 * 1.5);
				} else {
					xPos = (int) (x * size + size * width / (inputCount * 2.0) * (2 * i + 1) - size / 16 * 1.5);
				}
				int yPso = y * size;
				drawInput(g2, xPos, yPso, size / 16 * 3, power);
			}
			if (moreInputs()) {
				int xPos;
				if (width == 0) {
					xPos = (int) (x * size + size / (inputCount * 2.0) * (2 * inputCount - 1) - size / 16 * 1.5);
				} else {
					xPos = (int) (x * size + size * width / (inputCount * 2.0) * (2 * inputCount - 1) - size / 16 * 1.5);
				}
				int yPos = y * size;
				g2.setColor(Color.ORANGE);
				g2.fillRect(xPos, yPos, size / 16 * 3, size / 16 * 3);
				g2.setColor(Color.BLACK);
				g2.drawLine(xPos, yPos + size * 3 / 32, xPos + size / 16 * 3 - 1, yPos + size * 3 / 32);
				g2.drawLine(xPos + size * 3 / 32, yPos, xPos + size * 3 / 32, yPos + size / 16 * 3 - 1);
				if ((size / 16 * 3) % 2 == 0) {
					g2.drawLine(xPos, yPos + size * 3 / 32 - 1, xPos + size / 16 * 3 - 1, yPos + size * 3 / 32 - 1);
					g2.drawLine(xPos + size * 3 / 32 - 1, yPos, xPos + size * 3 / 32 - 1, yPos + size / 16 * 3 - 1);
				}
			}
		}
		// Output
		if (hasOutput()) {
			int xPos;
			int yPos;
			if (width == 0 || height == 0) {
				xPos = (int) (6.5 * size / 16) + size * x;
				yPos = size * 13 / 16 + size * y;
			} else {
				xPos = width * size / 2 - (int) (1.5 * size / 16) + size * x;
				yPos = (y + height) * size - size * 3 / 16;
			}
			drawOutput(g2, xPos, yPos, size / 16 * 3, isOutputInverted());
		}
	}

	protected void drawInput(Graphics2D g2, int x, int y, int size, boolean power) {
		if (power) {
			g2.setColor(Color.GREEN);
		} else {
			g2.setColor(Color.RED);
		}
		g2.fillRect(x, y, size, size);
		g2.setColor(Color.BLACK);
		g2.drawRect(x, y, size - 1, size - 1);
	}

	protected void drawOutput(Graphics2D g2, int x, int y, int size, boolean inverted) {
		if (power ^ inverted) {
			g2.setColor(Color.GREEN);
		} else {
			g2.setColor(Color.RED);
		}
		if (inverted) {
			g2.fillOval(x, y, size - 1, size - 1);
			g2.setColor(Color.BLACK);
			g2.drawOval(x, y, size - 1, size - 1);
		} else {
			g2.fillRect(x, y, size, size);
			g2.setColor(Color.BLACK);
			g2.drawRect(x, y, size - 1, size - 1);
		}
	}

	// endregion Draw

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

	public int inputCount() {
		return 0;
	}

	public boolean hasOutput() {
		return false;
	}

	public int[] getInputs() {
		return null;
	}

	public int getOutput() {
		return -1;
	}

	public boolean isOutputInverted() {
		return false;
	}

	public void invertOutput() {

	}

	public boolean moreInputs() {
		return false;
	}

	public void setInput(int index, int id) {

	}

	public void setOutput(int id) {

	}

	public void onEditorRightClick() {
		setPower(!getPower());
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
