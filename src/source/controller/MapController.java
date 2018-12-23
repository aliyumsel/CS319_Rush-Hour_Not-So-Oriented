package source.controller;

import interfaces.MapDao;
import source.model.*;

import java.util.ArrayList;

public class MapController extends Controller
{
   private static MapController instance = null;

   private MapDao mapDao;
   private Map map;

   private MapController()
   {
      mapDao = new MapDaoImpl();
   }

   public static MapController getInstance()
   {
      if(instance == null) {
         instance = new MapController();
      }
      return instance;
   }

   void loadLevel(int level)
   {
      Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
      map = mapDao.extractMap(level, currentPlayer, false);
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
      GameObject temp = getGameObjectBySelectedCell(x, y);

      if ( temp instanceof Vehicle )
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
      GameObject temp = getGameObjectBySelectedCell(x, y);

      if ( temp instanceof Obstacle )
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

   void highlightObstacles()
   {
      for ( GameObject gameObject : map.getGameObjects() )
      {
         if ( gameObject instanceof Vehicle )
         {
            gameObject.showBlackForeground();
         }
      }
   }

   void highlightLongs()
   {
      for ( GameObject gameObject : map.getGameObjects() )
      {
         if ( gameObject instanceof Car )
         {
            gameObject.showBlackForeground();
         }
         else if ( gameObject instanceof Obstacle )
         {
            gameObject.showBlackForeground();
         }
      }
   }

   void clearHighlights()
   {
      for ( GameObject gameObject : map.getGameObjects() )
      {
         gameObject.hideBlackForeground();
      }
   }

   // This method checks if the player is at the last cell he can go
   // One more move will make him get out of the grid and finish the game
   // Map should hold a reference to the player car so we don't have to check every game object every move.
   boolean isPlayerAtExit()
   {
      Vehicle player = getPlayerVehicle();

      if ( player == null )
      {
         return false;
      }
      return player.transform.position.gridX + player.transform.length == map.getMapSize();
   }

   // String builder kullansak daha guzel olcak
   String mapToString()
   {
      String[][] mapStr = new String[map.getMapSize()][map.getMapSize()];
      StringBuilder mapString = new StringBuilder();

      ArrayList<GameObject> gameObjects = map.getGameObjects();
      for ( GameObject gameObject : gameObjects )
      {
         if ( gameObject instanceof Vehicle )
         {
            int[] cells = gameObject.getOccupiedCells();
            for ( int i = 0; i < cells.length; i++ )
            {
               int x = cells[i] / mapStr.length;
               int y = cells[i] % mapStr.length;
               if ( i == 0 )
               {
                  if ( ( (Vehicle) gameObject ).isPlayer() )
                  {
                     mapStr[x][y] = "PC";
                  }
                  else
                  {
                     mapStr[x][y] = gameObject.getType().substring(0, 1).toUpperCase() + gameObject.transform.getDirection().substring(0, 1).toUpperCase();
                  }
               }
               else
               {
                  mapStr[x][y] = "XX";
               }
            }
         }
         else if ( gameObject instanceof Obstacle )
         {
            int cell = gameObject.getOccupiedCells()[0];
            int x = cell / mapStr.length;
            int y = cell % mapStr.length;
            mapStr[x][y] = "OO";
         }
      }

      for ( int i = 0; i < mapStr.length; i++ )
      {
         for ( int j = 0; j < mapStr.length; j++ )
         {
            if (mapStr[i][j] == null)
            {
               mapStr[i][j] = "SS";
            }
            mapString.append(mapStr[i][j]).append(" ");
         }
         mapString.append("| ");
      }

//      System.out.println("MAP TO STRING: " + mapString.substring(0, mapString.length() - 2));
      return mapString.substring(0, mapString.length() - 2);
   }

   Vehicle getPlayerVehicle()
   {
      Vehicle player = null;
      Vehicle temp;

      for ( GameObject gameObject : map.getGameObjects() )
      {
         if ( gameObject instanceof Vehicle )
         {
            temp = (Vehicle) gameObject;
            if ( temp.isPlayer() )
            {
               player = temp;
               break;
            }
         }
      }
      return player;
   }
}
