package source.controller;

import java.util.TimerTask;

public class GameEngine extends TimerTask
{
   public static GameEngine instance;

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
