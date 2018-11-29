package source.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager extends Controller
{
   public static SoundManager instance;

   private AudioStream audioStream = null;
   private InputStream inputStream = null;
   private Clip clip;
   private boolean isThemeEnabled = true;
   private boolean isEffectsEnabled = true;

   public SoundManager()
   {
      instance = this;
      background();
   }

   public void background()
   {
      if ( isThemeEnabled )
      {
         try
         {
            String trafficThemeSong = "src/sounds/theme.wav";
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(trafficThemeSong));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
         } catch (Exception a)
         {
            System.out.println("Not Found");
         }
      }
   }

   void vehicleHorn(String vehicle)
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = new FileInputStream("src/sounds/" + vehicle + ".wav");
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (IOException a)
         {
            System.out.println("Not Found");
         }
      }
   }

   public void buttonClick()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = new FileInputStream("src/sounds/buttonClick.wav");
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (IOException a)
         {
            System.out.println("Not Found");
         }
      }
   }

   public void successSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = new FileInputStream("src/sounds/success.wav");

            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (IOException a)
         {
            System.out.println("Not Found");
         }
      }
   }

   public void themeSongToggle()
   {
      isThemeEnabled = !isThemeEnabled;
      if ( isThemeEnabled )
      {
         clip.start();
      }
      else
      {
         clip.stop();
      }
      System.out.println(isThemeEnabled);

   }

   public void effectsToggle()
   {
      isEffectsEnabled = !isEffectsEnabled;
   }

}
