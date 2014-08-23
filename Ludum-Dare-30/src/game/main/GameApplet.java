package game.main;

import java.applet.Applet;
import java.awt.Color;

@SuppressWarnings("serial")
public class GameApplet extends Applet {

	private GameCanvas gameCanvas;

	public void init() {
		GameCanvas.IS_APPLET = true;
		setBackground(Color.BLACK);
		gameCanvas = new GameCanvas();
		gameCanvas.init();
		add(gameCanvas);
	}

	public void start() {
		gameCanvas.start();
	}

	public void stop() {
		gameCanvas.stop();
	}

	public void destroy() {
		gameCanvas.destroy();
	}

}
