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
      MapController.instance.highlightObstacles();
   }

   private void initializeShrink()
   {
      System.out.println("Activated Shrink");
      shrinkActive = true;
      deactivateSpace();
      MapController.instance.highlightLongs();
   }

   private void deactivateSpace()
   {
      System.out.println("Deactivated Space");
      spaceActive = false;
      MapController.instance.clearHighlights();
   }

   private void deactivateShrink()
   {
      System.out.println("Deactivated Shrink");
      shrinkActive = false;
      MapController.instance.clearHighlights();
   }

   public boolean isPowerUpActive()
   {
      return ( shrinkActive || spaceActive );
   }
}
