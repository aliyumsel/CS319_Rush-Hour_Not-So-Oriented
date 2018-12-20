package source.controller;

import com.google.gson.Gson;
import interfaces.MapDao;
import source.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class MapDaoImpl implements MapDao
{

   private Gson gson = new Gson();
   private ArrayList<GameObject> gameObjects = new ArrayList<>();

   @SuppressWarnings("StatementWithEmptyBody")
   @Override
   public Map extractMap(int level, Player player, boolean original)
   {
      Map map = new Map();
      gameObjects.clear();
      
      String mapStr = "";
      if ( !original )
      {
         //System.out.println("Inside inprogress if");
         mapStr = player.getLevels().get(level - 1).getMap();
      }
      else
      {
         //System.out.println("Inside else");
         try {
            mapStr = gson.fromJson(new FileReader("src/data/levels/level" + level + ".json"), OriginalLevel.class).map;
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
      }

      int x = 0;
      int y = 0;
      Scanner scan = new Scanner(mapStr);
      scan.useDelimiter(" ");

         //Object Codes are TU TD TR TL CU CD CR CL SS OO
      while ( scan.hasNext() )
      {
         String objectCode = scan.next();
         if (objectCode.equals("|"))
         {
            y++;
            x = 0;
            continue;
         }
         if ( objectCode.equals("TU") )
         {
            gameObjects.add(new Truck(x, y, "Upwards", false));
         }
         else if ( objectCode.equals("TD") )
         {
            gameObjects.add(new Truck(x, y, "Downwards", false));
         }
         else if ( objectCode.equals("TR") )
         {
            gameObjects.add(new Truck(x, y, "Right", false));
         }
         else if ( objectCode.equals("TL") )
         {
            gameObjects.add(new Truck(x, y, "Left", false));
         }
         else if ( objectCode.equals("CU") )
         {
            gameObjects.add(new Car(x, y, "Upwards", false));
         }
         else if ( objectCode.equals("CD") )
         {
            gameObjects.add(new Car(x, y, "Downwards", false));
         }
         else if ( objectCode.equals("CR") )
         {
            gameObjects.add(new Car(x, y, "Right", false));
         }
         else if ( objectCode.equals("CL") )
         {
            gameObjects.add(new Car(x, y, "Left", false));
         }
         else if ( objectCode.equals("PC") )
         {
            gameObjects.add(new Car(x, y, "Left", true));
         }
         else if ( objectCode.equals("PT") )
         {
            gameObjects.add(new Truck(x, y, "Right", true));
         }
         else if ( objectCode.equals("OO") )
         {
            gameObjects.add(new Obstacle(x, y, 1, "Right"));
         }
//         else if (objectCode.equals("SS"))
//         {
//            gameObjects.add(new Space(x, y, 1, "Right"));
//         }
         x++;
      }

      System.out.println(gameObjects.toString());
      map.formMap(gameObjects);
      return map;
   }

   private void printVehicleArray()
   {
      int i = 0;
      for ( GameObject gameObject : gameObjects )
      {
         System.out.println("Vehicle " + ++i);
         System.out.println("Type: " + gameObject.getType());
         System.out.println("X Coordinate: " + gameObject.transform.position.x);
         System.out.println("Y Coordinate: " + gameObject.transform.position.y);
         System.out.println("Direction:  " + gameObject.transform.direction);
         System.out.println("Axis:  " + gameObject.transform.axis);
         //System.out.println("Color: " + vehicle.getColor());
         System.out.println("Length: " + gameObject.transform.length);
         System.out.print("Occupied Cells: ");

         for ( int a = 0; a < gameObject.transform.length; a++ )
         {
            System.out.print(gameObject.getOccupiedCells()[a] + " ");
         }

         System.out.print("\nOccupied Coordinates: ");

         for ( int a = 0; a < gameObject.transform.length; a++ )
         {
            System.out.print("(" + gameObject.getOccupiedTransforms()[a].position.x + "," + gameObject.getOccupiedTransforms()[a].position.y + ")" + " ");
         }
      }
   }

}
