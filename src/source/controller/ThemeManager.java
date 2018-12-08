package source.controller;

import source.model.GameObject;
import source.model.Obstacle;
import source.model.Theme;
import source.model.Vehicle;

import java.awt.image.BufferedImage;

public class ThemeManager extends Controller {
    public static ThemeManager instance;
    public Theme currentTheme;
    public Theme classic;
    public Theme minimalistic;
    public Theme safari;
    public Theme space;

    public ThemeManager(){ //String theme parametresi ekleyip aşağıda hangi themese current theme o olucak oyun başlarken
        instance = this;
        minimalistic = new Theme("minimalistic");
        classic = new Theme("classic");
        safari = new Theme("safari");
        space = new Theme("space");
        currentTheme = minimalistic;
    }

    public void setTheme(String theme){
        if (theme == "classic")
            currentTheme = classic;
        else if (theme == "minimalistic")
            currentTheme = minimalistic;
        else if (theme == "safari")
            currentTheme = safari;
        else if (theme == "space")
            currentTheme = space;
        else {
            System.out.println("Theme is null");
        }
        try {
            if(MapController.instance.getMap().getGameObjects() != null){
                for (GameObject gameObject: MapController.instance.getMap().getGameObjects()){
                    gameObject.updateImages();
                }
            }
        } catch (Exception e){}
        SoundManager.instance.updateTheme();
    }

    public BufferedImage getLongVehicleImage(){
        return currentTheme.getLongVehicleImage();
    }

    public BufferedImage getShortVehicleImage(){
        return currentTheme.getShortVehicleImage();
    }

    public BufferedImage getBackgroundImage(){
        return currentTheme.getBackgroundImage();
    }

    public BufferedImage getObstacleImage(){
        return currentTheme.getObstacleImage();
    }
    public BufferedImage getPlayerImage(){
        return currentTheme.getPlayerImage();
    }
    public BufferedImage getSpecialPlayerImage(){
        return currentTheme.getSpecialPlayerImage();
    }
    public String getButtonClickSound(){
       return currentTheme.getButtonClickSound();
    }

    public String getThemeSong(){
        return currentTheme.getThemeSong();
    }
    public Theme getCurrentTheme(){
        return currentTheme;
    }
}
