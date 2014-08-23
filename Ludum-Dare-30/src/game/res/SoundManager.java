package game.res;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	private Map<String, Clip> clips;

	/**
	 * Add new Sound here!
	 */
	public final static SoundFile TEST = new SoundFile("Test", "/sounds/Chiptune.wav");

	public SoundManager() {
		clips = new HashMap<String, Clip>();
	}

	public void play(SoundFile sound) {
		System.out.println("[SoundManager] Playing SoundFile: " + sound.name);
		if (!clips.containsKey(sound.name)) load(sound);
		stop(sound);
		clips.get(sound.name).setFramePosition(0);
		clips.get(sound.name).start();
	}

	public void loop(SoundFile sound) {
		System.out.println("[SoundManager] Playing SoundFile: " + sound.name);
		if (!clips.containsKey(sound.name)) load(sound);
		stop(sound);
		clips.get(sound.name).setFramePosition(0);
		clips.get(sound.name).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void load(SoundFile sound) {
		System.out.println("[SoundManager] Loading SoundFile: " + sound.name + " from " + sound.path);
		clips.put(sound.name, loadClip(sound.path));
	}

	private Clip loadClip(String path) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream input = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(path)));
			clip.open(input);
			input.close();
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		return clip;
	}

	public void stop(SoundFile sound) {
		clips.get(sound.name).stop();
	}

	public void close() {
		for (String key : clips.keySet()) {
			clips.get(key).close();
		}
	}
}
