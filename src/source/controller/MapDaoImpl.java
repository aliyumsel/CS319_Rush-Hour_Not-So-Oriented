package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.MapDao;
import source.model.*;

class MapDaoImpl implements MapDao
{
   private ArrayList<GameObject> gameObjects = new ArrayList<>();

   @SuppressWarnings("StatementWithEmptyBody")
   @Override
   public Map extractMap(int level, Player player, boolean original)
   {
      Map map = new Map();
      gameObjects.clear();
      String theme = "minimalistic";
      theme = "traffic";
      boolean special = true;

      Scanner scanLevel = null;
      if ( !original )
      {
         //System.out.println("Inside inprogress if");
         try
         {
            scanLevel = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
         } catch (FileNotFoundException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         boolean trace = true;
         while ( trace )
         {
            if ( scanLevel.nextLine().trim().equals("<LevelNo>") )
            {
               if ( scanLevel.nextLine().trim().equals(level + "") )
               {
                  trace = false;
               }
            }
         }
      }
      else
      {
         //System.out.println("Inside else");
         try
         {
            scanLevel = new Scanner(new File("src/data/levels/level" + level + ".txt"));
         } catch (FileNotFoundException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

      int x = 0;
      int y = 0;

      while ( !scanLevel.nextLine().trim().equals("<Map>") )
      {
         ;
      }
      String row = scanLevel.nextLine();
      do
      {
         //System.out.println("Inside do while");
         Scanner scanRow = new Scanner(row);
         scanRow.useDelimiter(" ");

				/*
             * Object Codes are TU TD TR TL CU CD CR CL SS OO
				 */

         while ( scanRow.hasNext() )
         {

            String objectCode = scanRow.next();

            if ( objectCode.equals("TU") )
            {
               gameObjects.add(new Truck(x, y, "Upwards", false, special, theme));
            }
            else if ( objectCode.equals("TD") )
            {
               gameObjects.add(new Truck(x, y, "Downwards", false, special, theme));
            }
            else if ( objectCode.equals("TR") )
            {
               gameObjects.add(new Truck(x, y, "Right", false, special, theme));
            }
            else if ( objectCode.equals("TL") )
            {
               gameObjects.add(new Truck(x, y, "Left", false, special, theme));
            }
            else if ( objectCode.equals("CU") )
            {
               gameObjects.add(new Car(x, y, "Upwards", false, special, theme));
            }
            else if ( objectCode.equals("CD") )
            {
               gameObjects.add(new Car(x, y, "Downwards", false, special, theme));
            }
            else if ( objectCode.equals("CR") )
            {
               gameObjects.add(new Car(x, y, "Right", false, special, theme));
            }
            else if ( objectCode.equals("CL") )
            {
               gameObjects.add(new Car(x, y, "Left", false, special, theme));
            }
            else if ( objectCode.equals("PC") )
            {
               gameObjects.add(new Car(x, y, "Left", true, special, theme));
            }
            else if ( objectCode.equals("PT") )
            {
               gameObjects.add(new Truck(x, y, "Right", true, special, theme));
            }
            else if ( objectCode.equals("OO") )
            {
               gameObjects.add(new Obstacle(x, y, 1, "Right"));
            }
            x++;
         }
         y++;
         x = 0;
         row = scanLevel.nextLine();
      } while ( !row.trim().equals("<Map/>") );

      scanLevel.close();

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
