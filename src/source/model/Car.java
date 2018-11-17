package source.model;

public class Car extends Vehicle
{
	public Car(int x, int y, String direction, boolean player)
	{
		super(x, y, 2, direction, player);
		if (player)
		{
			super.setType("Playr");
		}
		else
		{
		super.setType("Car  ");
		}
	}
}
