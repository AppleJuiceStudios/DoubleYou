package game.main;

import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private GameCanvas gameCanvas;

	public GameFrame() {
		GameCanvas.IS_APPLET = false;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Game");
		gameCanvas = new GameCanvas();
		gameCanvas.init();
		add(gameCanvas);
		setResizable(false);
		pack();
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

}
