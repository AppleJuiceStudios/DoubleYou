package game.res;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
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

	public static void load() {
		Log.info("Loading res!");
		images = new HashMap<>();
		clips = new HashMap<>();
		midis = new HashMap<>();
		long startTime = System.currentTimeMillis();
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
