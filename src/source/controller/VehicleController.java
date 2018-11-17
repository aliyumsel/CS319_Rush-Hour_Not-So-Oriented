package source.controller;
import source.model.*;

public class VehicleController {
	
	Map map; 
	Vehicle selectedVehicle;
	public VehicleController (Map map)
	{
		this.map = map;
			
	}
	
	/*
	public void Update()
	{
		if ( input.getMouseButtonDown() )
		{
			ýnput.mouseLocatýon
			setSelectedVehýcle();
		}
		
		if (ýnput.getDýrectýon)
		{
			ýnput.gert
			if (trymove)
				move;
				map.formMap();

		}
		
	}
	*/
	
	public void setSelectedVehicle(Vehicle selectedVehicle)
	{
		this.selectedVehicle = selectedVehicle;
	}
	
	public boolean tryMove(String direction)
	{
		String vehicleAxis = selectedVehicle.transform.axis;
		int moveAmount = 0;
		int moveCheck = 0;
		/*
		if (direction.equals("Left") || direction.equals("Upwards"))
		{	
			moveAmount = -1;
			moveCheck = -1;
		}
		if (direction.equals("Right") || direction.equals("Downwards"))
		{	
			moveAmount = 1; 
			moveCheck = selectedVehicle.transform.length;
		}
		*/
		if (vehicleAxis.equals("Horizontal"))
		{
			if (direction.equals("Left"))
			{	
				moveAmount = -1;
				moveCheck = -1;
			}
			else if (direction.equals("Right"))
			{	
				moveAmount = 1; 
				moveCheck = selectedVehicle.transform.length;
			}
			if (selectedVehicle.transform.position.x + moveCheck < 0 || selectedVehicle.transform.position.x + moveCheck >= map.getMapSize())
			{
				return false;
			}
			
			if (map.getMap()[selectedVehicle.transform.position.y][selectedVehicle.transform.position.x + moveCheck].equals("Space"))
			{
				selectedVehicle.move(moveAmount);
				return true;
			}
			
			
		}
		if (vehicleAxis.equals("Vertical"))
		{
			if (direction.equals("Upwards"))
			{	
				moveAmount = 1;
				moveCheck = -1;
			}
			else if (direction.equals("Downwards"))
			{	
				moveAmount = -1; 
				moveCheck = selectedVehicle.transform.length;
			}
			if (selectedVehicle.transform.position.y + moveCheck < 0 || selectedVehicle.transform.position.y + moveCheck >= map.getMapSize())
			{
				return false;
			}
			
			if (map.getMap()[ (selectedVehicle.transform.position.y) + moveCheck][selectedVehicle.transform.position.x].equals("Space"))
			{
				selectedVehicle.move(moveAmount);
				return true;
			}
			
			
			
		}
		return false;
	}
	
	
	

}
