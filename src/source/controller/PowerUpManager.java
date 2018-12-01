package source.controller;

import source.model.Vehicle;

public class PowerUpManager extends Controller
{
   public enum PowerUp{Space, Shrink}
   private boolean spaceActive;
   private boolean shrinkActive;

   public void update()
   {
      if (spaceActive)
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               if (temp.transform.length == 3)
               {
                  System.out.println("Selected truck: " + temp.transform.position.x + ", " + temp.transform.position.y);
                  spaceActive = false;
               }
            }
         }
      }

      else if (shrinkActive)
      {
         if ( Input.getMouseButtonPressed(0) )
         {
            //Yeni method yazilcak MapController a
            //Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

//            if ( temp != null )
//            {
//               if (temp.transform.length == 3)
//               {
//                  System.out.println("Selected truck: " + temp.transform.position.x + ", " + temp.transform.position.y);
//                  spaceActive = false;
//               }
//            }
         }
      }
   }

   public void initializePowerUp(PowerUp powerUp)
   {
      if (powerUp == PowerUp.Space)
      {
         initializeSpace();
      }
      else if (powerUp == PowerUp.Shrink)
      {
         initializeShrink();
      }
   }

   private void initializeSpace()
   {
      spaceActive = true;
   }

   private void initializeShrink()
   {

   }
}
