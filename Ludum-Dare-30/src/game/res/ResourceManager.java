package game.res;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import util.log.Log;

public class ResourceManager {

	private static Map<String, BufferedImage> images;
	private static Map<String, Clip> clips;

	public static void load() {
		Log.debug("Loading res!");
		images = new HashMap<>();
		clips = new HashMap<>();
		long startTime = System.currentTimeMillis();
		Scanner scanner = new Scanner(ResourceManager.class.getResourceAsStream("/res.data"));
		while (scanner.hasNextLine()) {
			String path = scanner.nextLine();
			if (path.endsWith(".png")) {
				loadImage(path);
			} else if (path.endsWith(".wav")) {
				loadClip(path);
			}
		}
		scanner.close();
		Log.debug("Loading res took " + (System.currentTimeMillis() - startTime) + " ms!");
		Log.debug("Done with ResourceManager!");
	}

	private static void loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(ResourceManager.class.getResourceAsStream(path));
			images.put(path, image);
			Log.info("Load image: " + path);
		} catch (IOException e) {
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
			Log.info("Load clip: " + path);
			clips.put(path, clip);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("Can not load clip: " + path);
			e.printStackTrace();
		}
	}

	public static BufferedImage getImage(String path) {
		return images.get(path);
	}

	public static Clip getClip(String path) {
		return clips.get(path);
	}

}
