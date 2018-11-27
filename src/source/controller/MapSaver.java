package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import source.model.Player;
import source.model.Vehicle;

public class MapSaver {
	
	public void saveMap(ArrayList<Vehicle> vehicleList, int mapSize, int levelNo, Player player)
	{
		
		//converts vehicle list to data format
		String map = "";
		boolean found;
		for (int i = 0; i < mapSize; i++)
		{
			for (int j = 0; j < mapSize; j++)
			{
				found = false;
				for (Vehicle vehicle : vehicleList)
				{
					if (vehicle.transform.getPosition().y == i && vehicle.transform.getPosition().x == j)
					{
						if (vehicle.isPlayer())
						{
							map = map + "PC ";
						}
						else
						{
							map = map + vehicle.getType().substring(0,1).toUpperCase() + vehicle.transform.getDirection().substring(0,1).toUpperCase() + " ";
						}
						found = true;
						break;
					}
				}
				if (!found)
				{
					map = map + "SS ";
				}
			}
			map = map.substring(0, map.length() -1);
			map = map + "\n";
		}
		
		//saves the map
		Scanner scan = null;
		try {
			scan = new Scanner( new File(player.getPath() + "/playerInfo.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String line, text;
		int no;
		
		line = scan.nextLine();
		text = line + "\n";
		while (!line.trim().equals("<Levels>"))
		{
			line = scan.nextLine();
			text = text + line + "\n";	
		}
		line = scan.nextLine();
		text = text + line + "\n";
		while (!line.trim().equals("<Levels/>"))
		{
			if (line.trim().equals("<LevelNo>"))
			{
				line = scan.nextLine();
				text = text + line + "\n";	
				no = Integer.parseInt(line.trim());
				if (no == levelNo)
				{
					break;
				}
			}
			line = scan.nextLine();
			text = text + line + "\n";
		}
		while (!line.trim().equals("<Map>"))
		{
			line = scan.nextLine();
			text = text + line + "\n";	
		}
		
		text = text + map;
		
		while (!scan.nextLine().trim().equals("<Map/>"));
		
		line = "\t\t\t<Map/>";
		text = text + line + "\n";
		
		while (scan.hasNext())
		{
			line = scan.nextLine();
			text = text + line + "\n";
		}
		
		FileWriter fileOut = null;
        try {
        	fileOut = new FileWriter(player.getPath() + "/playerInfo.txt");
			fileOut.write(text);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
