package source.model;

import java.util.ArrayList;

public class Map
{

   private ArrayList<GameObject> gameObjects;
   private String[][] grid;
   private int mapSize = 8;

   public Map()
   {

   }

   public Map(ArrayList<GameObject> gameObjects)
   {
      this.gameObjects = gameObjects;
      formMap(gameObjects);
   }

   public void formMap(ArrayList<GameObject> _gameObjects)
   {
      gameObjects = _gameObjects;

      grid = new String[mapSize][mapSize];

      for ( GameObject gameObject : gameObjects )
      {
         String name = gameObject.getType();
//         System.out.println("NAME: " + name);
         for ( int i = 0; i < gameObject.transform.length; i++ )
         {
//            System.out.println("OCCUPIED CELL: " + (gameObject.getOccupiedCells()[i] / mapSize + "," + gameObject.getOccupiedCells()[i] % mapSize));
            grid[gameObject.getOccupiedCells()[i] / mapSize][gameObject.getOccupiedCells()[i] % mapSize] = name;
         }
      }

//      System.out.println("GRID: " + grid.toString());

      for ( int i = 0; i < mapSize; i++ )
      {
         for ( int j = 0; j < mapSize; j++ )
         {
            if ( grid[i][j] == null)
            {
               grid[i][j] = "Space";
            }
         }
      }

   }

   public ArrayList<GameObject> getGameObjects()
   {
      return gameObjects;
   }

   public String[][] getGrid()
   {
      return grid;
   }

   public int getMapSize()
   {
      return mapSize;
   }

}
