package source.controller;

import source.model.BonusLevelInformation;
import source.model.LevelInformation;
import source.view.GuiPanelManager;

public class GameManager extends Controller
{
   public static GameManager instance;
   public PlayerManager playerManager;

   public int level;
   public int time;
   private boolean bonus;

   boolean isGameActive = false;

   GameManager()
   {
      playerManager = PlayerManager.instance;
      instance = this;
      time = 0;
      bonus = false;
   }

   public void update()
   {
      if ( bonus )
      {
         time--;
         if ( time == 0 )
         {
            endMap();
         }
      }
   }

   void autoSave()
   {
      int moveAmount = VehicleController.instance.getNumberOfMoves();
      PlayerManager.instance.updateLevelDuringGame(level, moveAmount);
   }

   public void stopMap()
   {
      isGameActive = false;
   }

   void endMap()
   {

      System.out.println("Map Finished");
      isGameActive = false;
      VehicleController.instance.isExitReachable = false;
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
      LevelInformation levelToBeLoaded = PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1);

      if ( levelToBeLoaded instanceof BonusLevelInformation )
      {
         bonus = true;
         time = ( (BonusLevelInformation) levelToBeLoaded ).getTime() * 60;
         MapController.instance.loadOriginalLevel(_level);
         VehicleController.instance.setMap(MapController.instance.getMap());
         VehicleController.instance.setNumberOfMoves(0);
      }
      else if ( !levelToBeLoaded.getStatus().equals("inProgress") )
      {
         MapController.instance.loadOriginalLevel(_level);
         VehicleController.instance.setMap(MapController.instance.getMap());
         VehicleController.instance.setNumberOfMoves(0);
         autoSave();
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
      VehicleController.instance.setMap(MapController.instance.getMap());
      VehicleController.instance.setNumberOfMoves(0);
      autoSave();

      isGameActive = true;
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
