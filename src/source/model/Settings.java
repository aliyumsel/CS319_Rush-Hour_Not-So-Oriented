package source.model;

public class Settings
{
   private boolean music;
   private boolean sfx;

   public enum Theme
   {
      SIMPLE, CLASSIC, SAFARI, SPACE
   }

   private Theme theme;

   public Settings(boolean music, boolean sfx, Theme theme)
   {
      this.music = music;
      this.sfx = sfx;
      this.theme = theme;
   }

   public Settings(boolean music, boolean sfx)
   {
      this.music = music;
      this.sfx = sfx;
      this.theme = Theme.CLASSIC;
   }

   public Theme getTheme()
   {
      return theme;
   }

   public void setTheme(Theme theme)
   {
      this.theme = theme;
   }

   public boolean getMusic()
   {
      return music;
   }

//   public void setMusic(boolean music)
//   {
//      this.music = music;
//   }

   public boolean getSfx()
   {
      return sfx;
   }

//   public void setSfx(boolean sfx)
//   {
//      this.sfx = sfx;
//   }

   public void toggleMusic()
   {
      music = !music;
   }

   public void toggleSfx()
   {
      sfx = !sfx;
   }

   public String settingsToString()
   {
      String themeStr;
      if ( theme == Theme.SIMPLE )
      {
         themeStr = "SIMPLE";
      }
      else if ( theme == Theme.SAFARI )
      {
         themeStr = "SAFARI";
      }
      else if ( theme == Theme.SPACE )
      {
         themeStr = "SPACE";
      }
      else
      {
         themeStr = "CLASSIC";
      }
      return "\t\t<Music>\n" +
              "\t\t\t" + music + "\n" +
              "\t\t<Music/>\n" +
              "\t\t<Sfx>\n" +
              "\t\t\t" + sfx + "\n" +
              "\t\t<Sfx/>\n" +
              "\t\t<Theme>\n" +
              "\t\t\t" + themeStr + "\n" +
              "\t\t<Theme/>\n";

   }
}
