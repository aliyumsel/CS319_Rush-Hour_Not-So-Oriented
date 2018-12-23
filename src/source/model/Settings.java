package source.model;

import java.util.HashMap;

public class Settings
{
   /**
    * Settings class is used to hold settings information of a player
    */

   private boolean music; //  To determine whether the music is on or off.
   private boolean sfx; // To determine whether the sound effects are on or off.
   private String controlPreference; // Represents the selected theme by the player.

   private HashMap<String, Boolean> themes; // Represents the collection off the themes.
   private String activeTheme; // Represents the active theme.

//   public Settings(boolean music, boolean sfx, HashMap<String, Boolean> themes, String activeTheme, String controlPreference) {
//      this.music = music;
//      this.sfx = sfx;
//      this.themes = themes;
//      this.activeTheme = activeTheme;
//      this.controlPreference = controlPreference;
//   }

   /**
    * Constructor that initializes values to their specified initial values.
    * @param music To determine whether the music is on or off.
    * @param sfx To determine whether the sound effects are on or off.
    */
   public Settings(boolean music, boolean sfx) {
      this.music = music;
      this.sfx = sfx;
      themes = new HashMap<>();

      themes.put("minimalistic", true);
      themes.put("classic", false);
      themes.put("safari", false);
      themes.put("space", false);

      activeTheme = "minimalistic";
      controlPreference = "Slide";
   }

   /**
    * Getter for the collection of themes
    * @return the collection of themes
    */
   public HashMap<String,Boolean> getThemes() {
      return themes;
   }

//   public void setThemes(HashMap<String,Boolean> themes) {
//      this.themes = themes;
//   }

   /**
    * Getter for an active theme
    * @return the active theme of the game.
    */
   public String getActiveTheme() {
      return activeTheme;
   }

   /**
    * Setter fot the active theme of the game.
    * @param activeTheme the active theme of the game.
    */
   public void setActiveTheme(String activeTheme) {
      this.activeTheme = activeTheme;
   }

   /**
    * Getter for the music of the game
    * @return the desired music of the game.
    */
   public boolean getMusic() {
      return music;
   }

//   public void setMusic(boolean music) {
//      this.music = music;
//   }

   /**
    * Getter for the sfx of the game.
    * @return the sfx of the game
    */
   public boolean getSfx() {
      return sfx;
   }

//   public void setSfx(boolean sfx) {
//      this.sfx = sfx;
//   }

   /**
    * It toggles the state of the music.
    */
   public void toggleMusic() {
      music = !music;
   }

   /**
    * Toggles the state of the sfx.
    */
   public void toggleSfx() {
      sfx = !sfx;
   }

   /**
    * Toggles the control preferences.
    */
   public void toggleControlPreference()
   {
      if (controlPreference.equals("Slide"))
      {
         controlPreference = "Keyboard";
      }
      else
      {
         controlPreference = "Slide";
      }
   }

   /**
    * Getter for a control preference.
    * @return the control preference.
    */
   public String getControlPreference()
   {
      return controlPreference;
   }

//   public String settingsToString() {
//      return "\t<Music>\n" +
//              "\t\t" + music + "\n" +
//              "\t<Music/>\n" +
//              "\t<Sfx>\n" +
//              "\t\t" + sfx + "\n" +
//              "\t<Sfx/>\n" +
//              "\t<ControlPreference>\n" +
//              "\t\t" + controlPreference + "\n" +
//              "\t<ControlPreference/>\n" +
//              "\t<Theme>\n" +
//              "\t\t<Active>\n" +
//              "\t\t\t" + activeTheme + "\n" +
//              "\t\t<Active>\n" +
//              "\t\t<MinimalisticUnlocked>\n" +
//              "\t\t\t" + themes.get("minimalistic") + "\n" +
//              "\t\t<MinimalisticUnlocked/>\n" +
//              "\t\t<ClassicUnlocked>\n" +
//              "\t\t\t" + themes.get("classic") + "\n" +
//              "\t\t<ClassicUnlocked/>\n" +
//              "\t\t<SafariUnlocked>\n" +
//              "\t\t\t" + themes.get("safari") + "\n" +
//              "\t\t<SafariUnlocked/>\n" +
//              "\t\t<SpaceUnlocked>\n" +
//              "\t\t\t" + themes.get("space") + "\n" +
//              "\t\t<SpaceUnlocked/>\n" +
//              "\t<Theme/>\n";
//
//   }

}
