package source.model;

public class Car extends Vehicle {

	public Car(int x, int y, String direction, boolean player, boolean special, String theme)
	{
		super(x, y, 2, direction, player, special, theme);
		if (player)
		{
			super.setType("Player");
		}
		else
		{
			super.setType("Car");
		}
	}

}
