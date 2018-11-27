package source.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import interfaces.Drawable;

public class Vehicle extends GameObject implements Drawable
{
   private String type; // we may not need this
   public boolean isMoving; // we may not need this
   private boolean player;
   private int drawIndex;
   private int threshold;
   private int verticalMoveAxis;
   private int horizontalMoveAxis;
   private int drawingIndexForMoving;
   //private BufferedImage vehicle;
   private BufferedImage[] vehicleImages;

   // AffineTransform at;
   public Vehicle()
   {
      super();
   }

   public Vehicle(int x, int y, int length, String direction, boolean player, boolean special, String theme)
   {
      super(x, y, length, direction);
      this.player = player;
      isMoving = false;
      this.drawingIndexForMoving = 75;
      verticalMoveAxis = 0;
      horizontalMoveAxis = 0;
      vehicleImages = new BufferedImage[length];
      String path = "src/image/theme_" + theme + "/";
      int carType = (int) ( Math.random() * 2 );

      if ( !player && length == 2 )
      {
         path += "small";
      }
      else if ( !player && length == 3 )
      {
         path += "large";
      }
      else if ( player && !special )
      {
         path += "player";
      }
      else if ( player )
      {
         path += "police";
      }

      if ( direction.equals("Upwards") || direction.equals("Right") )
      {
         for ( int i = 0; i < length; i++ )
         {
            if ( !player )
            {
               vehicleImages[i] = LoadImage(path + carType + "-" + i + ".png");
            }
            else
            {
               vehicleImages[i] = LoadImage(path + "-" + i + ".png");
            }
         }
      }
      else
      {
         for ( int i = 0; i < length; i++ )
         {
            if ( !player )
            {
               vehicleImages[i] = LoadImage(path + carType + "-" + ( length - i - 1 ) + ".png");
            }
            else
            {
               vehicleImages[i] = LoadImage(path + "-" + ( length - i - 1 ) + ".png");
            }
         }
      }



		/*
      if (!player && length == 2)
			vehicle = LoadImage("src/image/Car.png");
		else if (!player && length == 3)
			vehicle = LoadImage("src/image/Truck.png");
		else if (player)
			vehicle = LoadImage("src/image/Player.png");
		*/
   }

   public void move(int moveAxis)
   {
      if ( !isMoving )
      {
         if ( transform.axis.equals("Vertical") )
         {
            transform.position.y -= moveAxis;
            verticalMoveAxis = moveAxis; // if moveaxis == -1 vehicle goes downwards
         }
         else if ( transform.axis.equals("Horizontal") )
         {
            transform.position.x += moveAxis;
            horizontalMoveAxis = moveAxis; // if moveaxis == -1 vehicle goes left
         }
         findOccupiedCells();
      }
   }

   public String getType()
   {
      return type;
   }

   void setType(String type)
   {
      this.type = type;
   }

   public boolean isPlayer()
   {
      return player;
   }

   @Override
   public void draw(Graphics graphics)
   {
      AffineTransform at;
      Graphics2D graphics2d = (Graphics2D) graphics;
      if ( drawingIndexForMoving <= 0 )
      {
         drawingIndexForMoving = 75;
         isMoving = false;
         verticalMoveAxis = 0;
         horizontalMoveAxis = 0;
      }

      if ( isMoving && ( verticalMoveAxis == -1 || horizontalMoveAxis == 1 ) )
      {
         drawIndex = transform.length - 1;
         threshold = -1;
      }
      else
      {
         drawIndex = 0;
         threshold = transform.length;
      }
      while ( drawIndex != threshold )
      {

         if ( isMoving )
         {
            System.out.println(occupiedTransforms[drawIndex].position.x + " , " + occupiedTransforms[drawIndex].position.y);
         }
         if ( isMoving && transform.axis.equals("Vertical") && verticalMoveAxis == -1 )
         {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[drawIndex].position.x * 75, occupiedTransforms[drawIndex].position.y * 75 - drawingIndexForMoving);
            drawingIndexForMoving -= 2;
         }
         else if ( isMoving && transform.axis.equals("Vertical") && verticalMoveAxis == 1 )
         {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[drawIndex].position.x * 75, occupiedTransforms[drawIndex].position.y * 75 + drawingIndexForMoving);
            drawingIndexForMoving -= 2;
         }
         else if ( isMoving && transform.axis.equals("Horizontal") && horizontalMoveAxis == -1 )
         {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[drawIndex].position.x * 75 + drawingIndexForMoving, occupiedTransforms[drawIndex].position.y * 75);
            drawingIndexForMoving -= 2;
         }
         else if ( isMoving && transform.axis.equals("Horizontal") && horizontalMoveAxis == 1 )
         {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[drawIndex].position.x * 75 - drawingIndexForMoving, occupiedTransforms[drawIndex].position.y * 75);
            drawingIndexForMoving -= 2;
         }
         else
         {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[drawIndex].position.x * 75, occupiedTransforms[drawIndex].position.y * 75);
         }


         if ( transform.direction.equals("Upwards") )
         {
            graphics2d.drawImage(vehicleImages[drawIndex], at, null);
         }
         else if ( transform.direction.equals("Downwards") )
         {
            at.rotate(Math.toRadians(180), vehicleImages[drawIndex].getWidth() / 2.0, vehicleImages[drawIndex].getHeight() / 2.0);
            graphics2d.drawImage(vehicleImages[drawIndex], at, null);
         }
         else if ( transform.direction.equals("Left") )
         {
            at.rotate(Math.toRadians(90), vehicleImages[drawIndex].getWidth() / 2.0, vehicleImages[drawIndex].getHeight() / 2.0);
            graphics2d.drawImage(vehicleImages[drawIndex], at, null);
         }
         else
         {
            at.rotate(Math.toRadians(270), vehicleImages[drawIndex].getWidth() / 2.0, vehicleImages[drawIndex].getHeight() / 2.0);
            graphics2d.drawImage(vehicleImages[drawIndex], at, null);
         }
         if ( isMoving && ( verticalMoveAxis == -1 || horizontalMoveAxis == 1 ) )
         {
            drawIndex--;
         }
         else
         {
            drawIndex++;
         }
      }


      // System.out.println(transform.position.x + " " + transform.position.y);
   }

   private BufferedImage LoadImage(String FileName)
   {
      BufferedImage image = null;
      try
      {
         image = ImageIO.read(new File(FileName));
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      return image;
   }
}
