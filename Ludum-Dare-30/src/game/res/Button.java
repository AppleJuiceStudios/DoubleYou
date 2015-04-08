package game.res;

import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button {

	private String text;
	private Rectangle rectangle;
	private int x;
	private int y;

	private boolean highlighted;
	private boolean highlightReplaces;
	private boolean textIsHidden;
	private BufferedImage image;
	private BufferedImage imageHighlight;
	private Color color;
	private Font font;
	private int offset;

	public Button(String text, int x, int y) {
		this(text, new Rectangle(), x, y);
		Rectangle rec = getRectangle();
		rec.width = (int) (GameCanvas.WIDTH * 0.25);
		rec.height = (int) (GameCanvas.HEIGHT * 0.066);
		setRectangle(rec);
	}

	public Button(String text, Rectangle rectangle, int x, int y) {
		this.setText(text);
		this.setRectangle(rectangle);
		this.setX(x);
		this.setY(y);
		rectangle.setLocation(x, y);

		this.setHighlighted(true);
		this.setHighlightReplaces(false);
		this.setTextIsHidden(false);
		this.setImage(ResourceManager.getImage("/buttons/button.png"));
		this.setImageHighlight(ResourceManager.getImage("/buttons/button-Highlight.png"));
		this.setColor(Color.WHITE);
		this.setFont(new Font("Impact", Font.BOLD, (int) (rectangle.height * 0.8)));
		this.setOffset(20);
	}

	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.setFont(font);

		if (isHighlighted()) {
			if (!isHighlightReplaces())
				g2.drawImage(image, x, y, (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
			g2.drawImage(imageHighlight, x, y, (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
		} else
			g2.drawImage(image, x, y, (int) rectangle.getWidth(), (int) rectangle.getHeight(), null);
		if (!isTextIsHidden())
			g2.drawString(text, x + offset, (int) (y + rectangle.height - rectangle.height * 0.2));
	}

	public boolean contains(Point p) {
		return rectangle.contains(p);
	}

	// region Getters/Setters
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
		rectangle.setLocation(x, y);
		this.setFont(new Font("Impact", Font.BOLD, (int) (rectangle.height * 0.8)));
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImageHighlight() {
		return imageHighlight;
	}

	public void setImageHighlight(BufferedImage imageHighlight) {
		this.imageHighlight = imageHighlight;
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
		rectangle.setLocation(x, y);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		rectangle.setLocation(x, y);

	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public void highlight() {
		setHighlighted(true);
	}

	public void deHighlight() {
		setHighlighted(false);
	}

	public void toogleHighlight() {
		setHighlighted(!isHighlighted());
	}

	public boolean isHighlightReplaces() {
		return highlightReplaces;
	}

	public void setHighlightReplaces(boolean highlightReplaces) {
		this.highlightReplaces = highlightReplaces;
	}

	public boolean isTextIsHidden() {
		return textIsHidden;
	}

	public void setTextIsHidden(boolean textIsHidden) {
		this.textIsHidden = textIsHidden;
	}

	// endregion Getters/Setters
}
