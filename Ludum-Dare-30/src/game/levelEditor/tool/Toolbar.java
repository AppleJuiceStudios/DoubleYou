package game.levelEditor.tool;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Toolbar {

	protected BufferedImage images[];

	protected boolean expanded;

	protected Tool[] tools;

	public abstract void draw(Graphics2D g2, double mouse_X, double mouse_Y);

	public abstract boolean containsMouse(double mouse_X, double mouse_Y);

}
