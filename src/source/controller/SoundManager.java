package source.controller;

import source.model.Theme;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
         String trafficThemeSong = ThemeManager.instance.getThemeSong();
         AudioInputStream inputStream = AudioSystem.getAudioInputStream(SoundManager.class.getClassLoader().getResourceAsStream(trafficThemeSong));
         clip = AudioSystem.getClip();
         clip.open(inputStream);
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      } catch (Exception a)
      {
         System.out.println("Not Found: Clip");
      }
   }

   public void background()
   {
      if ( isThemeEnabled )
      {
         initializeClip();
      }
   }

   void vehicleHorn()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            String selectionSound = ThemeManager.instance.getSelectionSound();
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(selectionSound); //buralar değişicek folderlarda ve theme classına eklenicek
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (IOException a)
         {
            System.out.println("Not Found: Vehicle horn");
         }
      }
   }

   public void updateTheme()
   {
      if ( clip != null )
      {
         clip.close();
      }
      background();
   }

   public void buttonClick()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.instance.getButtonClickSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (IOException a)
         {
            System.out.println("Not Found: button Click");
         }
      }
   }

   public void successSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.instance.getSelectionSound()); // getSuccess sound olacak

            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (IOException a)
         {
            System.out.println("Not Found: Success Sound");
         }
      }
   }

   public void themeSongToggle()
   {
      isThemeEnabled = !isThemeEnabled;
      if ( isThemeEnabled )
      {
         if ( clip == null )
         {
            initializeClip();
         }
         else
         {
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

   boolean isThemeSongEnabled()
   {
      return isThemeEnabled;
   }

   boolean isEffectsEnabled()
   {
      return isEffectsEnabled;
   }

   void setThemeSong(boolean themeEnabled)
   {
      if ( this.isThemeEnabled != themeEnabled )
      {
         themeSongToggle();
      }

   }

   void setEffects(boolean effectsEnabled)
   {
      if ( this.isEffectsEnabled != effectsEnabled )
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
