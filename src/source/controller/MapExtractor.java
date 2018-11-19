package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import source.model.Car;
import source.model.GameObject;
import source.model.Truck;
import source.model.Vehicle;

public class MapExtractor {

	private Scanner scanLevel, scanRow;
	private ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();
	private Map map;
	public MapExtractor(int level) throws FileNotFoundException {

		scanLevel = new Scanner(new File("src/data/level" + level + ".txt"));
		int x = 0;
		int y = 0; 

		while (scanLevel.hasNext()) {
			String row = scanLevel.nextLine();
			scanRow = new Scanner(row);
			scanRow.useDelimiter(" ");

			/*
			 * Object Codes are TU TD TR TL CU CD CR CL SS
			 */

			while (scanRow.hasNext()) {

				String objectCode = scanRow.next();

				if (objectCode.equals("TU")) {
					vehicleArray.add(new Truck(x, y, "Upwards", false));
				} else if (objectCode.equals("TD")) {
					vehicleArray.add(new Truck(x, y, "Downwards", false));
				} else if (objectCode.equals("TR")) {
					vehicleArray.add(new Truck(x, y, "Right", false));
				} else if (objectCode.equals("TL")) {
					vehicleArray.add(new Truck(x, y, "Left", false));
				} else if (objectCode.equals("CU")) {
					vehicleArray.add(new Car(x, y, "Upwards", false));
				} else if (objectCode.equals("CD")) {
					vehicleArray.add(new Car(x, y, "Downwards", false));
				} else if (objectCode.equals("CR")) {
					vehicleArray.add(new Car(x, y, "Right", false));
				} else if (objectCode.equals("CL")) {
					vehicleArray.add(new Car(x, y, "Left", false));
				} else if (objectCode.equals("PC")) {
					vehicleArray.add(new Car(x, y, "Right", true));
				} else if (objectCode.equals("PT")) {
					vehicleArray.add(new Truck(x, y, "Right", true));
				}
				x++;
			}
			y++;
			x = 0;
		}
		map = new Map(vehicleArray);
	}

	public ArrayList<Vehicle> getVehicleArray() {
		return vehicleArray;
	}

	public Map getMap() {
		return map;
	}
	public void printVehicleArray() {
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

