package source.controller;

import source.model.GameObject;
import source.model.LevelInformation;
import source.model.Vehicle;
import source.view.GuiPanelManager;

public class GameManager extends Controller
{
   public static GameManager instance;
   public PlayerManager playerManager;
   public String theme = "classic"; //Player da kaydedileceği zaman player a atın

   public int level;

   boolean isGameActive = false;

   GameManager()
   {
      playerManager = PlayerManager.instance;
      instance = this;
   }

   public void update()
   {

   }

   void autoSave(int moveAmount)
   {
      PlayerManager.instance.updateLevelDuringGame(level, moveAmount);
   }

   void endMap()
   {
      System.out.println("Map Finished");
      isGameActive = false;
      //PlayerManager.instance.setLevelStatusFinished(level);

      if ( isNextLevelLocked() )
      {
         unlockNextLevel();
         PlayerManager.instance.incrementLastUnlockedLevelNo();
      }

      int starsCollected = calculateStars(level);
      System.out.println("Stars Collected: " + starsCollected);
      PlayerManager.instance.updateLevelAtTheEnd(level, starsCollected);
      GuiPanelManager.instance.getGamePanel().setEndOfLevelPanelVisible(starsCollected);


   }

   private int calculateStars(int _level)
   {
      LevelInformation currentLevel = PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1);
      if ( currentLevel.getCurrentNumberOfMoves() <= currentLevel.getMaxNumberOfMovesForThreeStars() )
      {
         return 3;
      }
      else if ( currentLevel.getCurrentNumberOfMoves() <= currentLevel.getMaxNumberOfMovesForTwoStars() )
      {
         return 2;
      }
      else
      {
         return 1;
      }
   }

   public void loadLastLevel()
   {
      level = PlayerManager.instance.getCurrentPlayer().getLastUnlockedLevelNo();
      loadLevel(level);
   }

   public void loadLevel(int _level)
   {
      System.out.println("Loaded level: " + _level);
      System.out.println(PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1));
      level = _level;

      if ( !PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1).getStatus().equals("inProgress") )
      {
         MapController.instance.loadOriginalLevel(_level);
         autoSave(0);
         VehicleController.instance.setMap(MapController.instance.getMap());
         VehicleController.instance.setNumberOfMoves(0);
      }
      else
      {
         MapController.instance.loadLevel(_level);
         VehicleController.instance.setMap(MapController.instance.getMap());
         VehicleController.instance.setNumberOfMoves(playerManager.getCurrentPlayer().getLevels().get(_level - 1).getCurrentNumberOfMoves());
      }

      GuiPanelManager.instance.getGamePanel().setInnerGamePanelVisible();
      

      isGameActive = true;
   }

   public void nextLevel()
   {
      level++;
      loadLevel(level);
   }

   public void resetLevel()
   {
	   MapController.instance.loadOriginalLevel(level);
      autoSave(0);
      VehicleController.instance.setMap(MapController.instance.getMap());
      VehicleController.instance.setNumberOfMoves(0);
   }

   public void changeTheme(String theme)
   {
     this.theme = theme;
     try {
        if(MapController.instance.getMap().getGameObjects() != null){
           for (GameObject gameObject: MapController.instance.getMap().getGameObjects()){
              if(!gameObject.getType().equals("OO")){
                 System.out.println("Theme");
                 ((Vehicle)gameObject).updateVehicleImages();
              }
           }
        }
     } catch (Exception e){}

   }
   public int getLevel()
   {
      return level;
   }

   private boolean isNextLevelLocked()
   {
      return level < PlayerManager.instance.getCurrentPlayer().getLevels().size() && PlayerManager.instance.isLevelLocked(level + 1);
   }

   private void unlockNextLevel()
   {
      PlayerManager.instance.unlockLevel(level + 1);
   }

}
