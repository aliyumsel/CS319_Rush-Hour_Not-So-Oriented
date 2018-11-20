package source.model;

import java.util.ArrayList;

public class Map {
	
	private ArrayList<Vehicle> vehicleArray;
	private String[][] grid;
	private int mapSize = 6;

	public Map()
   {

   }

	public Map(ArrayList<Vehicle> vehicleArray)
	{
		this.vehicleArray = vehicleArray;
		formMap(vehicleArray);
	}
	
	public void formMap(ArrayList<Vehicle> _vehicleArray)
   {
      vehicleArray = _vehicleArray;

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

	public ArrayList<Vehicle> getVehicleArray()
   {
	   return vehicleArray;
	}
	
	public String[][] getGrid()
   {
	   return grid;
	}

   public int getMapSize() {
      return mapSize;
   }

	public void printMap()
   {
      System.out.println("Vehicle Array: " + vehicleArray);

		for(int i = 0; i < mapSize;i++)
		{
			//System.out.println("\n");
			for(int j = 0; j < mapSize;j++)
			{
				System.out.print(grid[i][j] + " ");
			}
		}
	}



	//public void setMapSize(int mapSize) {
	//  	this.mapSize = mapSize;
	//}
	

}
