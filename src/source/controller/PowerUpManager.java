package source.controller;

import source.model.Car;
import source.model.Obstacle;
import source.model.Vehicle;

public class PowerUpManager extends Controller
{
   private static PowerUpManager instance = null;

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

   private PowerUpManager()
   {
      obstacleToRemove = null;
      shouldCount = false;
      obstacleToRemoveX = -1;
      obstacleToRemoveY = -1;
      vehicleToShrinkCells = null;
      poofDuration = 27;
   }

   public static PowerUpManager getInstance()
   {
      if(instance == null) {
         instance = new PowerUpManager();
      }
      return instance;
   }

   public void update()
   {
      if ( shrinkActive )
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            Vehicle temp = MapController.getInstance().getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               if ( temp.transform.length == 3 )
               {
                  SoundManager.getInstance().shrinkSound();

                  vehicleToShrink = temp;
                  vehicleToShrinkCells = vehicleToShrink.getOccupiedCells();

                  deactivateShrink();
                  shouldCount = true;

                  //this decrement method will be put inside the game manager
                  PlayerManager.getInstance().decrementRemainingShrinkPowerup();
               }
            }
         }
      }
      else if ( spaceActive )
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            Obstacle temp = MapController.getInstance().getObstacleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               SoundManager.getInstance().poofSound();
               obstacleToRemove = temp;
               obstacleToRemoveX = obstacleToRemove.transform.position.gridX;
               obstacleToRemoveY = obstacleToRemove.transform.position.gridY;
               deactivateSpace();
               shouldCount = true;

               //this decrement method will be put inside the game manager
               PlayerManager.getInstance().decrementRemainingSpacePowerup();
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
            MapController.getInstance().removeGameObject(vehicleToShrink);
            Vehicle newVehicle = new Car(vehicleToShrink);
            MapController.getInstance().addGameObject(newVehicle);
            MapController.getInstance().updateMap();
            GameManager.getInstance().autoSave();
            vehicleToShrink = null;
         }

         //remove game object
         if ( obstacleToRemove != null)
         {
            System.out.println("Removed game object");
            MapController.getInstance().removeGameObject(obstacleToRemove);
            MapController.getInstance().updateMap();
            GameManager.getInstance().autoSave();
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

   public int getObstacleToRemoveX()
   {
      return obstacleToRemoveX;
   }

   public int getObstacleToRemoveY()
   {
      return obstacleToRemoveY;
   }

   public int[] getVehicleToShrinkCells()
   {
      return vehicleToShrinkCells;
   }

   public int getCurrentCount()
   {
      return counter;
   }

   public int getPoofDuration()
   {
      return poofDuration;
   }

   void deactivatePowerUps()
   {
      deactivateShrink();
      deactivateSpace();
   }

   private void initializeSpace()
   {
      System.out.println("Activated Space");
      spaceActive = true;
      deactivateShrink();
      MapController.getInstance().highlightObstacles();
   }

   private void initializeShrink()
   {
      System.out.println("Activated Shrink");
      shrinkActive = true;
      deactivateSpace();
      MapController.getInstance().highlightLongs();
   }

   private void deactivateSpace()
   {
      System.out.println("Deactivated Space");
      spaceActive = false;
      MapController.getInstance().clearHighlights();
   }

   private void deactivateShrink()
   {
      System.out.println("Deactivated Shrink");
      shrinkActive = false;
      MapController.getInstance().clearHighlights();
   }

   public boolean isPowerUpActive()
   {
      return ( shrinkActive || spaceActive );
   }
}
