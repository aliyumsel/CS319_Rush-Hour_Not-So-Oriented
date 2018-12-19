package source.model;

import interfaces.Drawable;

import java.awt.*;

public class GameObject implements Drawable
{
   public Transform transform;
   private int[] occupiedCellNumbers;
   private Transform[] occupiedTransforms;
   boolean isBlackedOut; //bunu highlight olayi icin kullanmamiz gerekebilir


   GameObject(int x, int y, int length, String direction)
   {
      transform = new Transform(x, y, length, direction);
      findOccupiedCells();
      isBlackedOut = false;
   }

   void findOccupiedCells()
   {
      occupiedCellNumbers = new int[transform.length];
      occupiedCellNumbers[0] = transform.position.gridY * 8 + transform.position.gridX;

      if ( transform.axis.equals("Vertical") )
      {
         for ( int i = 1; i < transform.length; i++ )
         {
            occupiedCellNumbers[i] = occupiedCellNumbers[i - 1] + 8;
         }
      }
      else if ( transform.axis.equals("Horizontal") )
      {
         for ( int i = 1; i < transform.length; i++ )
         {
            occupiedCellNumbers[i] = occupiedCellNumbers[i - 1] + 1;
         }
      }

      findPivotPointsOfOccupiedCells();
   }

   private void findPivotPointsOfOccupiedCells()
   {
      occupiedTransforms = new Transform[transform.length];
      occupiedTransforms[0] = new Transform((int) transform.position.gridX, (int) transform.position.gridY, 1, transform.direction);

      for ( int i = 1; i < transform.length; i++ )
      {
         occupiedTransforms[i] = new Transform(occupiedCellNumbers[i] % 8, occupiedCellNumbers[i] / 8, 1, transform.direction);
      }
   }

   public Transform[] getOccupiedTransforms()
   {
      return occupiedTransforms;
   }

   public int[] getOccupiedCells() // for those want to use cell numbers and calculate pivot points
   {
      return occupiedCellNumbers;
   }

   public String getType()
   {
      return "";
   }

   @Override
   public void draw(Graphics2D graphics)
   {

   }


   public void updateImages()
   {
   }

   public void showBlackForeground()
   {
      isBlackedOut = true;
   }

   public void hideBlackForeground()
   {
      isBlackedOut = false;
   }
}