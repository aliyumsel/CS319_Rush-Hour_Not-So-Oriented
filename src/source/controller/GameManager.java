package source.controller;

import source.model.LevelInformation;
import source.view.GuiPanelManager;


/**
 * GameManager is a generic class that handles higher aspects of the game.
 */
public class GameManager extends Controller
{
   public static GameManager instance;
   public PlayerManager playerManager;

   public int level;
   private int timerStartValue;
   private int remainingTime;
   private boolean isLevelBonus;

   boolean isGameActive = false;


   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   GameManager()
   {
      playerManager = PlayerManager.instance;
      instance = this;
      remainingTime = 0;
      isLevelBonus = false;
   }


   /**
    * Updates the game.
    */
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


   /**
    * Auto saves the game.
    */
   void autoSave()
   {
      int moveAmount = VehicleController.instance.getNumberOfMoves();
      PlayerManager.instance.updateLevelDuringGame(level, moveAmount);
   }


   /**
    * It stops the map.
    */
   public void stopMap()
   {
      isGameActive = false;
      PowerUpManager.instance.deactivatePowerUps();
   }


   /**
    * This is called when the level is finished
    * and handles all the action of level being completed.
    */
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


   /**
    * It indicates time over after time lapses.
    */
   private void timeOver()
   {
      System.out.println("Time Over!");
      isGameActive = false;
      PowerUpManager.instance.deactivatePowerUps();
      PlayerManager.instance.updateLevelAtTheEnd(level, 0);
      GuiPanelManager.instance.getGamePanel().showTimeOverPopUp();
   }


   /**
    * It calculates the stars with considering the moves of the player.
    * @param _level the level that is played.
    * @return an integer between 1-3.
    */
   private int calculateStars(int _level)
   {
      LevelInformation currentLevel = PlayerManager.instance.getCurrentPlayer().getLevels().get(_level - 1);
      //bad fix maybe change it later
      if ( VehicleController.instance.getNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForThreeStars() )
      {
         return 3;
      }
      else if ( VehicleController.instance.getNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForTwoStars() )
      {
         return 2;
      }
      else
      {
         return 1;
      }
   }


   /**
    * Loads the last level that is played but not finished.
    */
   public void loadLastLevel()
   {
      level = PlayerManager.instance.getCurrentPlayer().getLastUnlockedLevelNo();
      loadLevel(level, false);
   }


   /**
    * Loads the given level.
    * @param _level desired level.
    * @param reset a boolean that indicates the reset
    */
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


   /**
    *  Loads the next level.
    */
   public void nextLevel()
   {
      level++;
      loadLevel(level, false);
   }


   /**
    * Resets level.
    */
   public void resetLevel()
   {
      PlayerManager.instance.updateLevelAtReset(level);
      loadLevel(level, true);
      PowerUpManager.instance.deactivatePowerUps();
   }

   /**
    * Checks whether it is last level or not
    * @return true for last level.
    */
   public boolean isLastLevel()
   {
      return level == PlayerManager.instance.getCurrentPlayer().getLevels().size();
   }


   /**
    * Getter for level
    * @return the level
    */
   public int getLevel()
   {
      return level;
   }


   /**
    * Checks whether the next level is locked or not.
    * @return true if it is locked, false otherwise.
    */
   private boolean isNextLevelLocked()
   {
      return level < PlayerManager.instance.getCurrentPlayer().getLevels().size() && PlayerManager.instance.isLevelLocked(level + 1);
   }


   /**
    * Unlocks the next level.
    */
   private void unlockNextLevel()
   {
      PlayerManager.instance.unlockLevel(level + 1);
   }


   /**
    * Checks whether shrink power up is usable or not.
    * @return true if it is usable, false otherwise.
    */
   public boolean isShrinkPowerUpUsable()
   {
      return playerManager.getCurrentPlayer().getRemainingShrinkPowerup() > 0;
   }


   /**
    * Checks whether space power up is usable or not.
    * @return true if it is usable, false otherwise.
    */
   public boolean isSpacePowerUpUsable()
   {
      return playerManager.getCurrentPlayer().getRemainingSpacePowerup() > 0;
   }


   /**
    * Getter for remaining time.
    * @return the reaining time.
    */
   public int getRemainingTime()
   {
      return remainingTime;
   }


   /**
    * Getter for the start value of timer.
    * @return timer's start value.
    */
   public int getTimerStartValue()
   {
      return timerStartValue;
   }


   /**
    * Checks if there is a level bonus or not.
    * @return the boolean isLevelBonus, true if it exists, false otherwise.
    */
   public boolean isLevelBonus()
   {
      return isLevelBonus;
   }


   /**
    * Checks if the game is active or not.
    * @return true if it is active, false otherwise.
    */
   public boolean isGameActive()
   {
      return isGameActive;
   }


   /**
    * Toggles control type.
    */
   public void toggleControlType()
   {
      PlayerManager.instance.toggleControlPreference();
      VehicleController.instance.toggleCurrentControl();
   }
}
