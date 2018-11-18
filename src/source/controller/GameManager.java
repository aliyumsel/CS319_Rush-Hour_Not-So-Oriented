package source.controller;
import interfaces.Updatable;

public class GameManager implements Updatable
{
    static GameManager instance;
    
    

    private boolean mapFinished = false;

    public GameManager()
    {
    	if (instance == null)
    	{    		
    		instance = this;
    	}
    }

    public void Update()
    {
        if (mapFinished)
        {
            System.out.println("Map is finished");
        }
    }

    void endMap()
    {
        mapFinished = true;
    }
}