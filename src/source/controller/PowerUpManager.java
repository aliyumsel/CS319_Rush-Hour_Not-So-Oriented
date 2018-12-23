package source.controller;

import source.model.Car;
import source.model.Obstacle;
import source.model.Vehicle;

/**
 * It is class for managing power ups.
 */
public class PowerUpManager extends Controller
{
   public static PowerUpManager instance;

   public enum PowerUp
   {
      Space, Shrink
   }

   private boolean spaceActive;
   private boolean shrinkActive;

   private Vehicle vehicleToShrink;
   private int[] vehicleToShrinkCells;

   private Obstacle obstacleToRemove;
   private int obstacleToRemoveX;
   private int obstacleToRemoveY;

   private int poofDuration;
   private int counter = 0;
   private boolean shouldCount;

   PowerUpManager()
   {
      instance = this;
      obstacleToRemove = null;
      shouldCount = false;
      obstacleToRemoveX = -1;
      obstacleToRemoveY = -1;
      vehicleToShrinkCells = null;
      poofDuration = 27;
   }

   /**
    * Updates the power-up activities.
    */
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
                  SoundManager.instance.shrinkSound();

                  vehicleToShrink = temp;
                  vehicleToShrinkCells = vehicleToShrink.getOccupiedCells();

//                  MapController.instance.removeGameObject(temp);
//                  Vehicle newVehicle = new Car(temp);
//                  MapController.instance.addGameObject(newVehicle);
//                  MapController.instance.updateMap();
//                  GameManager.instance.autoSave();

                  deactivateShrink();
                  shouldCount = true;

                  //this decrement method will be put inside the game manager
                  PlayerManager.instance.decrementRemainingShrinkPowerup();
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
               SoundManager.instance.poofSound();
               obstacleToRemove = temp;
               obstacleToRemoveX = obstacleToRemove.transform.position.gridX;
               obstacleToRemoveY = obstacleToRemove.transform.position.gridY;
               deactivateSpace();
               shouldCount = true;

               //this decrement method will be put inside the game manager
               PlayerManager.instance.decrementRemainingSpacePowerup();
            }
         }
      }

      if ( shouldCount )
      {
         counter++;
         System.out.println("Counter: " + counter);
      }

      if (counter >= ( poofDuration * ( 2 / 3f ) ))
      {
         //shrink the gameobject
         if ( vehicleToShrink != null)
         {
            MapController.instance.removeGameObject(vehicleToShrink);
            Vehicle newVehicle = new Car(vehicleToShrink);
            MapController.instance.addGameObject(newVehicle);
            MapController.instance.updateMap();
            GameManager.instance.autoSave();
            vehicleToShrink = null;
         }

         //remove game object
         if ( obstacleToRemove != null)
         {
            System.out.println("Removed game object");
            MapController.instance.removeGameObject(obstacleToRemove);
            MapController.instance.updateMap();
            GameManager.instance.autoSave();
            obstacleToRemove = null;
         }
      }

      if ( counter >= poofDuration )
      {
         System.out.println("Stopped Counter");
         counter = 0;
         shouldCount = false;
         obstacleToRemoveX = -1;
         obstacleToRemoveY = -1;
         vehicleToShrinkCells = null;
      }
   }


   /**
    * Toggles the power ups.
    * @param powerUp The desired power-up.
    */
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

   /**
    * Getter for the obstacle that is going to be removed from x axis
    * @return obstacle to be removed
    */
   public int getObstacleToRemoveX()
   {
      return obstacleToRemoveX;
   }


   /**
    * Getter for the obstacle that is going to be removed from y axis
    * @return obstacle to be removed
    */
   public int getObstacleToRemoveY()
   {
      return obstacleToRemoveY;
   }


   /**
    * Getter for the vehicle that is going to shrink.
    * @return the vehicle.
    */
   public int[] getVehicleToShrinkCells()
   {
      return vehicleToShrinkCells;
   }


   /**
    * Getter for the current count.
    * @return the number of count.
    */
   public int getCurrentCount()
   {
      return counter;
   }


   /**
    * Getter for the poof duration
    * @return the duration of the "poof" effect
    */
   public int getPoofDuration()
   {
      return poofDuration;
   }


   /**
    * Deactivates the power-ups.
    */
   void deactivatePowerUps()
   {
      deactivateShrink();
      deactivateSpace();
   }


   /**
    * It initializes the theme of space.
    */
   private void initializeSpace()
   {
      System.out.println("Activated Space");
      spaceActive = true;
      deactivateShrink();
      MapController.instance.highlightObstacles();
   }


   /**
    * It initializes the theme of shrink.
    */
   private void initializeShrink()
   {
      System.out.println("Activated Shrink");
      shrinkActive = true;
      deactivateSpace();
      MapController.instance.highlightLongs();
   }


   /**
    * Deactivates the space theme.
    */
   private void deactivateSpace()
   {
      System.out.println("Deactivated Space");
      spaceActive = false;
      MapController.instance.clearHighlights();
   }


   /**
    * Deactivates the shrink theme.
    */
   private void deactivateShrink()
   {
      System.out.println("Deactivated Shrink");
      shrinkActive = false;
      MapController.instance.clearHighlights();
   }


   /**
    * Checks whether the power up is active or not.
    * @return true if active, false otherwise.
    */
   public boolean isPowerUpActive()
   {
      return ( shrinkActive || spaceActive );
   }
}
