package source.controller;

import java.util.ArrayList;

import source.model.GameObject;
import source.model.Vehicle;

public class Map {
	
	private ArrayList<Vehicle> vehicleArray;
	private String[][] map;
	private int mapSize = 6;
	
	public Map(ArrayList<Vehicle> vehicleArray) {
		this.vehicleArray = vehicleArray;
		formMap(vehicleArray);
	}
	
	public void formMap(ArrayList<Vehicle> vehicleArray) {
		map = new String[mapSize][mapSize];
		
		for (Vehicle vehicle: vehicleArray) {
			String name = vehicle.getType();
			for(int i = 0; i < vehicle.transform.length;i++) {
				map[vehicle.getOccupiedCells()[i]/mapSize][vehicle.getOccupiedCells()[i]%mapSize] = name;
			}
		}
		
		for(int i = 0; i < mapSize;i++) {
			for(int j = 0; j < mapSize;j++)
			{
				if(map[i][j] == null)
				{
					map[i][j] = "Space";
				}
			}
		}
	}
	
	public void updateMap(ArrayList<Vehicle> vehicleArray) {
		formMap(vehicleArray);
	}
	
	public ArrayList<Vehicle> getVehicleArray(){
		return vehicleArray;
	}
	
	public String[][] getMap(){
		return map;
	}
	public void printMap() {
		for(int i = 0; i < mapSize;i++) {
			System.out.println("\n");
			for(int j = 0; j < mapSize;j++)
			{
				System.out.print(map[i][j] + " ");
			}
		}
	}

	public int getMapSize() {
		return mapSize;
	}

	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}
	
	public Vehicle getVehicleBySelectedCell(int x, int y)
	{
		int[] occupiedCells;
		int cellNumber = (mapSize * y) + x;
		for (Vehicle vehicle : vehicleArray)
		{
			occupiedCells = vehicle.getOccupiedCells();
			for (int i = 0; i < occupiedCells.length; i++)
			{
				if (cellNumber == occupiedCells[i])
				{
					return vehicle;
				}
			}
		}
		return null;
	}
	
	//This method checks if the player is at the last cell he can go
	//One more move will make him get out of the map and finish the game
	public boolean isPlayerAtLast()
	{
		Vehicle player = null;
		
		for (Vehicle v : vehicleArray)
		{
			if (v.isPlayer())
			{
				player = v;
				break;
			}
		}
		if (player == null)
		{
			return false;
		}
		if (player.transform.position.x + player.transform.length == mapSize)
		{
			return true;
		}
		return false;
	}
	
	
}
