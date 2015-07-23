package game.levelEditor.tool;

import game.main.GameCanvas;
import game.res.ResourceManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ToolbarWorld extends Toolbar {

	public ToolbarWorld() {
		BufferedImage image = ResourceManager.getImage("/levelEditor/toolbarLeft.png");
		images = new BufferedImage[3];
		images[0] = image.getSubimage(0, 0, 60, 48);
		images[1] = image.getSubimage(0, 48, 60, 40);
		images[2] = image.getSubimage(0, 88, 60, 48);

		tools = new Tool[4];
		tools[0] = new Tool();
		tools[1] = new Tool();
		tools[2] = new Tool();
		tools[3] = new Tool();
	}

	public void draw(Graphics2D g2, double mouse_X, double mouse_Y) {
		if (expanded) {
			if (mouse_X > 60 || mouse_Y > (GameCanvas.HEIGHT + tools.length * 40) / 2 + 8 || mouse_Y < (GameCanvas.HEIGHT - tools.length * 40) / 2 - 8) {
				expanded = false;
			}
		} else {
			if (mouse_X <= 16 && mouse_Y <= (GameCanvas.HEIGHT + tools.length * 40) / 2 + 8 && mouse_Y >= (GameCanvas.HEIGHT - tools.length * 40) / 2 - 8) {
				expanded = true;
			}
		}
		int yStart = (GameCanvas.HEIGHT - tools.length * 40) / 2;
		int xStart = expanded ? 0 : -44;
		g2.drawImage(images[0], xStart, yStart - 8, 60, 48, null);
		for (int i = 1; i < tools.length - 1; i++) {
			g2.drawImage(images[1], xStart, yStart + i * 40, 60, 40, null);
		}
		g2.drawImage(images[2], xStart, yStart + (tools.length - 1) * 40, 60, 48, null);
	}

	public boolean containsMouse(double mouse_X, double mouse_Y) {
		return false;
	}

}
