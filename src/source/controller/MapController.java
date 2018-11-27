package source.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import source.model.Map;
import source.model.Player;
import source.model.Vehicle;

public class MapController
{
   public static MapController instance;

   private MapExtractor mapExtractor;
   private MapSaver mapSaver;
   private Map map;

//	public boolean mapFinished;

   MapController()
   {
      instance = this;
      mapExtractor = new MapExtractor();
      mapSaver = new MapSaver();
      // map = new Map();
   }

   void loadLevel(int level)
   {
      try
      {
         Player currentPlayer = PlayerManager.instance.getCurrentPlayer();
         map = mapExtractor.extractLevel(level, currentPlayer);
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }

      if ( map != null )
      {
         System.out.println("Map Loaded");
      }
   }

   void loadOriginalLevel(int level)
   {
      try
      {
         map = mapExtractor.extractLevel(level, null);
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }

   public Map getMap()
   {
      return map;
   }

   void updateMap(ArrayList<Vehicle> vehicleArray)
   {
      map.formMap(vehicleArray);
   }

   Vehicle getVehicleBySelectedCell(int x, int y)
   {
      int[] occupiedCells;
      int cellNumber = ( map.getMapSize() * y ) + x;
      for ( Vehicle vehicle : map.getVehicleArray() )
      {
         occupiedCells = vehicle.getOccupiedCells();
         for ( int i = 0; i < occupiedCells.length; i++ )
         {
            if ( cellNumber == occupiedCells[i] )
            {
               return vehicle;
            }
         }
      }
      return null;
   }

   // This method checks if the player is at the last cell he can go
   // One more move will make him get out of the grid and finish the game
   boolean isPlayerAtExit()
   {
      Vehicle player = null;

      for ( Vehicle vehicle : map.getVehicleArray() )
      {
         if ( vehicle.isPlayer() )
         {
            player = vehicle;
            break;
         }
      }
      if ( player == null )
      {
         return false;
      }
      if ( player.transform.position.x + player.transform.length == map.getMapSize() )
      {
         return true;
      }
      return false;
   }

//	public void autosave(ArrayList<Vehicle> vehicleList)
//	{
//		mapSaver.saveMap(vehicleList, map.getMapSize(), GameManager.instance.getLevel(), PlayerManager.instance.getCurrentPlayer());
//	}
}
