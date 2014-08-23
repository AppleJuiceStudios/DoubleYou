package game.main;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Color;

@SuppressWarnings("serial")
public class GameApplet extends Applet {

	private GameCanvas gameCanvas;
	public static AppletContext appletContext;

	public void init() {
		appletContext = getAppletContext();
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
