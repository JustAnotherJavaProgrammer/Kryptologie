package otp_chat.client.gui;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import otp_chat.client.Client;

public class AudioPlayer {
	public static AudioPlayer getInstance() {
		return new AudioPlayer();
	}

	private Clip clip;

	public AudioPlayer() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			clip = null;
			e.printStackTrace();
		}
	}

	public void play(String filename) {
		if (clip == null)
			return;
		if (clip.isActive())
			clip.stop();
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(
					new BufferedInputStream(Client.class.getResourceAsStream("sounds/" + filename)));
			clip.open(in);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
