package source.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import source.model.Player;
import source.model.Vehicle;

public class MapController {
	private Map map;
	private boolean isMapFinished;
	
	static MapController instance;
	
	public MapController()
    {
    	if (instance == null)
    	{    		
    		instance = this;
    	}
    	isMapFinished = false;
    }
	
	public void extractMap(Player player, int level) throws FileNotFoundException
	{
		map = MapExtractor.extractMap(level);
		
	}
	public Map getMap() {
		return map;
	}
	public void printVehicleArray() {
		int i = 0;
		for (Vehicle vehicle : map.getVehicleArray()) {
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

			System.out.println("\n");
		}
	}
	
	public void updateMap(ArrayList<Vehicle> vehicleArray)
	{
		map.formMap(vehicleArray);
		MapSaver.saveMap(map);
	}
	public Vehicle getVehicleBySelectedCell(int x, int y)
	{
		int[] occupiedCells;
		int cellNumber = (map.getMapSize() * y) + x;
		for (Vehicle vehicle : map.getVehicleArray())
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
		
		for (Vehicle v : map.getVehicleArray())
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
		if (player.transform.position.x + player.transform.length == map.getMapSize())
		{
			return true;
		}
		return false;
	}

	public boolean isMapFinished() {
		return isMapFinished;
	}

	public void setMapFinished(boolean isMapFinished) {
		this.isMapFinished = isMapFinished;
	}
	

}
