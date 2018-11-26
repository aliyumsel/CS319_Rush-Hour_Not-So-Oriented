package source.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
	public static SoundManager instance;

	// Traffic Theme
	private String trafficThemeSong = "src/sounds/theme.wav";
	private String buttonClick = "src/sounds/buttonClick.wav";
	private String success = "src/sounds/success.wav";
	private AudioStream audioStream = null;
	private InputStream inputStream = null;
	private Clip clip;
	public boolean isThemeEnabled = true;
	public boolean isEffectsEnabled = true;

	public SoundManager() {
		instance = this;
		background();
	}

	public void background() {
		if (isThemeEnabled) { // java fx kulland�m, swing loop a al�rken hata veriyodu
			try {

				AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(trafficThemeSong));
				clip = AudioSystem.getClip();
				clip.open(inputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (Exception a) {
				System.out.println("Not Found");
			}
		}
	}

	public void vehicleHorn(String vehicle) { // Vehicle Controller �n set selected Methodun da �a��r�l�yo
												// yani user bi
												// obje se�ti�i zaman
		if (isEffectsEnabled) {
			try {
				inputStream = new FileInputStream("src/sounds/" + vehicle + ".wav"); // Sound eklerken buradaki isme
																						// dikkat edin vehicle'�n TYPE
																						// ile ayn� olmas� laz�m
				audioStream = new AudioStream(inputStream);
				AudioPlayer.player.start(audioStream);
			} catch (IOException a) {
				System.out.println("Not Found");
			}
		}
	}

	public void buttonClick() { // Vehicle Controller �n set selected Methodun da �a��r�l�yo yani
								// user bi
		// obje se�ti�i zaman
		if (isEffectsEnabled) {
			try {
				inputStream = new FileInputStream("src/sounds/buttonClick.wav"); // Sound eklerken buradaki isme
				// dikkat edin vehicle'�n TYPE
				// ile ayn� olmas� laz�m
				audioStream = new AudioStream(inputStream);
				AudioPlayer.player.start(audioStream);
			} catch (IOException a) {
				System.out.println("Not Found");
			}
		}
	}

	public void successSound() { // Vehicle Controller �n set selected Methodun da �a��r�l�yo yani

		if (isEffectsEnabled) {
			try {
				inputStream = new FileInputStream("src/sounds/success.wav"); // Sound eklerken buradaki isme

				audioStream = new AudioStream(inputStream);
				AudioPlayer.player.start(audioStream);
			} catch (IOException a) {
				System.out.println("Not Found");
			}
		}
	}

	public void themeSongToggle() { // Toggle Button a eklicez settingsde
		isThemeEnabled = !isThemeEnabled;
		if(isThemeEnabled)
			clip.start();
		else
			clip.stop();
		System.out.println(isThemeEnabled);
		
	}

	public void effectsToggle() {
		isEffectsEnabled = !isEffectsEnabled;
	}

}
