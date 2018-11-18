package source.view;
import source.controller.GameEngine;
import java.util.Timer;

public class Main
{
	//Main executable olcak gui ve engine i bagliyo
	public static void main(String[] args)
	{
		GameEngine gameEngine = new GameEngine();
		GuiPanelManager guiManager = new GuiPanelManager();
		guiManager.setListeners();
		gameEngine.setGUI(guiManager);

		Timer timer = new Timer();
		timer.schedule(gameEngine, 0, 1000 / 60);
	}

}