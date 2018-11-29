package source.controller;

import source.model.*;

public class VehicleController extends Controller
{
   public static VehicleController instance;

   //private MapController mapController;

   private Map map;
   private Vehicle selectedVehicle;
   private SoundManager soundManager;
   private int numberOfMoves;

   VehicleController()
   {
      instance = this;
      numberOfMoves = 0;
      soundManager = GameEngine.instance.soundManager;
   }

   public void setMap(Map _map)
   {
      map = _map;
   }

   //executed every frame write the functionality needed to here
   public void update()
   {
      if ( !GameManager.instance.isGameActive )
      {
         return;
      }

      if ( Input.getMouseButtonPressed(0) )
      {
         Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

         if ( temp != null )
         {
            setSelectedVehicle(temp);
            System.out.println("Selected vehicle: " + selectedVehicle.transform.position.x + ", " + selectedVehicle.transform.position.y);
         }
      }

      if ( selectedVehicle != null )
      {
         if ( selectedVehicle.isPlayer() && MapController.instance.isPlayerAtExit() )
         {
            GameManager.instance.endMap();
            selectedVehicle = null;
            return;
         }

         boolean moved = false;
         if ( Input.getKeyPressed("w") )
         {
            moved = tryMove("Upwards");
         }
         else if ( Input.getKeyPressed("a") )
         {
            moved = tryMove("Left");
         }
         else if ( Input.getKeyPressed("s") )
         {
            moved = tryMove("Downwards");
         }
         else if ( Input.getKeyPressed("d") )
         {
            moved = tryMove("Right");
         }

         if ( moved )
         {
            System.out.println("Moved");
            MapController.instance.updateMap(map.getGameObjects());
            numberOfMoves++;
            GameManager.instance.autoSave(numberOfMoves);
         }
      }
   }

   private void setSelectedVehicle(Vehicle _selectedVehicle)
   {
      selectedVehicle = _selectedVehicle;
      if ( soundManager == null )
      {
         return;
      }
      soundManager.vehicleHorn(selectedVehicle.getType());
   }

   private boolean tryMove(String direction)
   {
      System.out.println("In here??");
      String vehicleAxis = selectedVehicle.transform.axis;
      int moveAmount = 0;
      int moveCheck = 0;
      if ( !selectedVehicle.isMoving )
      {
         if ( vehicleAxis.equals("Horizontal") )
         {
            if ( direction.equals("Left") )
            {
               moveAmount = -1;
               moveCheck = -1;
            }
            else if ( direction.equals("Right") )
            {
               moveAmount = 1;
               moveCheck = selectedVehicle.transform.length;
            }
            if ( selectedVehicle.transform.position.x + moveCheck < 0 || selectedVehicle.transform.position.x + moveCheck >= map.getMapSize() )
            {
               return false;
            }

            if ( map.getGrid()[selectedVehicle.transform.position.y][selectedVehicle.transform.position.x + moveCheck].equals("Space") )
            {
               selectedVehicle.move(moveAmount);
               selectedVehicle.isMoving = true;
               return true;
            }
         }
         if ( vehicleAxis.equals("Vertical") )
         {
            if ( direction.equals("Upwards") )
            {
               moveAmount = 1;
               moveCheck = -1;
            }
            else if ( direction.equals("Downwards") )
            {
               moveAmount = -1;
               moveCheck = selectedVehicle.transform.length;
            }
            if ( selectedVehicle.transform.position.y + moveCheck < 0 || selectedVehicle.transform.position.y + moveCheck >= map.getMapSize() )
            {
               return false;
            }

            if ( map.getGrid()[( selectedVehicle.transform.position.y ) + moveCheck][selectedVehicle.transform.position.x].equals("Space") )
            {
               selectedVehicle.move(moveAmount);
               selectedVehicle.isMoving = true;
               return true;
            }
         }
      }
      return false;
   }

   public int getNumberOfMoves()
   {
      return numberOfMoves;
   }

   void setNumberOfMoves(int _moves)
   {
      numberOfMoves = _moves;
   }
}
