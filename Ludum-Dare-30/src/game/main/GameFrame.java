package game.main;

import game.res.SaveGame;

import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import util.GeneralUtils;
import util.Log;
import de.Auch.Monitoring;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 4260463266395801740L;
	public static long GAMESTARTTIME;

	private GameCanvas gameCanvas;

	public GameFrame() {
		GameCanvas.IS_APPLET = false;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Double You");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(GameFrame.class.getResource("/dy_icon.png"));
		setIconImage(img);

		setUndecorated(true);
		setVisible(true);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice display = ge.getDefaultScreenDevice();
		display.setFullScreenWindow(this);
		DisplayMode[] displayModes = display.getDisplayModes();
		DisplayMode dMode = displayModes[0];
		for (int i = 1; i < displayModes.length; i++) {
			if (displayModes[i].getWidth() * displayModes[i].getHeight() > dMode.getWidth() * dMode.getHeight()) {
				dMode = displayModes[i];
			} else if (displayModes[i].getWidth() == dMode.getWidth() && displayModes[i].getHeight() == dMode.getHeight()
					&& displayModes[i].getRefreshRate() > dMode.getRefreshRate()) {
				dMode = displayModes[i];
			}
		}
		Log.info("Choosen DisplayMode: " + dMode.getWidth() + "x" + dMode.getHeight() + " " + dMode.getRefreshRate() + "Hz " + dMode.getBitDepth() + "Bit");
		display.setDisplayMode(dMode);

		gameCanvas = new GameCanvas(dMode.getWidth(), dMode.getHeight());
		gameCanvas.init();
		add(gameCanvas);

		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				close();
				System.exit(0);
			}
		});

	}

	public void start() {
		Monitoring.init();
		Monitoring.setUpdateTime(10);
		Monitoring.addMonitoring("Draw", false);
		Monitoring.addMonitoring("Sleep", false);
		Monitoring.addMonitoring("Update", false);
		Monitoring.addInformation("FPS");

		if (GeneralUtils.isDevMode()) {
			if (System.getenv("USERNAME").trim().equals("Philipp")) {
				Monitoring.startMonitoring();
				Log.info("Monitoring is running!");
			}
		}

		gameCanvas.start();
	}

	public void close() {
		gameCanvas.close();
	}

	public static void main(String[] args) {
		GAMESTARTTIME = System.currentTimeMillis();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Log.openLog(SaveGame.getPath());
				Log.info("Starting Log!");
				GameFrame gf = new GameFrame();
				gf.setVisible(true);
				gf.start();
			}
		});
	}

}