package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import source.model.Car;
import source.model.GameObject;
import source.model.Player;
import source.model.Truck;
import source.model.Vehicle;

public class MapExtractor {

	//private Scanner scanLevel, scanRow;
	//private ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();
	//private Map map;
	//public MapExtractor(int level) throws FileNotFoundException { 
	public static Map extractMap(Player palyer, int level) throws FileNotFoundException {
		Scanner scanLevel, scanRow;
		ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();
		Map map;
		
		if (player.getLevels()[level - 1].getStatus().equals("inProgress"))
		{
			scanLevel = new Scanner(new File("src/data/players/" + player.getName() + "/inProgress.txt"));
		}
		else
		{
			scanLevel = new Scanner(new File("src/data/levels/level" + level + ".txt"));
		}
		
		//scanLevel = new Scanner(new File("src/data/level" + level + ".txt"));
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
		return map;
	}


	
}

