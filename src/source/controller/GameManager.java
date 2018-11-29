package source.controller;

import source.model.LevelInformation;
import source.view.GuiPanelManager;

public class GameManager extends Controller
{
   public static GameManager instance;
   public PlayerManager playerManager;

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
      PlayerManager.instance.setLevelStatusFinished(level);

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
      loadLevel(level, false);
   }

   public void loadLevel(int _level, boolean original)
   {
      System.out.println("Loaded level: " + _level);
      System.out.println(PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1));

      if ( original )
      {
         MapController.instance.loadOriginalLevel(_level);
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
      level = _level;

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
      autoSave(0);
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
