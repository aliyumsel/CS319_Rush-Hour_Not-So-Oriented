package source.model;

public class Transform
{
	public class Position
	{
		public int x;
		public int y;

		public Position(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public Position()
		{
			this.x = 0;
			this.y = 0;
		}
	}

	public Position position;
	public int length;
	public String axis;
	public String direction;

	public Transform(int x, int y, int length, String direction)
	{
		String _axis;
		if (direction.equals("Upwards") || direction.equals("Downwards"))
		{
			_axis = "Vertical";
		}
		else if (direction.equals("Right") || direction.equals("Left"))
		{
			_axis = "Horizontal";
		}
		else
		{
			_axis = "";
		}

		position = new Position(x, y);
		this.length = length;
		this.axis = _axis;
		this.direction = direction;
	}

	public Transform()
	{
		position = new Position();
		length = 1;
		axis = "Vertical";
		direction = "Upwards";
	}

	public void setPosition(int x, int y)
	{
		position.x = x;
		position.y = y;
	}

	public Position getPosition()
	{
		return position;
	}

	public int getLength()
	{
		return length;
	}

	public String getAxis()
	{
		return axis;
	}

	public String getDirection() {
		return direction;
	}
}
