package game.main;

import game.res.SoundManager;

import java.applet.AppletContext;
import java.awt.Color;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class GameApplet extends JApplet {

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
		SoundManager.stopAll();
	}

	public void destroy() {
		gameCanvas.destroy();
	}
}
