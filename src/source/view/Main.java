package source.view;

import source.controller.GameEngine;
import java.util.Timer;

public class Main {

	public static void main(String[] args)
	{
		GameEngine gameEngine = new GameEngine();
		GuiPanelManager guiManager = new GuiPanelManager();
		gameEngine.setGUI(guiManager);

		Timer timer = new Timer();
		timer.schedule(gameEngine, 0, 1000 / 60);
	}
}