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
	// Traffic Theme
	private String trafficThemeSong = "src/sounds/theme.wav";
	private AudioStream audioStream = null;
	private InputStream inputStream = null;
	private boolean isThemeEnabled = true;
	private boolean isEffectsEnabled = true;

	public void background() {
		if (isThemeEnabled) { //java fx kullandým, swing loop a alýrken hata veriyodu
			try {

				AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(trafficThemeSong));
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (Exception a) {
				System.out.println("Not Found");
			}
		}
	}

	public void vehicleHorn(String vehicle) { // Vehicle Controller ýn set selected Methodun da çaðýrýlýyo yani user bi
												// obje seçtiði zaman
		if (isEffectsEnabled) {
			try {
				inputStream = new FileInputStream("src/sounds/" + vehicle + ".wav"); // Sound eklerken buradaki isme
																						// dikkat edin vehicle'ýn TYPE
																						// ile ayný olmasý lazým
				audioStream = new AudioStream(inputStream);
				AudioPlayer.player.start(audioStream);
			} catch (IOException a) {
				System.out.println("Not Found");
			}
		}
	}

	public void themeSongToggle() {  //Toggle Button a eklicez settingsde
		isThemeEnabled = !isThemeEnabled;
	}

	public void effectsToggle() {
		isEffectsEnabled = !isEffectsEnabled;
	}

}
