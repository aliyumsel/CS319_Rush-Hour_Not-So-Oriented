package source.controller;

import java.util.ArrayList;

import interfaces.MapDao;
import source.model.*;

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
      map = mapDao.extractMap(level, currentPlayer, false);

      if ( map != null )
      {
         System.out.println("Map Loaded");
      }
   }

   void loadOriginalLevel(int level)
   {
      map = mapDao.extractMap(level, null, true);
   }

   public Map getMap()
   {
      return map;
   }

   void updateMap(ArrayList<GameObject> gameObjects)
   {
      map.formMap(gameObjects);
   }

   //Currently testing / May even crate a method called formMap() with no arguments
   void updateMap()
   {
      map.formMap(map.getGameObjects());
   }

   private GameObject getGameObjectBySelectedCell(int x, int y)
   {
      int[] occupiedCells;
      int cellNumber = ( map.getMapSize() * y ) + x;
      GameObject selectedObject = null;
      for ( GameObject gameObject : map.getGameObjects() )
      {
         occupiedCells = gameObject.getOccupiedCells();
         for ( int i = 0; i < occupiedCells.length; i++ )
         {
            if ( cellNumber == occupiedCells[i] )
            {
               selectedObject = gameObject;
               break;
            }
         }
      }

      return selectedObject;
   }

   Vehicle getVehicleBySelectedCell(int x, int y)
   {
      GameObject temp = getGameObjectBySelectedCell(x,y);

      if (temp instanceof Vehicle)
      {
         return (Vehicle) temp;
      }
      else
      {
         return null;
      }
   }

   Obstacle getObstacleBySelectedCell(int x, int y)
   {
      GameObject temp = getGameObjectBySelectedCell(x,y);

      if (temp instanceof Obstacle)
      {
         return (Obstacle) temp;
      }
      else
      {
         return null;
      }
   }

   void removeGameObject(GameObject gameObject)
   {
      map.getGameObjects().remove(gameObject);
   }

   void addGameObject(GameObject gameObject)
   {
      map.getGameObjects().add(gameObject);
   }

   // This method checks if the player is at the last cell he can go
   // One more move will make him get out of the grid and finish the game
   boolean isPlayerAtExit()
   {
      Vehicle player = null;
      Vehicle temp = null;

      for ( GameObject gameObject : map.getGameObjects() )
      {
         if (gameObject instanceof Vehicle)
         {
            temp = (Vehicle)gameObject;
            if ( temp.isPlayer() )
            {
               player = temp;
               break;
            }
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
            for ( GameObject gameObject : map.getGameObjects() )
            {
               if ( gameObject.transform.getPosition().y == i && gameObject.transform.getPosition().x == j )
               {
                  if (gameObject instanceof Vehicle)
                  {
                     if ( ((Vehicle)gameObject).isPlayer() )
                     {
                        mapStr = mapStr + "PC ";
                     }
                     else
                     {
                        mapStr = mapStr + gameObject.getType().substring(0, 1).toUpperCase() + gameObject.transform.getDirection().substring(0, 1).toUpperCase() + " ";
                     }
                     found = true;
                     break;
                  }

                  if (gameObject instanceof Obstacle)
                  {
                     mapStr = mapStr + "OO ";
                     found = true;
                     break;
                  }
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
      //System.out.println(mapStr);
      return mapStr;
   }

}
