package game.main;

import game.res.SoundManager;

import java.applet.AppletContext;
import java.awt.Color;

import javax.swing.JApplet;

public class GameApplet extends JApplet {
	private static final long serialVersionUID = 922363733377328646L;

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
		gameCanvas.close();
		SoundManager.stopAll();
	}

	public void destroy() {
		gameCanvas.destroy();
	}
}
