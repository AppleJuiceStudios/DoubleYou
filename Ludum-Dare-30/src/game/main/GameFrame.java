package game.main;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.net.URL;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 4260463266395801740L;

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
				stop();
				System.exit(0);
			}
		});
		setLocationRelativeTo(null);
	}

	public void start() {
		gameCanvas.start();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GameFrame gf = new GameFrame();
				gf.setVisible(true);
				gf.start();
			}
		});
	}

	public void stop() {
		gameCanvas.stop();
	}

}