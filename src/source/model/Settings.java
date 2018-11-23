package source.model;

public class Settings
{
   private boolean music;
   private boolean sfx;
   public enum Theme{
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

   public void setMusic(boolean music)
   {
      this.music = music;
   }

   public boolean getSfx()
   {
      return sfx;
   }

   public void setSfx(boolean sfx)
   {
      this.sfx = sfx;
   }
}
