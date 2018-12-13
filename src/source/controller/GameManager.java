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
      if ( bonus & isGameActive)
      {
         time--;
         if (time <= 0) {
            timeOver();
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
      GuiPanelManager.instance.getGamePanel().setEndOfLevelPanelVisible(starsCollected, true);
   }

   private void timeOver()
   {
      System.out.println("Time Over!");
      isGameActive = false;
      PlayerManager.instance.updateLevelAtTheEnd(level, 0);
      GuiPanelManager.instance.getGamePanel().setEndOfLevelPanelVisible(0, false);
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
      loadLevel(level, false);
   }

   public void loadLevel(int _level, boolean reset)
   {
      System.out.println("Loaded level: " + _level);
      System.out.println(PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1));
      level = _level;
      LevelInformation levelToBeLoaded = PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1);
      bonus = false;

      if ( levelToBeLoaded instanceof BonusLevelInformation )
      {
         System.out.println("Bonus Map");
         bonus = true;
         time = ( (BonusLevelInformation) levelToBeLoaded ).getTime() * 60;
         MapController.instance.loadOriginalLevel(_level);
         VehicleController.instance.setMap(MapController.instance.getMap());
         VehicleController.instance.setNumberOfMoves(0);
      }
      else if ( !levelToBeLoaded.getStatus().equals("inProgress") || reset )
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
      loadLevel(level, false);
   }

   public void resetLevel()
   {
      loadLevel(level, true);
      /*
      MapController.instance.loadOriginalLevel(level);
      VehicleController.instance.setMap(MapController.instance.getMap());
      VehicleController.instance.setNumberOfMoves(0);
      autoSave();

      isGameActive = true;
      */
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
