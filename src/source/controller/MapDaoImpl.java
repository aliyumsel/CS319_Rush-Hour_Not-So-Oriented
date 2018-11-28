package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.MapDao;
import source.model.Car;
import source.model.Map;
import source.model.Player;
import source.model.Truck;
import source.model.Vehicle;

class MapDaoImpl implements MapDao {
	
	ArrayList<Vehicle> vehicleArray = new ArrayList<>();
	
	@Override
	public Map extractMap(int level, Player player) {
		Map map = new Map();
		vehicleArray.clear();
		String theme = "minimalistic";
	    boolean special = true;
	    
	    Scanner scanLevel = null;
	      if ( player != null && player.getLevels().size() >= level && player.getLevels().get(level - 1).getStatus().equals("inProgress") )
	      {
	         System.out.println("Inside inprogress if");
	         try {
				scanLevel = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
			} catch (FileNotFoundException e) {
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
	         System.out.println("Inside else");
	         try {
				scanLevel = new Scanner(new File("src/data/levels/level" + level + ".txt"));
			} catch (FileNotFoundException e) {
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
	         System.out.println("Inside do while");
	         Scanner scanRow = new Scanner(row);
	         scanRow.useDelimiter(" ");

				/*
				 * Object Codes are TU TD TR TL CU CD CR CL SS
				 */

	         while ( scanRow.hasNext() )
	         {

	            String objectCode = scanRow.next();

	            if ( objectCode.equals("TU") )
	            {
	               vehicleArray.add(new Truck(x, y, "Upwards", false, special, theme));
	            }
	            else if ( objectCode.equals("TD") )
	            {
	               vehicleArray.add(new Truck(x, y, "Downwards", false, special, theme));
	            }
	            else if ( objectCode.equals("TR") )
	            {
	               vehicleArray.add(new Truck(x, y, "Right", false, special, theme));
	            }
	            else if ( objectCode.equals("TL") )
	            {
	               vehicleArray.add(new Truck(x, y, "Left", false, special, theme));
	            }
	            else if ( objectCode.equals("CU") )
	            {
	               vehicleArray.add(new Car(x, y, "Upwards", false, special, theme));
	            }
	            else if ( objectCode.equals("CD") )
	            {
	               vehicleArray.add(new Car(x, y, "Downwards", false, special, theme));
	            }
	            else if ( objectCode.equals("CR") )
	            {
	               vehicleArray.add(new Car(x, y, "Right", false, special, theme));
	            }
	            else if ( objectCode.equals("CL") )
	            {
	               vehicleArray.add(new Car(x, y, "Left", false, special, theme));
	            }
	            else if ( objectCode.equals("PC") )
	            {
	               vehicleArray.add(new Car(x, y, "Left", true, special, theme));
	            }
	            else if ( objectCode.equals("PT") )
	            {
	               vehicleArray.add(new Truck(x, y, "Right", true, special, theme));
	            }
	            x++;
	         }
	         y++;
	         x = 0;
	         row = scanLevel.nextLine();
	      } while ( !row.trim().equals("<Map/>") );
	      
	      scanLevel.close();
	      
	      map.formMap(vehicleArray);
	      return map;
	}
	
	private void printVehicleArray()
	   {
	      int i = 0;
	      for ( Vehicle vehicle : vehicleArray )
	      {
	         System.out.println("Vehicle " + ++i);
	         System.out.println("Type: " + vehicle.getType());
	         System.out.println("X Coordinate: " + vehicle.transform.position.x);
	         System.out.println("Y Coordinate: " + vehicle.transform.position.y);
	         System.out.println("Direction:  " + vehicle.transform.direction);
	         System.out.println("Axis:  " + vehicle.transform.axis);
	         //System.out.println("Color: " + vehicle.getColor());
	         System.out.println("Length: " + vehicle.transform.length);
	         System.out.print("Occupied Cells: ");

	         for ( int a = 0; a < vehicle.transform.length; a++ )
	         {
	            System.out.print(vehicle.getOccupiedCells()[a] + " ");
	         }

	         System.out.print("\nOccupied Coordinates: ");

	         for ( int a = 0; a < vehicle.transform.length; a++ )
	         {
	            System.out.print("(" + vehicle.getOccupiedTransforms()[a].position.x + "," + vehicle.getOccupiedTransforms()[a].position.y + ")" + " ");
	         }
	      }
	   }

}
