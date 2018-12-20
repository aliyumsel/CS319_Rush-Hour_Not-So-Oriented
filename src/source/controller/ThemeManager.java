package source.controller;

import source.model.GameObject;
import source.model.Theme;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ThemeManager extends Controller
{
   public static ThemeManager instance;
   public Theme currentTheme;
   public Theme classic;
   public Theme minimalistic;
   public Theme safari;
   public Theme space;

   public String[] getThemes() {
      return themes;
   }

   private String themes[] = {"minimalistic", "classic" , "safari", "space"};

   public ThemeManager()
   { //String theme parametresi ekleyip aşağıda hangi themese current theme o olucak oyun başlarken
      instance = this;
      minimalistic = new Theme("minimalistic");
      classic = new Theme("classic");
      safari = new Theme("safari");
      space = new Theme("space");
   }

   private Theme findThemeByName(String theme)
   {
      if ( theme.equals("classic") )
      {
         return classic;
      }
      else if ( theme.equals("minimalistic") )
      {
         return minimalistic;
      }
      else if ( theme.equals("safari") )
      {
         return safari;
      }
      else if ( theme.equals("space") )
      {
         return space;
      }
      else
      {
         System.out.println("Theme is null");
         return null;
      }
   }

   public BufferedImage getLongVehicleImage()
   {
      return currentTheme.getLongVehicleImage();
   }

   public BufferedImage getShortVehicleImage()
   {
      return currentTheme.getShortVehicleImage();
   }

   public BufferedImage getBackgroundImage()
   {
      return currentTheme.getBackgroundImage();
   }

   public BufferedImage getGamePanelBackgroundImage()
   {
      return currentTheme.getGamePanelBackgroundImage();
   }

   public BufferedImage getPopupBackgroundImage()
   {
      return currentTheme.getPopupBackgroundImage();
   }

   public BufferedImage getObstacleImage()
   {
      return currentTheme.getObstacleImage();
   }

   public BufferedImage getDisabledImage(String type)
   {
      if (type.equals("obstacle")){
       return currentTheme.getObstacleDisabledImage();}
      else if (type.equals("long")){
         return currentTheme.getLongDisabledImage();
      }
      else if (type.equals("short")){
         return currentTheme.getShortDisabledImage();
      }
      else {
         System.out.println("Disabled image not found");
         return null;
      }
   }

   public BufferedImage getPlayerImage()
   {
      return currentTheme.getPlayerImage();
   }

   public BufferedImage getSpecialPlayerImage()
   {
      return currentTheme.getSpecialPlayerImage();
   }

   String getButtonClickSound()
   {
      return currentTheme.getButtonClickSound();
   }

   String getThemeSong()
   {
      return currentTheme.getThemeSong();
   }

   String getSelectionSound()
   {
      return currentTheme.getSelectionSound();
   }

//   public Theme getCurrentTheme()
//   {
//      return currentTheme;
//   }

   public int findRequiredStars()
   {
      int requiredStars = 100;
      if ( !classic.isUnlocked() )
      {
         requiredStars -= 25;
      }
      if ( !safari.isUnlocked() )
      {
         requiredStars -= 25;
      }
      if ( !space.isUnlocked() )
      {
         requiredStars -= 25;
      }
      if ( requiredStars == 100 )
      {
         return 0;
      }
      return requiredStars;
   }

   //Should be used in Settings Panel for the icons od theme buttons
   //returns 0 if the theme is locked and not unlockable
   //returns 1 if the theme is locked but unlockable
   //returns 2 if the the theme is already unlocked
   public int getThemeStatus(String themeName)
   {
      Theme theme = findThemeByName(themeName);

      if ( theme.isUnlocked() )
      {
         return 2;
      }
      else
      {
         if ( GameEngine.instance.playerManager.getCurrentPlayer().getStarAmount() >= findRequiredStars() )
         {
            return 1;
         }
         else
         {
            return 0;
         }
      }
   }

   public void start(){
      update();
   }

   public void changeTheme(String themeName)
   {
      GameEngine.instance.playerManager.changeTheme(themeName);
      setTheme(themeName);
   }

   //This will be called from gameManager
   void setTheme(String theme)
   {
      currentTheme = findThemeByName(theme);
      try
      {
         //Map controller updateMapImages diye bi method olmasi lazim
         // BU methoduda gamemanegerda cagirilcak
         if ( MapController.instance.getMap() != null ) //settings panelin previousunu da check edebiliriz
         {
            if ( MapController.instance.getMap().getGameObjects() != null )
            {
               for ( GameObject gameObject : MapController.instance.getMap().getGameObjects() )
               {
                  gameObject.updateImages();
               }
            }
         }
      } catch (Exception e)
      {
         //e.printStackTrace(); bunun commentini açmayın exception alması doğal halledicem burayı
      }
      SoundManager.instance.updateTheme();
//      GuiPanelManager.instance.updateImages();
   }

   public void unlockTheme(String themeName)
   {
      Theme theme = findThemeByName(themeName);

      theme.setUnlocked(true);
      GameEngine.instance.playerManager.unlockTheme(themeName);
      setTheme(themeName);
   }

   public void update()
   {
      HashMap themes = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getThemes();
      minimalistic.setUnlocked((boolean) themes.get("minimalistic"));
      classic.setUnlocked((boolean) themes.get("classic"));
      safari.setUnlocked((boolean) themes.get("safari"));
      space.setUnlocked((boolean) themes.get("space"));

      currentTheme = findThemeByName(GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getActiveTheme());
      findRequiredStars();
   }
}
