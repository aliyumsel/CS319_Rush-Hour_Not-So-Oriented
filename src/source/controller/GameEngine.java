package source.controller;

import java.io.FileNotFoundException;
import java.util.TimerTask;

import source.view.*;

public class GameEngine extends TimerTask {
	public static GameEngine instance; // extremely simple singleton to access gameEngine with ease

	private GuiPanelManager guiPanelManager;
	private SoundManager soundManager;
	private MapExtractor mapExtractor;
	private Map map;
	private VehicleController vehicleController;
	public GameManager gameManager;

	public GameEngine() {
		instance = this; // extremely simple singleton to access gameEngine with ease

		try {
			mapExtractor = new MapExtractor(2);
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map = mapExtractor.getMap();
		vehicleController = new VehicleController(map);
		gameManager = new GameManager();
		soundManager = new SoundManager();
		soundManager.background(); //theme song is started when the game is intialized
	}

	public Map getMap()
   {
	   return map;
	}

	public void setGUI(GuiPanelManager guiManager) {
		guiPanelManager = guiManager;
	}

	// this method is executed over and over from main
	// calls the update method of other classes that needs to be updated

	public void run() {

		vehicleController.Update();
		gameManager.Update();

		guiPanelManager.getGamePanel().updatePanel();

      Input.reset();
	}
}
