package source.controller;
import interfaces.Updatable;
import source.view.GuiPanelManager;

public class GameManager implements Updatable
{
    static GameManager instance;

    private boolean mapFinished = false;
    public int currentLevel;

    GameManager()
    {
        instance = this;
    }

    public void Update()
    {

    }

    void endMap()
    {
       mapFinished = true;
       currentLevel++;
       mapFinished = false;
       System.out.println("level to be loaded: " + currentLevel);
       // pop up window for level change
       GuiPanelManager.instance.setPanelVisible(currentLevel);
    }
}
