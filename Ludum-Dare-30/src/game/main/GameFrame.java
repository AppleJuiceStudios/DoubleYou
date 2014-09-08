package game.main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private GameCanvas gameCanvas;

	public GameFrame() {
		GameCanvas.IS_APPLET = false;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Double You");
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
