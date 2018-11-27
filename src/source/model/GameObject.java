package source.model;

public class GameObject
{
	public Transform transform;
	private int[] occupiedCellNumbers;
	Transform[] occupiedTransforms;

	GameObject()
	{
		transform = new Transform();
		findOccupiedCells();
	}

	GameObject(int x, int y, int length, String direction)
	{
		transform = new Transform(x, y, length, direction);
		findOccupiedCells();
	}

	void findOccupiedCells()
	{
		occupiedCellNumbers = new int[transform.length];
		occupiedCellNumbers[0] = transform.position.y * 6 + transform.position.x;

		if (transform.axis.equals("Vertical") )
		{
			for(int i = 1; i < transform.length; i++)
			{
				occupiedCellNumbers[i] = occupiedCellNumbers[i-1] + 6;
			}
		}
		else if (transform.axis.equals("Horizontal") )
		{
			for(int i = 1; i < transform.length; i++)
			{
				occupiedCellNumbers[i] = occupiedCellNumbers[i-1] + 1;
			}
		}

		findPivotPointsOfOccupiedCells();
	}

	private void findPivotPointsOfOccupiedCells()
	{
		occupiedTransforms = new Transform[transform.length];
		occupiedTransforms[0] = new Transform(transform.position.x, transform.position.y, 1, transform.direction);

		for(int i = 1; i< transform.length;i++)
		{
			occupiedTransforms[i] = new Transform(occupiedCellNumbers[i] % 6, occupiedCellNumbers[i] / 6, 1, transform.direction);
		}
	}

	public Transform[] getOccupiedTransforms()
	{
		return occupiedTransforms;
	}

	public int[] getOccupiedCells() // for those want to use cell numbers and calculate pivot points
	{
		return occupiedCellNumbers;
	}
}