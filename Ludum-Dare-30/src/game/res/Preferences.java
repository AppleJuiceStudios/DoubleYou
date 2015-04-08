package game.res;

import game.main.GameFrame;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import util.Log;

public class Preferences {

	private static Properties props;
	private static Map<String, Integer> keyBinding;

	private static int nextLevel;
	private static String lang;
	private static String path;

	private Preferences() {
	}

	public static void load() {
		Log.info("Loading Properties");

		getDocumentPath();
		path = getPath() + "/DoubleYou.properties";
		props = new Properties();
		keyBinding = new HashMap<String, Integer>();
		File file = new File(path);
		try {
			file.createNewFile();
			props.load(new FileReader(file));
			if (props.isEmpty() || props.size() == 0)
				createAllValues();
			else {
				// Writing Values from props
				lang = props.getProperty("lang");

				Set<Object> keys = props.keySet();
				for (Object key : keys) {
					String str = key + "";
					if (str.startsWith("key_"))
						keyBinding.put(str, Integer.parseInt(props.getProperty(str)));
				}

			}
		} catch (IOException e1) {
			Log.error(e1.getMessage());
			e1.printStackTrace();
			createAllValues();
		}

		// Read nextLevel from Registry
		java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("de/applejuicestudios/doubleyou");
		nextLevel = prefs.getInt("nextlevel", 1);
		Log.debug("Loaded nextLevel from Registry");

		save();
		writeVersion();
	}

	public static void save() {
		Log.info("Saving Properties");

		// Values
		if (props == null)
			props = new Properties();

		props.setProperty("lang", lang);

		path = getPath() + "/DoubleYou.properties";
		File file = new File(path);
		try {
			new File(file.getParent()).mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			props.store(new FileWriter(file), "DoubleYou | " + GameFrame.GAME_URL);
		} catch (IOException e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}

		// Save nextLevel to Registry
		java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("de/applejuicestudios/doubleyou");
		prefs.putInt("nextlevel", nextLevel);
		Log.debug("Saved nextLevel to Registry");
	}

	private static void createAllValues() {
		Log.warning("Unable to detect preferences! Using defaults.");

		// Manuall Values
		lang = Locale.getDefault().toString();
	}

	// region Getter, Setter and Util

	public static int getNextLevel() {
		return nextLevel;
	}

	public static void setNextLevel(int nextLevel) {
		Preferences.nextLevel = nextLevel;
	}

	public static String getLang() {
		return lang;
	}

	public static void setLang(String lang) {
		Preferences.lang = lang;
	}

	public static String getPath() {
		String path;
		if (isWindows()) {
			path = System.getenv("AppData");
			path += "/DoubleYou";
		} else {
			path = System.getenv("HOME");
			path += "/.doubleYou";
		}
		new File(path).mkdir();
		return path;
	}

	public static String getDocumentPath() {
		String path;
		if (isWindows()) {
			path = System.getenv("userprofile");
			path += "/Saved Games/DoubleYou/CustomLevels";
		} else {
			path = System.getenv("HOME");
			path += "/.doubleYou/CustomLevels";
		}
		new File(path).mkdirs();
		return path;
	}

	private static boolean isWindows() {
		String OS;
		try {
			OS = (System.getenv("OS")).toUpperCase();
		} catch (Exception e) {
			OS = "NULL";
		}
		if (OS.contains("WIN")) {
			return true;
		} else {
			return false;
		}
	}

	private static void writeVersion() {
		PrintWriter writer = null;
		Scanner in = null;
		try {
			in = new Scanner(Preferences.class.getResourceAsStream("/VERSION"));
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

	// endregion Getter, Setter and Util

}