package source.controller;

import source.model.Player;

import java.util.ArrayList;
import java.util.TimerTask;

public class GameEngine extends TimerTask
{
   public static GameEngine instance;

   private ArrayList<Controller> controllers;

   SoundManager soundManager;
   public VehicleController vehicleController;
   public MapController mapController;
   public PlayerManager playerManager;
   public GameManager gameManager;

   public GameEngine()
   {
      instance = this;

      soundManager = new SoundManager();
      mapController = new MapController();
      vehicleController = new VehicleController();
      playerManager = new PlayerManager();
      gameManager = new GameManager();

      controllers = new ArrayList<>();

      controllers.add(soundManager);
      controllers.add(mapController);
      controllers.add(vehicleController);
      controllers.add(playerManager);
      controllers.add(gameManager);

      for (Controller controller: controllers)
      {
         controller.start();
      }
   }

   // this method is executed over and over from main
   // calls the update method of other classes that needs to be updated
   public void run()
   {
      for ( Controller controller: controllers )
      {
         controller.update();
      }

      Input.reset();
   }
}
