package source.model;

import java.util.HashMap;

public class Settings {
    private boolean music;
    private boolean sfx;


    private HashMap<String, Boolean> themes;
    private String activeTheme;

    public Settings(boolean music, boolean sfx, HashMap themes, String activeTheme) {
        this.music = music;
        this.sfx = sfx;
        this.themes = themes;
        this.activeTheme = activeTheme;
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
    }

    public HashMap getThemes() {
        return themes;
    }

    public void setThemes(HashMap themes) {
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

    public String settingsToString() {
        return "\t\t<Music>\n" +
                "\t\t\t" + music + "\n" +
                "\t\t<Music/>\n" +
                "\t\t<Sfx>\n" +
                "\t\t\t" + sfx + "\n" +
                "\t\t<Sfx/>\n" +
                "\t\t<Theme>\n" +
                "\t\t\t<Active>\n" +
                "\t\t\t\t" + activeTheme + "\n" +
                "\t\t\t<Active>\n" +
                "\t\t\t<MinimalisticUnlocked>\n" +
                "\t\t\t\t" + themes.get("minimalistic") + "\n" +
                "\t\t\t<MinimalisticUnlocked/>\n" +
                "\t\t\t<ClassicUnlocked>\n" +
                "\t\t\t\t" + themes.get("classic") + "\n" +
                "\t\t\t<ClassicUnlocked/>\n" +
                "\t\t\t<SafariUnlocked>\n" +
                "\t\t\t\t" + themes.get("safari") + "\n" +
                "\t\t\t<SafariUnlocked/>\n" +
                "\t\t\t<SpaceUnlocked>\n" +
                "\t\t\t\t" + themes.get("space") + "\n" +
                "\t\t\t<SpaceUnlocked/>\n" +
                "\t\t<Theme/>\n";

    }
}
