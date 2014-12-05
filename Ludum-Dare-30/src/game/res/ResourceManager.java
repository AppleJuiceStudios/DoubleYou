package game.res;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import util.log.Log;

public class ResourceManager {

	private static Map<String, BufferedImage> images;
	private static Map<String, Clip> clips;
	private static Map<String, Sequencer> midis;
	private static ResourceBundle langFile;

	public static void load() {
		long startTime = System.currentTimeMillis();
		Log.info("Loading res!");
		images = new HashMap<>();
		clips = new HashMap<>();
		midis = new HashMap<>();
		loadLang();
		Log.info("Loading lang took " + (System.currentTimeMillis() - startTime) + " ms!");
		Scanner scanner = new Scanner(ResourceManager.class.getResourceAsStream("/res.data"));
		while (scanner.hasNextLine()) {
			String path = scanner.nextLine();
			if (path.endsWith(".png")) {
				loadImage(path);
			} else if (path.endsWith(".wav")) {
				loadClip(path);
			} else if (path.endsWith(".mid")) {
				loadMidi(path);
			}
		}
		scanner.close();
		Log.info("Loading res took " + (System.currentTimeMillis() - startTime) + " ms!");
	}

	private static void loadLang() {
		Locale local = new Locale(SaveGame.saveGame.getLang());
		InputStream input = null;
		try {
			langFile = ResourceBundle.getBundle("lang.lang", local);
			Log.info("Loaded Language \"" + local.toString() + "\"");
			Log.debug("Language-Name \"" + getString("language.name") + "\"");
			Log.debug("Language-Region \"" + getString("language.region") + "\"");
		} catch (NullPointerException e) {
			Log.error("Couldn't read lang file: \"" + local.toString() + "\"!");
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void reloadLang() {
		loadLang();
	}

	private static void loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(ResourceManager.class.getResourceAsStream(path));
			images.put(path, image);
			Log.debug("Load image: " + path);
		} catch (Exception e) {
			Log.error("Can not load image: " + path);
			e.printStackTrace();
		}
	}

	private static void loadClip(String path) {
		try {
			Clip clip = AudioSystem.getClip();
			BufferedInputStream inStream = new BufferedInputStream(SoundManager.class.getResourceAsStream(path));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(inStream);
			clip.open(audioIn);
			audioIn.close();
			Log.debug("Load clip: " + path);
			clips.put(path, clip);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			Log.error("Can not load clip: " + path);
			e.printStackTrace();
		}
	}

	private static void loadMidi(String path) {
		try {
			Sequence sequence = MidiSystem.getSequence(SoundManager.class.getResourceAsStream(path));
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);

			Log.debug("Load Midi: " + path);
			midis.put(path, sequencer);
		} catch (Exception e) {
			Log.error("Can not load clip: " + path);
			e.printStackTrace();
		}
	}

	public static String getString(String key) {
		String raw = langFile.getString(key);
		String str = null;
		try {
			str = new String(raw.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (str == null) {
			Log.error("Accessed Language-String wasn't loaded: " + key);
			str = "Error!";
		}
		return str;
	}

	public static BufferedImage getImage(String path) {
		BufferedImage img = images.get(path);
		if (img == null)
			Log.error("Accessed Image wasn't loaded: " + path);
		return img;
	}

	public static Clip getClip(String path) {
		Clip clip = clips.get(path);
		if (clip == null)
			Log.error("Accessed Clip wasn't loaded: " + path);
		return clip;
	}

	public static Sequencer getMidi(String path) {
		Sequencer seq = midis.get(path);
		if (seq == null)
			Log.error("Accessed Midi wasn't loaded: " + path);
		return seq;
	}

	public static void close() {

	}

}
