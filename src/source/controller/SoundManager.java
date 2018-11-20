package source.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager
{
	public static SoundManager instance;

	// Traffic Theme
	private String trafficThemeSong = "src/sounds/theme.wav";
	private AudioStream audioStream = null;
	private InputStream inputStream = null;
	private boolean isThemeEnabled = true;
	private boolean isEffectsEnabled = true;

	public SoundManager()
	{
		instance = this;
		background();
	}

	public void background() {
		if (isThemeEnabled) { //java fx kulland�m, swing loop a al�rken hata veriyodu
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

	public void vehicleHorn(String vehicle) { // Vehicle Controller �n set selected Methodun da �a��r�l�yo yani user bi
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

	public void themeSongToggle() {  //Toggle Button a eklicez settingsde
		isThemeEnabled = !isThemeEnabled;
	}

	public void effectsToggle() {
		isEffectsEnabled = !isEffectsEnabled;
	}

}
