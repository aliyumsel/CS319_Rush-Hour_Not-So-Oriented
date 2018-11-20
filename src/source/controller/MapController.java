package source.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import source.model.Map;
import source.model.Vehicle;

/**
 * Created by ASTOD on 11/19/2018.
 */
public class MapController
{
   public static MapController instance;

   private MapExtractor mapExtractor;
   private Map map;

   public boolean mapFinished;

   public MapController()
   {
      instance = this;
      mapExtractor = new MapExtractor();
      //map = new Map();
   }

   public void loadLevel(int level)
   {
      try
      {
         map = mapExtractor.extractLevel(level);
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }

      if (map != null)
      {
         System.out.println("Map Loaded");
         map.printMap();
      }
   }

   public Map getMap()
   {
      return map;
   }

   public void updateMap(ArrayList<Vehicle> vehicleArray)
   {
      map.formMap(vehicleArray);
   }

   public Vehicle getVehicleBySelectedCell(int x, int y)
   {
      int[] occupiedCells;
      int cellNumber = (map.getMapSize() * y) + x;
      for (Vehicle vehicle : map.getVehicleArray())
      {
         occupiedCells = vehicle.getOccupiedCells();
         for (int i = 0; i < occupiedCells.length; i++)
         {
            if (cellNumber == occupiedCells[i])
            {
               return vehicle;
            }
         }
      }
      return null;
   }

   //This method checks if the player is at the last cell he can go
   //One more move will make him get out of the grid and finish the game
   public boolean isPlayerAtExit()
   {
      Vehicle player = null;

      for (Vehicle v : map.getVehicleArray())
      {
         if (v.isPlayer())
         {
            player = v;
            break;
         }
      }
      if (player == null)
      {
         return false;
      }
      if (player.transform.position.x + player.transform.length == map.getMapSize())
      {
         return true;
      }
      return false;
   }

}
