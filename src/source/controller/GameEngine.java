package source.controller;

import java.util.ArrayList;


/**
 * GameEngine class is responsible for handling the game loop and every class that is related to the game logic.
 */
public class GameEngine
{
   public static GameEngine instance;
   private ArrayList<Controller> controllers;
   public ThemeManager themeManager;
   public SoundManager soundManager;
   public VehicleController vehicleController;
   public MapController mapController;
   public PlayerManager playerManager;
   public GameManager gameManager;
   public PowerUpManager powerUpManager;


   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   public GameEngine()
   {
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

      for ( Controller controller : controllers )
      {
         controller.start();
      }
   }


   /**
    * Calls the update methods of every Manager object that is inside managers array.
    */
   // this method is executed over and over from main
   // calls the update method of other classes that needs to be updated
   public void run()
   {
      for ( Controller controller : controllers )
      {
         controller.update();
      }

      Input.reset();
   }

}
