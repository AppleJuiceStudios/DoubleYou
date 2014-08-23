package game.staging;

import game.main.GameCanvas;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class StageManager {

	private Stage stage;

	private KeyListener keyListener;
	private MouseListener mouseListener;

	public static final int STAGE_LEVEL = 1;
	public static final int STAGE_MAIN_MENUE = 2;
	public static final int STAGE_OPTIONS = 3;
	public static final int STAGE_CREDITS = 4;

	public StageManager(GameCanvas gameCanvas) {
		initListener(gameCanvas);
		stage = new StageMainMenue(this, null);
	}

	public void setStage(int stageID) {
		setStage(stageID, null);
	}

	public void setStage(int stageID, Map<String, String> data) {
		Stage oldStage = stage;
		keyListener = null;
		mouseListener = null;
		if (stageID == STAGE_LEVEL) {
			stage = new StageLevel(this, data);
		} else if (stageID == STAGE_MAIN_MENUE) {
			stage = new StageMainMenue(this, data);
		} else if (stageID == STAGE_OPTIONS) {
			stage = new StageOptions(this, data);
		} else if (stageID == STAGE_CREDITS) {
			stage = new StageCredits(this, data);
		}
		oldStage.stop();
	}

	public void draw(Graphics2D g2) {
		stage.draw(g2);
	}

	public void update() {
		stage.update();
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	private void initListener(GameCanvas gameCanvas) {
		gameCanvas.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				if (keyListener != null) {
					keyListener.keyTyped(e);
				}
			}

			public void keyReleased(KeyEvent e) {
				if (keyListener != null) {
					keyListener.keyReleased(e);
				}
			}

			public void keyPressed(KeyEvent e) {
				if (keyListener != null) {
					keyListener.keyPressed(e);
				}
			}
		});
		gameCanvas.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (mouseListener != null) {
					mouseListener.mouseReleased(e);
				}
			}

			public void mousePressed(MouseEvent e) {
				if (mouseListener != null) {
					mouseListener.mousePressed(e);
				}
			}

			public void mouseExited(MouseEvent e) {
				if (mouseListener != null) {
					mouseListener.mouseExited(e);
				}
			}

			public void mouseEntered(MouseEvent e) {
				if (mouseListener != null) {
					mouseListener.mouseEntered(e);
				}
			}

			public void mouseClicked(MouseEvent e) {
				if (mouseListener != null) {
					mouseListener.mouseClicked(e);
				}
			}
		});
	}

}
