package source.controller;

import source.model.*;

public class VehicleController extends Controller
{
   public static VehicleController instance;

   private Map map;
   private Vehicle selectedVehicle;
   private SoundManager soundManager;
   private int numberOfMoves;
   private boolean changed = false;

   private enum CONTROL
   {
      SLIDE, KEYBOARD
   }

   private CONTROL currentControl;

   private double[] vehicleOriginPosition;
   private int[] mouseOriginPosition;

   VehicleController()
   {
      instance = this;
      numberOfMoves = 0;
      soundManager = GameEngine.instance.soundManager;
      currentControl = CONTROL.SLIDE;

      mouseOriginPosition = new int[2];
      vehicleOriginPosition = new double[2];
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

      if ( PowerUpManager.instance.isPowerUpActive() )
      {
         return;
      }

      if ( currentControl == CONTROL.SLIDE )
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               setSelectedVehicle(temp);
               vehicleOriginPosition[0] = selectedVehicle.transform.position.x;
               vehicleOriginPosition[1] = selectedVehicle.transform.position.y;
               mouseOriginPosition = Input.getMousePosition();
            }
         }

         if ( Input.getMouseButtonReleased(0) )
         {
            if ( selectedVehicle != null )
            {
               int gridPositionX = (int) ( selectedVehicle.transform.position.x + 0.5 );
               int gridPositionY = (int) ( selectedVehicle.transform.position.y + 0.5 );
               System.out.println("Moved the vehicle to the point");
               selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
               MapController.instance.updateMap(map.getGameObjects());
            }

            selectedVehicle = null;
         }

         //Moving while holding the mouse down
         if ( selectedVehicle != null )
         {
            if ( selectedVehicle.transform.axis.equals("Horizontal") )
            {
               int gridPositionX = (int)(vehicleOriginPosition[0] + ((Input.getMousePosition()[0] - mouseOriginPosition[0] ) / (double) 60 ));
               int gridPositionY = (int)vehicleOriginPosition[1];

               int testPositionX = gridPositionX;
               int testPositionY = gridPositionY;

               int mouseDifference = (Input.getMousePosition()[0] - mouseOriginPosition[0] );

               if ( mouseDifference > 0) //Right
               {
                  testPositionX = gridPositionX + selectedVehicle.transform.length;
                  testPositionY = gridPositionY;
               }
               else if (mouseDifference < 0) //Left
               {
                  testPositionX = gridPositionX;
                  testPositionY = gridPositionY;
               }

               if (mouseDifference != 0)
               {
                  System.out.println("TEST POSITION: " + testPositionX + "," + testPositionY);
                  if ( testPositionX >= 0 && testPositionX < map.getMapSize())
                  {
                     System.out.print("RESULT: " + map.getGrid()[testPositionY][testPositionX] + "\n");
                     if ( map.getGrid()[testPositionY][testPositionX].equals("Space") )
                     {
                        selectedVehicle.transform.position.x = vehicleOriginPosition[0] + (mouseDifference / (double)60);
                     }
                  }
               }
            }
            else // Vertical
            {

            }
         }
      }

      else if ( currentControl == CONTROL.KEYBOARD )
      {
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
            //System.out.println("Selected vehicle: NOT NULL");
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
               changed = true;
            }
         }
      }
   }

   private void setSelectedVehicle(Vehicle _selectedVehicle)
   {
      if ( _selectedVehicle != selectedVehicle )
      {
         if ( changed )
         {
            numberOfMoves++;
            GameManager.instance.autoSave(numberOfMoves);
            changed = false;
         }
      }
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

            if ( map.getGrid()[(int) selectedVehicle.transform.position.y][(int) selectedVehicle.transform.position.x + moveCheck].equals("Space") )
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

            if ( map.getGrid()[( (int) selectedVehicle.transform.position.y ) + moveCheck][(int) selectedVehicle.transform.position.x].equals("Space") )
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
