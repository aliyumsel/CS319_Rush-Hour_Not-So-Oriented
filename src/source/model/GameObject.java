package source.model;

import interfaces.Drawable;

import java.awt.*;

public class GameObject implements Drawable
{
   public Transform transform;
   private int[] occupiedCellNumbers;
   Transform[] occupiedTransforms;

   GameObject()
   {
      transform = new Transform();
      findOccupiedCells();
   }

   GameObject(int x, int y, int length, String direction)
   {
      transform = new Transform(x, y, length, direction);
      findOccupiedCells();
   }

   void findOccupiedCells()
   {
      occupiedCellNumbers = new int[transform.length];
      occupiedCellNumbers[0] = (int)transform.position.y * 8 + (int)transform.position.x;

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
      occupiedTransforms[0] = new Transform((int)transform.position.x, (int)transform.position.y, 1, transform.direction);

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
   public void draw(Graphics graphics)
   {

   }


   public void updateImages(){}
}