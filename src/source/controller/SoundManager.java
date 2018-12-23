package source.controller;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * SoundManager class is responsible for all the sounds
 * that can be heard in the game.
 */
public class SoundManager extends Controller
{
   public static SoundManager instance;

   private AudioStream audioStream = null;
   private InputStream inputStream = null;
   private Clip clip;
   private boolean isThemeEnabled;
   private boolean isEffectsEnabled;


   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   SoundManager()
   {
      //isThemeEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getMusic();
      //isEffectsEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getSfx();
      instance = this;
   }


   /**
    * It initializes the clip.
    */
   private void initializeClip()
   {
      try
      {
         String themeSong = ThemeManager.instance.getThemeSong();
         clip = AudioSystem.getClip();
         URL url = this.getClass().getResource(themeSong);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         clip.open(audioInputStream);
         clip.start();
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      } catch (Exception a)
      {
         a.printStackTrace();
      }
   }


   /**
    * Plays the background music
    */
   public void background()
   {
      if ( isThemeEnabled )
      {
         initializeClip();
      }
   }


   /**
    * Plays the vehicle horn sound effect
    */
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
         } catch (Exception a)
         {
            System.out.println("Not Found: Vehicle horn");
         }
      }
   }


   /**
    * Updates the theme for the sounds.
    */
   public void updateTheme()
   {
      if ( clip != null )
      {
         clip.close();
      }
      background();
   }


   /**
    * Plays the button click sound.
    */
   public void buttonClick()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.instance.getButtonClickSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: button Click");
         }
      }
   }


   /**
    * Plays poof sound after the poof effect.
    */
   public void poofSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.instance.getPoofSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: Poof");
         }
      }
   }


   /**
    * Plays shrink sound after the shrink effect.
    */
   public void shrinkSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.instance.getShrinkSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: Shrink");
         }
      }
   }


   /**
    * Plays the success sound.
    */
   public void successSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.instance.getEndOfLevelSound()); // getSuccess sound olacak

            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: Success Sound");
         }
      }
   }


   /**
    * Toggles the state of the theme song, on or off.
    */
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


   /**
    * Toggles the state of the sound effects, on or off.
    */
   public void effectsToggle()
   {
      isEffectsEnabled = !isEffectsEnabled;
   }


   /**
    * Checks whether theme song is enabled or not.
    * @return true if it is enabled, false otherwise.
    */
   boolean isThemeSongEnabled()
   {
      return isThemeEnabled;
   }


   /**
    * Checks whether effects is enabled or not.
    * @return true if it is enabled, false otherwise.
    */
   boolean isEffectsEnabled()
   {
      return isEffectsEnabled;
   }


   /**
    * Setter for theme song
    * @param themeEnabled Sets if the theme enabled.
    */
   void setThemeSong(boolean themeEnabled)
   {
      if ( this.isThemeEnabled != themeEnabled )
      {
         themeSongToggle();
      }

   }


   /**
    * Setter for effects.
    * @param effectsEnabled Sets if the effects enabled.
    */
   void setEffects(boolean effectsEnabled)
   {
      if ( this.isEffectsEnabled != effectsEnabled )
      {
         effectsToggle();
      }
   }


   /**
    * Starter for sound manager.
    */
   public void start()
   {
      isThemeEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getMusic();
      isEffectsEnabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getSfx();
      background();
   }
}
