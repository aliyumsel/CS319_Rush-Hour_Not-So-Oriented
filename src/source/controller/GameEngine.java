package source.controller;
import java.util.TimerTask;

public class GameEngine extends TimerTask
{
    public void run()
    {
        if (Input.getMouseButtonPressed(0))
        {
            System.out.println( "Gonna pick the vehicle at: " + Input.getMousePositionX() + ", " + Input.getMousePositionY());
        }

        if (Input.getKeyPressed("w"))
        {
            System.out.println( "Gonna move up" );
        }

        if (Input.getKeyPressed("a"))
        {
            System.out.println( "Gonna move left" );
        }

        if (Input.getKeyPressed("s"))
        {
            System.out.println( "Gonna move down" );
        }

        if (Input.getKeyPressed("d"))
        {
            System.out.println( "Gonna move right" );
        }

        Input.reset();
    }
}
