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

	private String[] text;
	private double speed;
	private Font font;

	private int x, y, width, height;
	private double currentY;

	private ScrollingAnimation() {
		speed = 1;
		font = new Font("Arial", Font.PLAIN, 20);
	}

	public ScrollingAnimation(String[] text, double speed, Rectangle rec) {
		this();
		this.text = text;
		this.speed = speed;

		this.x = rec.x;
		this.y = rec.y;
		this.width = rec.width;
		this.height = rec.height;

		currentY = y;
	}

	public ScrollingAnimation(String[] text, double speed, int x, int y, int width, int height) {
		this(text, speed, new Rectangle(x, y, width, height));
	}

	public ScrollingAnimation(String filename, double speed, Rectangle rec) {
		this();
		Scanner scanner = new Scanner(ResourceManager.class.getResourceAsStream(filename));
		List<String> list = new ArrayList<>();
		while (scanner.hasNextLine()) {
			list.add(scanner.nextLine());
		}

		text = list.toArray(new String[0]);

		scanner.close();

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
		g.setColor(Color.LIGHT_GRAY);
		// g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		int lastY = 0;
		for (int i = 0; i < text.length; i++) {
			if ((i + 1) * 30 + currentY > y) {
				if ((i + 1) * 30 + (int) currentY - y < 100) {
					g.setColor(new Color(1f, 1f, 1f, (float) (((i + 1) * 30 + currentY - y) / 100)));
				} else {
					g.setColor(Color.WHITE);
				}
				g.drawString(text[i], x, (i + 1) * 30 + (int) currentY);
				if (lastY < (i + 1) * 30 + (int) currentY)
					lastY = (i + 1) * 30 + (int) currentY;
			}
		}
		currentY -= speed / 60;
		if (lastY < 100) {
			currentY = GameCanvas.HEIGHT;
		}
	}

	// region Getters-Setters

	public String[] getText() {
		return text;
	}

	public void setText(String[] text) {
		this.text = text;
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
