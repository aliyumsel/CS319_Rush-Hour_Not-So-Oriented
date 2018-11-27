package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import source.model.Map;
import source.model.Player;
import source.model.Car;
import source.model.Truck;
import source.model.Vehicle;

public class MapExtractor {

	private Scanner scanLevel, scanRow;
	private ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();
	private Map map;

	public MapExtractor()
   {
      map = new Map();
	}

	public Map extractLevel(int level, Player player) throws FileNotFoundException
   {
		System.out.println("Inside extract level");
   	  String theme = "minimalistic";
   	  boolean special = true;
   	  vehicleArray.clear();
      //inside of the if condition will be revised
		//System.out.println("Player: " + player.getPlayerName());
		//System.out.println("Player level size: " + player.getLevels().size());
		//System.out.println("Level: " + level);
		//System.out.println("Status: " + player.getLevels().get(level - 1).getStatus());

      if (player != null && player.getLevels().size() >= level && player.getLevels().get(level - 1).getStatus().equals("inProgress"))
	  {
    	  System.out.println("Inside inprogress if");
    	  scanLevel = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
    	  boolean trace = true;
    	  while (trace)
    	  {
    		  if (scanLevel.nextLine().trim().equals("<LevelNo>"))
    		  {
    			  if (scanLevel.nextLine().trim().equals(level + ""))
    			  {
    				  trace = false;
    			  }
    		  }
    	  }
	  }
      else
      {
    	  System.out.println("Inside else");
    	  scanLevel = new Scanner(new File("src/data/levels/level" + level + ".txt"));
      }

      int x = 0;
      int y = 0;

      while (!scanLevel.nextLine().trim().equals("<Map>"));
      String row = scanLevel.nextLine();
      do {
    	  System.out.println("Inside do while");
         scanRow = new Scanner(row);
         scanRow.useDelimiter(" ");

			/*
			 * Object Codes are TU TD TR TL CU CD CR CL SS
			 */

         while (scanRow.hasNext()) {

            String objectCode = scanRow.next();

            if (objectCode.equals("TU")) {
               vehicleArray.add(new Truck(x, y, "Upwards", false, special, theme));
            } else if (objectCode.equals("TD")) {
               vehicleArray.add(new Truck(x, y, "Downwards", false, special, theme));
            } else if (objectCode.equals("TR")) {
               vehicleArray.add(new Truck(x, y, "Right", false, special, theme));
            } else if (objectCode.equals("TL")) {
               vehicleArray.add(new Truck(x, y, "Left", false, special, theme));
            } else if (objectCode.equals("CU")) {
               vehicleArray.add(new Car(x, y, "Upwards", false, special, theme));
            } else if (objectCode.equals("CD")) {
               vehicleArray.add(new Car(x, y, "Downwards", false, special, theme));
            } else if (objectCode.equals("CR")) {
               vehicleArray.add(new Car(x, y, "Right", false, special, theme));
            } else if (objectCode.equals("CL")) {
               vehicleArray.add(new Car(x, y, "Left", false, special, theme));
            } else if (objectCode.equals("PC")) {
               vehicleArray.add(new Car(x, y, "Left", true, special, theme));
            } else if (objectCode.equals("PT")) {
               vehicleArray.add(new Truck(x, y, "Right", true, special, theme));
            }
            x++;
         }
         y++;
         x = 0;
         row = scanLevel.nextLine();
      } while (!row.trim().equals("<Map/>"));
      map.formMap(vehicleArray);
      //printVehicleArray();
      return map;
   }

	public ArrayList<Vehicle> getVehicleArray() {
		return vehicleArray;
	}

	public Map getMap() {
		return map;
	}

	private void printVehicleArray()
   {
		int i = 0;
		for (Vehicle vehicle : vehicleArray) {
			System.out.println("Vehicle " + ++i);
			System.out.println("Type: " + vehicle.getType());
			System.out.println("X Coordinate: " + vehicle.transform.position.x);
			System.out.println("Y Coordinate: " + vehicle.transform.position.y);
			System.out.println("Direction:  " + vehicle.transform.direction);
			System.out.println("Axis:  " + vehicle.transform.axis);
			//System.out.println("Color: " + vehicle.getColor());
			System.out.println("Length: " + vehicle.transform.length);
			System.out.print("Occupied Cells: ");

			for (int a = 0; a < vehicle.transform.length; a++)
				System.out.print(vehicle.getOccupiedCells()[a] + " ");

			System.out.print("\nOccupied Coordinates: ");

			for (int a = 0; a < vehicle.transform.length; a++)
			{
				System.out.print("(" + vehicle.getOccupiedTransforms()[a].position.x + "," + vehicle.getOccupiedTransforms()[a].position.y + ")" + " ");
			}

			//System.out.println("\n");
		}
	}
}

