package source.model;

import java.util.HashMap;

public class Settings {
   private boolean music;
   private boolean sfx;
   String controlPrefrence;

   private HashMap<String, Boolean> themes;
   private String activeTheme;

   public Settings(boolean music, boolean sfx, HashMap<String, Boolean> themes, String activeTheme, String controlPrefrence) {
      this.music = music;
      this.sfx = sfx;
      this.themes = themes;
      this.activeTheme = activeTheme;
      this.controlPrefrence = controlPrefrence;
   }

   public Settings(boolean music, boolean sfx) {
      this.music = music;
      this.sfx = sfx;
      themes = new HashMap<String, Boolean>();

      themes.put("minimalistic", true);
      themes.put("classic", false);
      themes.put("safari", false);
      themes.put("space", false);

      activeTheme = "minimalistic";
      controlPrefrence = "Slide";
   }

   public HashMap<String,Boolean> getThemes() {
      return themes;
   }

   public void setThemes(HashMap<String,Boolean> themes) {
      this.themes = themes;
   }

   public String getActiveTheme() {
      return activeTheme;
   }

   public void setActiveTheme(String activeTheme) {
      this.activeTheme = activeTheme;
   }

   public boolean getMusic() {
      return music;
   }

   public void setMusic(boolean music) {
      this.music = music;
   }

   public boolean getSfx() {
      return sfx;
   }

   public void setSfx(boolean sfx) {
      this.sfx = sfx;
   }

   public void toggleMusic() {
      music = !music;
   }

   public void toggleSfx() {
      sfx = !sfx;
   }

   public void toggleControlPreference()
   {
      if (controlPrefrence.equals("Slide"))
      {
         controlPrefrence = "Keyboard";
      }
      else
      {
         controlPrefrence = "Slide";
      }
   }

   public String getControlPrefrence()
   {
      return controlPrefrence;
   }

   public String settingsToString() {
      return "\t<Music>\n" +
              "\t\t" + music + "\n" +
              "\t<Music/>\n" +
              "\t<Sfx>\n" +
              "\t\t" + sfx + "\n" +
              "\t<Sfx/>\n" +
              "\t<ControlPreference>\n" +
              "\t\t" + controlPrefrence + "\n" +
              "\t<ControlPreference/>\n" +
              "\t<Theme>\n" +
              "\t\t<Active>\n" +
              "\t\t\t" + activeTheme + "\n" +
              "\t\t<Active>\n" +
              "\t\t<MinimalisticUnlocked>\n" +
              "\t\t\t" + themes.get("minimalistic") + "\n" +
              "\t\t<MinimalisticUnlocked/>\n" +
              "\t\t<ClassicUnlocked>\n" +
              "\t\t\t" + themes.get("classic") + "\n" +
              "\t\t<ClassicUnlocked/>\n" +
              "\t\t<SafariUnlocked>\n" +
              "\t\t\t" + themes.get("safari") + "\n" +
              "\t\t<SafariUnlocked/>\n" +
              "\t\t<SpaceUnlocked>\n" +
              "\t\t\t" + themes.get("space") + "\n" +
              "\t\t<SpaceUnlocked/>\n" +
              "\t<Theme/>\n";

   }
}
