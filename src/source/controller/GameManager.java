package source.controller;

import java.util.ArrayList;

import interfaces.Updatable;
import source.model.Vehicle;
import source.model.Player;
import source.view.GuiPanelManager;

public class GameManager implements Updatable
{
	public static GameManager instance;
   public PlayerManager playerManager;

   public int level;

   public boolean isGameActive = false;

   GameManager()
   {
	  playerManager = PlayerManager.instance;
      instance = this;
   }

   public void Update()
   {

   }
   
   public void autoSave(int moveAmount, ArrayList<Vehicle> vehicleList)
   {
	   PlayerManager.instance.updateLevel(level, moveAmount, vehicleList);
   }
   
   void endMap()
   {
      System.out.println("Map Finished");
      isGameActive = false;
      PlayerManager.instance.setLevelStatus(level, "finished");
      GuiPanelManager.instance.getGamePanel().setEndOfLevelPanelVisible();
   }

   public void loadLastLevel()
   {
      //TO BE CHANGED
      loadLevel(1, false);
      level = 1;
   }

   public void loadLevel(int _level, boolean original)
   {
      System.out.println(PlayerManager.instance.getCurrentPlayer().getLevels().get(0).getStatus());

      if (original)
      {
         MapController.instance.loadOriginalLevel(_level);
      }
      else
      {
         MapController.instance.loadLevel(_level);
      }

      VehicleController.instance.setMap(MapController.instance.getMap());
      VehicleController.instance.setNumberOfMoves(0);
      GuiPanelManager.instance.getGamePanel().setInnerGamePanelVisible();
      level = _level;

      isGameActive = true;
   }

   public void nextLevel()
   {
      level++;
      loadLevel(level,false);
   }

   public void resetLevel()
   {
      loadLevel(level,true);
      autoSave(0, MapController.instance.getMap().getVehicleArray());
   }

   public int getLevel() {
	   return level;
   }

   public void setLevel(int level) {
	   this.level = level;
   }
   
}
