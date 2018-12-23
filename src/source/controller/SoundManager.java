package source.controller;




import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

@SuppressWarnings("Duplicates")
public class SoundManager extends Controller
{
   private static SoundManager instance = null;


   private Clip theme;
   private  Clip effect;
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
         theme = AudioSystem.getClip();
         URL url = this.getClass().getResource(themeSong);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         theme.open(audioInputStream);
         theme.start();
         theme.loop(Clip.LOOP_CONTINUOUSLY);
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
         playSound(ThemeManager.getInstance().getSelectionSound());
      }
   }

   public void updateTheme()
   {
      if ( theme != null )
      {
         theme.close();
      }
      background();
   }

   public void buttonClick()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getButtonClickSound());
      }
   }

   public void poofSound()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getShrinkSound());
      }
   }

   public void shrinkSound()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getShrinkSound());
      }
   }

   public void successSound()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getEndOfLevelSound());
      }
   }

   public void themeSongToggle()
   {
      isThemeEnabled = !isThemeEnabled;
      if ( isThemeEnabled )
      {
         if ( theme == null )
         {
            initializeClip();
         }
         else
         {
            theme.start();
         }
      }
      else
      {
         theme.stop();
      }
      System.out.println(isThemeEnabled);

   }

   private void playSound(String soundIn){
      try
      {
         String sound = soundIn;
         effect = AudioSystem.getClip();
         URL url = this.getClass().getResource(sound);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         effect.open(audioInputStream);
         effect.start();
      } catch (Exception a)
      {
         System.out.println("Not Found: "+soundIn+" Sound");
      }
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
