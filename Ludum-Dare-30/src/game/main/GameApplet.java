package game.main;

import java.applet.Applet;
import java.awt.Color;

@SuppressWarnings("serial")
public class GameApplet extends Applet {

	private GameCanvas gameCanvas;

	public void init() {
		setBackground(Color.BLACK);
		gameCanvas = new GameCanvas();
		gameCanvas.init();
		add(gameCanvas);
	}

	public void start() {
		gameCanvas.start();
	}

	public void stop() {
		gameCanvas.start();
	}

	public void destroy() {
		gameCanvas.destroy();
	}

}
