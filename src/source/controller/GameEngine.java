package source.controller;

import java.io.FileNotFoundException;
import java.util.TimerTask;

import source.view.*;

public class GameEngine extends TimerTask {
	public static GameEngine instance; // extremely simple singleton to access gameEngine with ease

	private SoundManager soundManager;
	public VehicleController vehicleController;
	public GameManager gameManager;
	public MapController mapController;

	public GameEngine() {
		instance = this; // extremely simple singleton to access gameEngine with ease

      soundManager = new SoundManager();
		vehicleController = new VehicleController();
		gameManager = new GameManager();
		mapController = new MapController();
	}

	// this method is executed over and over from main
	// calls the update method of other classes that needs to be updated
	public void run()
   {
      gameManager.Update();
		vehicleController.Update();

      Input.reset();
	}
}
