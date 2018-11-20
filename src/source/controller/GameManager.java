package source.controller;

import interfaces.Updatable;
import source.view.GuiPanelManager;

public class GameManager implements Updatable
{
   static GameManager instance;

   public int level = 1;

   public boolean isGameActive = false;

   GameManager()
   {
      instance = this;
   }

   public void Update()
   {
      if (!isGameActive && Input.getKeyPressed("n"))
      {
         nextLevel();
      }
   }

   void endMap()
   {
      System.out.println("Map Finished");
      isGameActive = false;

      //GuiPanelManager.instance.getGamePanel().setEndOfLevelPanelVisible();
   }

   public void loadLastLevel()
   {
      //TO BE CHANGED
      loadLevel(1);
      level = 1;
   }

   public void loadLevel(int _level)
   {
      MapController.instance.loadLevel(_level);
      VehicleController.instance.setMap(MapController.instance.getMap());
      VehicleController.instance.setNumberOfMoves(0);
      level = _level;
      //mapFinished = false;

      isGameActive = true;
   }

   public void nextLevel()
   {
      level++;
      loadLevel(level);
   }

   public void resetLevel()
   {
      loadLevel(level);
   }
}
