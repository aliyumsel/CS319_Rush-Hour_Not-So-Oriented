package source.controller;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.net.URL;

public class SoundManager extends Controller
{
   private static SoundManager instance = null;

   private AudioStream audioStream = null;
   private InputStream inputStream = null;
   private Clip clip;
   private boolean isThemeEnabled;
   private boolean isEffectsEnabled;

   private SoundManager()
   {
      //isThemeEnabled = GameEngine.getInstance().playerManager.getCurrentPlayer().getSettings().getMusic();
      //isEffectsEnabled = GameEngine.getInstance().playerManager.getCurrentPlayer().getSettings().getSfx();
   }

   public static SoundManager getInstance()
   {
      if(instance == null) {
         instance = new SoundManager();
      }
      return instance;
   }

   private void initializeClip()
   {
      try
      {
         String themeSong = ThemeManager.getInstance().getThemeSong();
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
            String selectionSound = ThemeManager.getInstance().getSelectionSound();
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(selectionSound); //buralar değişicek folderlarda ve theme classına eklenicek
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
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
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.getInstance().getButtonClickSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: button Click");
         }
      }
   }

   public void poofSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.getInstance().getPoofSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: Poof");
         }
      }
   }

   public void shrinkSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.getInstance().getShrinkSound());
            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
         {
            System.out.println("Not Found: Shrink");
         }
      }
   }

   public void successSound()
   {
      if ( isEffectsEnabled )
      {
         try
         {
            inputStream = SoundManager.class.getClassLoader().getResourceAsStream(ThemeManager.getInstance().getEndOfLevelSound()); // getSuccess sound olacak

            audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
         } catch (Exception a)
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
      isThemeEnabled = PlayerManager.getInstance().getCurrentPlayer().getSettings().getMusic();
      isEffectsEnabled = PlayerManager.getInstance().getCurrentPlayer().getSettings().getSfx();
      background();
   }
}
