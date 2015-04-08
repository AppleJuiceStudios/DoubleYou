package game.res;

import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrollingAnimation {

	private String[] textLeft;
	private String[] textRight;
	private double speed;
	private Font font;

	private int x, x2, y, width, height, space;
	private double currentY;

	private ScrollingAnimation() {
		speed = 1;
		font = new Font("Arial", Font.PLAIN, (int) (GameCanvas.HEIGHT * 0.05));
		x2 = (int) (x + GameCanvas.WIDTH * 0.5);
		space = font.getSize() + 10;
	}

	public ScrollingAnimation(String[] textLeft, String[] textRight, double speed, Rectangle rec) {
		this();
		this.textLeft = textLeft;
		this.textRight = textRight;
		this.speed = speed;

		this.x = rec.x;
		this.y = rec.y;
		this.width = rec.width;
		this.height = rec.height;

		currentY = y;
	}

	public ScrollingAnimation(String[] textLeft, String[] textRight, double speed, int x, int y, int width, int height) {
		this(textLeft, textRight, speed, new Rectangle(x, y, width, height));
	}

	public ScrollingAnimation(String filename, double speed, Rectangle rec) {
		this();
		Scanner scanner = new Scanner(ResourceManager.class.getResourceAsStream(filename));
		List<String> listLeft = new ArrayList<>();
		List<String> listRight = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int i = line.indexOf("\t");
			if (i > 0) {
				listLeft.add(line.substring(0, i));
				listRight.add(line.substring(i));
			}
		}
		scanner.close();

		textLeft = listLeft.toArray(new String[0]);
		textRight = listRight.toArray(new String[0]);

		this.speed = speed;
		this.x = rec.x;
		this.y = rec.y;
		this.width = rec.width;
		this.height = rec.height;

		currentY = GameCanvas.HEIGHT;
	}

	public ScrollingAnimation(String filename, double speed, int x, int y, int width, int height) {
		this(filename, speed, new Rectangle(x, y, width, height));
	}

	public void draw(Graphics g) {
		g.setFont(font);
		// g.setColor(Color.LIGHT_GRAY);
		// g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		int lastY = 0;
		for (int i = 0; i < textLeft.length; i++) { // All Lines
			if ((i + 1) * space + currentY > y) { // if under top line
				if ((i + 1) * space + (int) currentY - y < 100) { // Distance to top line
					g.setColor(new Color(1f, 1f, 1f, (float) (((i + 1) * space + currentY - y) / 100)));
				} else {
					g.setColor(Color.WHITE);
				}
				g.drawString(textLeft[i], x, (i + 1) * space + (int) currentY);
				g.drawString(textRight[i], x2, (i + 1) * space + (int) currentY);
				if (lastY < (i + 1) * space + (int) currentY)
					lastY = (i + 1) * space + (int) currentY;
			}
		}
		currentY -= speed / 60;
		if (lastY < 100) {
			currentY = GameCanvas.HEIGHT;
		}
	}

	// region Getters-Setters

	public String[] getText() {
		return textLeft;
	}

	public void setText(String[] text) {
		this.textLeft = text;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
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

	// endregion Getters-Setters
}
