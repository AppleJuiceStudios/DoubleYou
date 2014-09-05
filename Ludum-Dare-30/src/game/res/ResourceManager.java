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

public class ResourceManager {

	private static Map<String, BufferedImage> images;
	private static Map<String, Clip> clips;

	public static void load() {
		images = new HashMap<>();
		clips = new HashMap<>();
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
	}

	private static void loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(ResourceManager.class.getResourceAsStream(path));
			images.put(path, image);
			System.out.println("Load image: " + path);
		} catch (IOException e) {
			System.out.println("Can not load image: " + path);
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
			System.out.println("Load clip: " + path);
			clips.put(path, clip);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can not load clip: " + path);
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
