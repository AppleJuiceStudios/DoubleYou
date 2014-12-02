package util.hud;

import game.main.GameCanvas;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Hud {

	private int height;

	public Hud() {
		height = 50;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(new Color(46, 49, 51, 200));
		g2.fillRect(0, GameCanvas.HEIGHT - height, GameCanvas.WIDTH, height);
	}

}