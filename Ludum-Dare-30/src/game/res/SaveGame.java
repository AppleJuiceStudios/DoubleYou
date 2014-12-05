package game.res;

import game.main.GameCanvas;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import util.Log;

@XmlRootElement
public class SaveGame {

	public static SaveGame saveGame;

	private int nextLevel;
	private String lang;
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
				try {
					saveGame.lang = Locale.getDefault().getDisplayName();
				} catch (Exception e) {
					e.printStackTrace();
					Log.warning("Unable to detect system lang! Using engl.");
				}
				SaveGame.save();
			}
			writeVersion();
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
			JAXB.marshal(saveGame, new File(path));
		}
	}

	public int getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(int nextLevel) {
		this.nextLevel = nextLevel;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public static String getPath() {
		String path;
		String OS;
		try {
			OS = (System.getenv("OS")).toUpperCase();
		} catch (Exception e) {
			OS = "NULL";
		}
		if (OS.contains("WIN")) {
			path = System.getenv("AppData");
			path += "/DoubleYou";
		} else {
			path = System.getenv("HOME");
			path += "/.doubleYou";
		}
		new File(path).mkdir();
		return path;
	}

	private static void writeVersion() {
		PrintWriter writer = null;
		Scanner in = null;
		try {
			in = new Scanner(SaveGame.class.getResourceAsStream("/VERSION"));
			writer = new PrintWriter(getPath() + "/VERSION");

			while (in.hasNext())
				writer.println(in.nextLine());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
			if (in != null)
				in.close();
		}

	}
}
