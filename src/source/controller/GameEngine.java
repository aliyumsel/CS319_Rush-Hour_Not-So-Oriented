package source.controller;

import java.util.ArrayList;
import java.util.TimerTask;

public class GameEngine  {
    public static GameEngine instance;
    public int frameCount;
    private ArrayList<Controller> controllers;
    public ThemeManager themeManager;
    public SoundManager soundManager;
    public VehicleController vehicleController;
    public MapController mapController;
    public PlayerManager playerManager;
    public GameManager gameManager;
    public PowerUpManager powerUpManager;

    public GameEngine() {
        instance = this;
        themeManager = new ThemeManager();
        soundManager = new SoundManager();
        mapController = new MapController();
        powerUpManager = new PowerUpManager();
        vehicleController = new VehicleController();
        playerManager = new PlayerManager();
        gameManager = new GameManager();
        controllers = new ArrayList<>();

      controllers.add(themeManager);
      controllers.add(soundManager);
      controllers.add(mapController);
      controllers.add(vehicleController);
      controllers.add(playerManager);
      controllers.add(gameManager);
      controllers.add(powerUpManager);

        frameCount = 0;

        for (Controller controller : controllers) {
            controller.start();
        }
    }

    // this method is executed over and over from main
    // calls the update method of other classes that needs to be updated
    public void run() {
        for (Controller controller : controllers) {
            controller.update();
        }

        if (frameCount >= 60)
        {
            frameCount = 1;
        }
        else {
            frameCount++;
        }

        Input.reset();
    }

}
