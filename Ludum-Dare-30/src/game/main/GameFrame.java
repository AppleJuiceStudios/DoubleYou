package game.main;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import util.log.GeneralUtils;
import util.log.Log;
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

		gameCanvas = new GameCanvas();
		gameCanvas.init();
		add(gameCanvas);

		setResizable(false);
		pack();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				close();
				System.exit(0);
			}
		});
		setLocationRelativeTo(null);
	}

	public void start() {
		Monitoring.init();
		Monitoring.setUpdateTime(10);
		Monitoring.addMonitoring("Draw", false);
		Monitoring.addMonitoring("Sleep", false);
		Monitoring.addMonitoring("Update", false);
		Monitoring.addInformation("FPS");

		if (GeneralUtils.isDevMode()) {
			if (System.getenv("USERNAME") == "Philipp") {
				Monitoring.startMonitoring();
				Log.info("Monitoring is running!");
			}
		}

		gameCanvas.start();
	}

	public static void main(String[] args) {
		GAMESTARTTIME = System.currentTimeMillis();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GameFrame gf = new GameFrame();
				gf.setVisible(true);
				gf.requestFocus();
				gf.start();
			}
		});
	}

	public void close() {
		gameCanvas.close();
	}

}