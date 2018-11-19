package source.controller;
import interfaces.Updatable;
import source.view.GuiPanelManager;

public class GameManager implements Updatable
{
    static GameManager instance;

    private boolean mapFinished = false;

    GameManager()
    {
        instance = this;
    }

    public void Update()
    {
        if (mapFinished)
        {
        	GameEngine.instance.level = 2;
        	GameEngine.instance.updateLevel();
        	GuiPanelManager.instance.updatePlayGamePanel();
        	mapFinished = false;
            System.out.println("Map is finished");
        }
    }

    void endMap()
    {
        mapFinished = true;
    }
}
