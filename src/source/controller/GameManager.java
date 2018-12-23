package source.controller;

import source.model.LevelInformation;
import source.view.GuiPanelManager;

public class GameManager extends Controller
{
   private static GameManager instance = null;

   public PlayerManager playerManager;
   public int level;
   private int timerStartValue;
   private int remainingTime;
   private boolean isLevelBonus;

   boolean isGameActive = false;

   private GameManager()
   {
      playerManager = PlayerManager.getInstance();
      instance = this;
      remainingTime = 0;
      isLevelBonus = false;
   }

   public static GameManager getInstance()
   {
      if(instance == null) {
         instance = new GameManager();
      }
      return instance;
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
      int moveAmount = VehicleController.getInstance().getNumberOfMoves();
      PlayerManager.getInstance().updateLevelDuringGame(level, moveAmount);
   }

   public void stopMap()
   {
      isGameActive = false;
      PowerUpManager.getInstance().deactivatePowerUps();
   }

   void endMap()
   {

//      System.out.println("Map Finished");
      isGameActive = false;
      PowerUpManager.getInstance().deactivatePowerUps();
      VehicleController.getInstance().isExitReachable = false;
      //PlayerManager.getInstance().setLevelStatusFinished(level);

      if ( isNextLevelLocked() )
      {
         unlockNextLevel();
         PlayerManager.getInstance().incrementLastUnlockedLevelNo();
      }

      if (isLevelBonus && PlayerManager.getInstance().getCurrentPlayer().getLevels().get(level - 1).getStars() == 0)
      {
         PlayerManager.getInstance().addShrinkPowerup(2);
         PlayerManager.getInstance().addSpacePowerup(2);
      }

      int starsCollected = calculateStars(level);
//      System.out.println("Stars Collected: " + starsCollected);
      PlayerManager.getInstance().updateLevelAtTheEnd(level, starsCollected);
      GuiPanelManager.getInstance().getGamePanel().showEndOfLevelPopUp(starsCollected);
   }

   private void timeOver()
   {
      System.out.println("Time Over!");
      isGameActive = false;
      PowerUpManager.getInstance().deactivatePowerUps();
      PlayerManager.getInstance().updateLevelAtTheEnd(level, 0);
      GuiPanelManager.getInstance().getGamePanel().showTimeOverPopUp();
   }

   private int calculateStars(int _level)
   {
      LevelInformation currentLevel = PlayerManager.getInstance().getCurrentPlayer().getLevels().get(_level - 1);
      //bad fix maybe change it later
      if ( VehicleController.getInstance().getNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForThreeStars() )
      {
         return 3;
      }
      else if ( VehicleController.getInstance().getNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForTwoStars() )
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
      level = PlayerManager.getInstance().getCurrentPlayer().getLastUnlockedLevelNo();
      loadLevel(level, false);
   }

   public void loadLevel(int _level, boolean reset)
   {
//      System.out.println("Loaded level: " + _level);
//      System.out.println(PlayerManager.getInstance().getCurrentPlayer().getLevels().get(_level - 1));
      level = _level;
      LevelInformation levelToBeLoaded = PlayerManager.getInstance().getCurrentPlayer().getLevels().get(_level - 1);
      isLevelBonus = false;

      if ( levelToBeLoaded.getTime() >= 0 )
      {
         System.out.println("Bonus Map");
         isLevelBonus = true;
         remainingTime = levelToBeLoaded .getTime() * 60;
         timerStartValue = remainingTime;
         MapController.getInstance().loadOriginalLevel(_level);
         VehicleController.getInstance().setMap(MapController.getInstance().getMap());
         VehicleController.getInstance().setNumberOfMoves(0);
      }
      else if ( !levelToBeLoaded.getStatus().equals("inProgress") || reset )
      {
         MapController.getInstance().loadOriginalLevel(_level);
         VehicleController.getInstance().setMap(MapController.getInstance().getMap());
         VehicleController.getInstance().setNumberOfMoves(0);
      }
      else
      {
         MapController.getInstance().loadLevel(_level);
         VehicleController.getInstance().setMap(MapController.getInstance().getMap());
         VehicleController.getInstance().setNumberOfMoves(playerManager.getCurrentPlayer().getLevels().get(_level - 1).getCurrentNumberOfMoves());
      }

      GuiPanelManager.getInstance().getGamePanel().setInnerGamePanelVisible();


      isGameActive = true;
   }

   public void nextLevel()
   {
      level++;
      loadLevel(level, false);
   }

   public void resetLevel()
   {
      PlayerManager.getInstance().updateLevelAtReset(level);
      loadLevel(level, true);
      PowerUpManager.getInstance().deactivatePowerUps();
   }

   public boolean isLastLevel()
   {
      return level == PlayerManager.getInstance().getCurrentPlayer().getLevels().size();
   }

   public int getLevel()
   {
      return level;
   }

   private boolean isNextLevelLocked()
   {
      return level < PlayerManager.getInstance().getCurrentPlayer().getLevels().size() && PlayerManager.getInstance().isLevelLocked(level + 1);
   }

   private void unlockNextLevel()
   {
      PlayerManager.getInstance().unlockLevel(level + 1);
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
      PlayerManager.getInstance().toggleControlPreference();
      VehicleController.getInstance().toggleCurrentControl();
   }
}
