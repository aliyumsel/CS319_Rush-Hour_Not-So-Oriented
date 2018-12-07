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
   private boolean isThemeEnabled;
   private boolean isEffectsEnabled;

   public SoundManager()
   {
      //isThemeEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getMusic();
      //isEffectsEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getSfx();
      instance = this;
   }

   private void initializeClip()
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

   public void background()
   {
      if ( isThemeEnabled )
      {
         initializeClip();
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
         if (clip == null)
         {
            initializeClip();
         }
         else {
            clip.start();
         }
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

   public boolean isThemeSongEnabled()
   {
      return isThemeEnabled;
   }

   public boolean isEffectsEnabled()
   {
      return isEffectsEnabled;
   }

   public void setThemeSong(boolean themeEnabled) {
      if (this.isThemeEnabled != themeEnabled)
      {
         themeSongToggle();
      }

   }

   public void setEffects(boolean effectsEnabled) {
      if (this.isEffectsEnabled != effectsEnabled)
      {
         effectsToggle();
      }
   }

   public void start()
   {
      isThemeEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getMusic();
      isEffectsEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getSfx();
      background();
   }
}
