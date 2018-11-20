package source.model;

import java.util.ArrayList;

public class Map {
	
	private ArrayList<Vehicle> vehicleArray;
	private String[][] grid;
	private int mapSize = 6;
	
	public Map(ArrayList<Vehicle> vehicleArray)
	{
		this.vehicleArray = vehicleArray;
		formMap(vehicleArray);
	}
	
	public void formMap(ArrayList<Vehicle> vehicleArray)
   {
		grid = new String[mapSize][mapSize];
		
		for (Vehicle vehicle: vehicleArray)
		{
			String name = vehicle.getType();
			for(int i = 0; i < vehicle.transform.length;i++)
			{
				grid[vehicle.getOccupiedCells()[i]/mapSize][vehicle.getOccupiedCells()[i]%mapSize] = name;
			}
		}
		
		for(int i = 0; i < mapSize;i++)
		{
			for(int j = 0; j < mapSize;j++)
			{
				if( grid[i][j] == null)
				{
					grid[i][j] = "Space";
				}
			}
		}
	}
	
	public void updateMap(ArrayList<Vehicle> vehicleArray)
   {
	   formMap(vehicleArray);
	}
	
	public ArrayList<Vehicle> getVehicleArray()
   {
	   return vehicleArray;
	}
	
	public String[][] getGrid()
   {
	   return grid;
	}
	public void printMap() {
		for(int i = 0; i < mapSize;i++) {
			//System.out.println("\n");
			for(int j = 0; j < mapSize;j++)
			{
				System.out.print(grid[i][j] + " ");
			}
		}
	}

	public int getMapSize() {
		return mapSize;
	}

	//public void setMapSize(int mapSize) {
	//  	this.mapSize = mapSize;
	//}
	
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
	//One more move will make him get out of the grid and finish the game
	public boolean isPlayerAtExit()
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
