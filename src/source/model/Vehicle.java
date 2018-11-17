package source.model;

public class Vehicle extends GameObject
{
    private String type; //we may not need this
    private boolean isChosen; //we may not need this
    private boolean player;

    public Vehicle()
    {
        super();
    }

    public Vehicle(int x, int y, int length, String direction, boolean player)
    {
        super(x, y, length, direction);
        this.player = player;
    }

    public void move(int moveAxis) {

        if (transform.axis.equals("Vertical"))
        {
            transform.position.y -= moveAxis;
        }
        else if (transform.axis.equals("Horizontal"))
        {
            transform.position.x += moveAxis;
        }
        findOccupiedCells();
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    
    public boolean isPlayer()
    {
    	return player;
    }
}
