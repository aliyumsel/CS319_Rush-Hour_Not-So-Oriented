package source.controller;

import source.model.Car;
import source.model.Obstacle;
import source.model.Vehicle;

public class PowerUpManager extends Controller
{
   public static PowerUpManager instance;

   public enum PowerUp
   {
      Space, Shrink
   }

   private boolean spaceActive;
   private boolean shrinkActive;

   PowerUpManager()
   {
      instance = this;
   }

   public void update()
   {
      if ( shrinkActive )
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               if ( temp.transform.length == 3 )
               {
                  System.out.println("Selected truck: " + temp.transform.position.x + ", " + temp.transform.position.y);
                  MapController.instance.removeGameObject(temp);

                  //May move this to MapController or smt
                  //Vehicle newVehicle = new Vehicle(temp, 2);
                  Vehicle newVehicle = new Car(temp);
                  MapController.instance.addGameObject(newVehicle);

                  MapController.instance.updateMap();
                  deactivateShrink();
                  //this decrement method will be put inside the game manager
                  GameEngine.instance.playerManager.decrementRemainingShrinkPowerup();
               }
            }
         }
      }
      else if ( spaceActive )
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            Obstacle temp = MapController.instance.getObstacleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               MapController.instance.removeGameObject(temp);
               MapController.instance.updateMap();
               deactivateSpace();
               //this decrement method will be put inside the game manager
               GameEngine.instance.playerManager.decrementRemainingSpacePowerup();
            }
         }
      }
   }

   public void togglePowerUp(PowerUp powerUp)
   {
      if ( powerUp == PowerUp.Space )
      {
         if ( spaceActive )
         {
            deactivateSpace();
         }
         else
         {
            initializeSpace();
         }
      }
      else if ( powerUp == PowerUp.Shrink )
      {
         if ( shrinkActive )
         {
            deactivateShrink();
         }
         else
         {
            initializeShrink();
         }
      }
   }

//   private void initializePowerUp(PowerUp powerUp)
//   {
//      if ( powerUp == PowerUp.Space )
//      {
//         initializeSpace();
//      }
//      else if ( powerUp == PowerUp.Shrink )
//      {
//         initializeShrink();
//      }
//   }

   private void initializeSpace()
   {
      System.out.println("Activated Space");
      spaceActive = true;
      deactivateShrink();
   }

   private void initializeShrink()
   {
      System.out.println("Activated Shrink");
      shrinkActive = true;
      deactivateSpace();
   }

   private void deactivateSpace()
   {
      System.out.println("Deactivated Space");
      spaceActive = false;
   }

   private void deactivateShrink()
   {
      System.out.println("Deactivated Shrink");
      shrinkActive = false;
   }

   boolean isPowerUpActive()
   {
      return ( shrinkActive || spaceActive );
   }
}
