package source.controller;

import source.model.LevelInformation;
import source.view.GuiPanelManager;

public class GameManager extends Controller
{
   public static GameManager instance;
   public PlayerManager playerManager;

   public int level;
   private int timerStartValue;
   private int remainingTime;
   private boolean isLevelBonus;

   boolean isGameActive = false;

   GameManager()
   {
      playerManager = PlayerManager.instance;
      instance = this;
      remainingTime = 0;
      isLevelBonus = false;
   }

   public void update()
   {
      if ( isLevelBonus & isGameActive)
      {
         remainingTime--;
         if (remainingTime <= 0)
         {
            timeOver();
         }
      }

      if (Input.getKeyPressed("n"))
      {
         endMap();
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
      PowerUpManager.instance.deactivatePowerUps();
   }

   void endMap()
   {

//      System.out.println("Map Finished");
      isGameActive = false;
      PowerUpManager.instance.deactivatePowerUps();
      VehicleController.instance.isExitReachable = false;
      //PlayerManager.instance.setLevelStatusFinished(level);

      if ( isNextLevelLocked() )
      {
         unlockNextLevel();
         PlayerManager.instance.incrementLastUnlockedLevelNo();
      }

      if (isLevelBonus && PlayerManager.instance.getCurrentPlayer().getLevels().get(level - 1).getStars() == 0)
      {
         PlayerManager.instance.addShrinkPowerup(2);
         PlayerManager.instance.addSpacePowerup(2);
      }

      int starsCollected = calculateStars(level);
//      System.out.println("Stars Collected: " + starsCollected);
      PlayerManager.instance.updateLevelAtTheEnd(level, starsCollected);
      GuiPanelManager.instance.getGamePanel().showEndOfLevelPopUp(starsCollected);
   }

   private void timeOver()
   {
      System.out.println("Time Over!");
      isGameActive = false;
      PowerUpManager.instance.deactivatePowerUps();
      PlayerManager.instance.updateLevelAtTheEnd(level, 0);
      GuiPanelManager.instance.getGamePanel().showTimeOverPopUp();
   }

   private int calculateStars(int _level)
   {
      LevelInformation currentLevel = PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1);
      //bad fix maybe change it later
      if ( currentLevel.getCurrentNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForThreeStars() )
      {
         return 3;
      }
      else if ( currentLevel.getCurrentNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForTwoStars() )
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
//      System.out.println("Loaded level: " + _level);
//      System.out.println(PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1));
      level = _level;
      LevelInformation levelToBeLoaded = PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1);
      isLevelBonus = false;

      if ( levelToBeLoaded.getTime() >= 0 )
      {
         System.out.println("Bonus Map");
         isLevelBonus = true;
         remainingTime = levelToBeLoaded .getTime() * 60;
         timerStartValue = remainingTime;
         MapController.instance.loadOriginalLevel(_level);
         VehicleController.instance.setMap(MapController.instance.getMap());
         VehicleController.instance.setNumberOfMoves(0);
      }
      else if ( !levelToBeLoaded.getStatus().equals("inProgress") || reset )
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


      isGameActive = true;
   }

   public void nextLevel()
   {
      level++;
      loadLevel(level, false);
   }

   public void resetLevel()
   {
      PlayerManager.instance.updateLevelAtReset(level);
      loadLevel(level, true);
   }

   public boolean isLastLevel()
   {
      return level == PlayerManager.instance.getCurrentPlayer().getLevels().size();
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

   public boolean isShrinkPowerUpUsable()
   {
      return playerManager.getCurrentPlayer().getRemainingShrinkPowerup() > 0;
   }

   public boolean isSpacePowerUpUsable()
   {
      return playerManager.getCurrentPlayer().getRemainingSpacePowerup() > 0;
   }

   public int getRemainingTime()
   {
      return remainingTime;
   }

   public int getTimerStartValue()
   {
      return timerStartValue;
   }

   public boolean isLevelBonus()
   {
      return isLevelBonus;
   }

   public boolean isGameActive()
   {
      return isGameActive;
   }

   public void toggleControlType()
   {
      PlayerManager.instance.toggleControlPreference();
      VehicleController.instance.toggleCurrentControl();
   }
}
