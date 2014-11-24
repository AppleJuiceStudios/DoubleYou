package game.res;

import game.main.GameCanvas;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaveGame {

	public static SaveGame saveGame;

	private int nextLevel;
	private static String path;

	public static SaveGame getSaveGame() {
		return saveGame;
	}

	public static void setSaveGame(SaveGame saveGame) {
		SaveGame.saveGame = saveGame;
	}

	public static void load() {
		if (!GameCanvas.IS_APPLET) {
			path = getPath() + "/SaveGame.xml";
			File file = new File(path);
			if (file.exists()) {
				saveGame = JAXB.unmarshal(file, SaveGame.class);
			} else {
				saveGame = JAXB.unmarshal(SaveGame.class.getResourceAsStream("/SaveGame.xml"), SaveGame.class);
				SaveGame.save();
			}
		}
	}

	public static void save() {
		if (!GameCanvas.IS_APPLET) {
			path = getPath() + "/SaveGame.xml";
			File file = new File(path);
			try {
				new File(file.getParent()).mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JAXB.marshal(saveGame, path);
		}
	}

	public int getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(int nextLevel) {
		this.nextLevel = nextLevel;
	}

	public static String getPath() {
		String path;
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) {
			path = System.getenv("AppData");
		} else {
			path = System.getProperty("user.home");
			path += "/Library/Application Support";
		}
		path += "/DoubleYou";
		return path;
	}
}
