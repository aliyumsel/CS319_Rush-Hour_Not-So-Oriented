package source.controller;
import interfaces.Updatable;
import source.model.*;

public class VehicleController implements Updatable
{
	private Map map; 
	private Vehicle selectedVehicle;
	private SoundManager soundManager;
	public VehicleController (Map map)
	{
		this.map = map;
		soundManager = new SoundManager();
	}

	//executed every frame write the functionality needed to here
	public void Update()
	{
		if (Input.getMouseButtonPressed(0))
		{
			//System.out.println( "Gonna pick the vehicle at: " + Input.getMouseMatrixPosition()[0] + ", " + Input.getMouseMatrixPosition()[1] );
			Vehicle temp = map.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

			if (temp != null)
			{
				setSelectedVehicle(temp); // bu null testini yapiyoruz cunku bosa tiklaninca selected vehicle in null olmasiini istemiyoruz eski vehicle olarak kalmali
				// ilerde kenara bi tus koyariz vehicle i deselect etmek icin belki
				//System.out.println("Selected vehicle");
			}
		}

		if (selectedVehicle != null)
		{
			if (selectedVehicle.isPlayer() && map.isPlayerAtLast())
			{
				GameManager.instance.endMap();
				return;
			}

			boolean moved = false;
			if (Input.getKeyPressed("w"))
			{
				System.out.println("Gonna move up");
				moved = tryMove("Upwards");
			}
			else if (Input.getKeyPressed("a"))
			{
				System.out.println("Gonna move left");
				moved = tryMove("Left");
			}
			else if (Input.getKeyPressed("s"))
			{
				System.out.println("Gonna move down");
				moved = tryMove("Downwards");
			}
			else if (Input.getKeyPressed("d"))
			{
				System.out.println("Gonna move right");
				moved = tryMove("Right");
			}

			if (moved)
			{
				map.updateMap(map.getVehicleArray());
			}
		}
	}

	
	public void setSelectedVehicle(Vehicle selectedVehicle)
	{
		this.selectedVehicle = selectedVehicle;
		soundManager.vehicleHorn(selectedVehicle.getType());
	}
	
	public boolean tryMove(String direction)
	{
		String vehicleAxis = selectedVehicle.transform.axis;
		int moveAmount = 0;
		int moveCheck = 0;
	
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
