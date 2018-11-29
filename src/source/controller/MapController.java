package source.controller;

import java.util.ArrayList;

import interfaces.MapDao;
import source.model.Map;
import source.model.Player;
import source.model.Vehicle;

public class MapController extends Controller
{
   public static MapController instance;

   private MapDao mapDao;
   private Map map;

   MapController()
   {
      instance = this;
      mapDao = new MapDaoImpl();
   }

   void loadLevel(int level)
   {
	   Player currentPlayer = PlayerManager.instance.getCurrentPlayer();
       map = mapDao.extractMap(level, currentPlayer);

      if ( map != null )
      {
         System.out.println("Map Loaded");
      }
   }

   void loadOriginalLevel(int level)
   {
	   map = mapDao.extractMap(level, null);
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
   
   String mapToString()
   {
	      String mapStr = "";
	      boolean found;
	      int mapStrSize = map.getMapSize();
	   	  for ( int i = 0; i < mapStrSize; i++ )
	      {
	         for ( int j = 0; j < mapStrSize; j++ )
	         {
	            found = false;
	            for ( Vehicle vehicle : map.getVehicleArray() )
	            {
	               if ( vehicle.transform.getPosition().y == i && vehicle.transform.getPosition().x == j )
	               {
	                  if ( vehicle.isPlayer() )
	                  {
	                     mapStr = mapStr + "PC ";
	                  }
	                  else
	                  {
	                     mapStr = mapStr + vehicle.getType().substring(0, 1).toUpperCase() + vehicle.transform.getDirection().substring(0, 1).toUpperCase() + " ";
	                  }
	                  found = true;
	                  break;
	               }
	            }
	            if ( !found )
	            {
	               mapStr = mapStr + "SS ";
	            }
	         }
	         mapStr = mapStr.substring(0, mapStr.length() - 1);
	         mapStr = mapStr + "\n";
	      }
	   	  return mapStr;
   }

}
