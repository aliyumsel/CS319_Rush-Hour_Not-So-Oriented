package source.model;

import source.controller.ThemeManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Vehicle extends GameObject// implements Drawable
{
   private String type; // we may not need this
   public boolean isMoving; // we may not need this
   private boolean player;
   private int gridPixelSize = 60;
   private boolean special = false;
   private BufferedImage image;
   private BufferedImage blackedOutImage;
   public boolean isSliding = false;
   public double velocity;

   Vehicle(int x, int y, int length, String direction, boolean player) //theme i sil burdan
   {
      super(x, y, length, direction);
      this.player = player;
      isMoving = false;
      this.special = special;
      velocity = 0.05;
      updateImages();
   }

   public void move(double moveAxis)
   {
      if ( transform.axis.equals("Vertical") )
      {
         transform.position.y -= moveAxis;
         if (!isSliding) {
            transform.position.gridY = (int) ((transform.position.y + 0.1) / 1); //değerler double a döndüğü için direk typecast etmek mantıklı / 1 yaptım olay anlaşılsın diye
         }
      }
      else if ( transform.axis.equals("Horizontal") )
      {
         transform.position.x += moveAxis;
         if (!isSliding) {
            transform.position.gridX = (int) ((transform.position.x + 0.1) / 1);
         }
      }
      findOccupiedCells();
   }

   public void slideToPoint(int x, int y) // tam sayılara gitmesini istiyoruz, eğer başka bir feature gelirse double la değiştirin
   {
      if ( transform.axis.equals("Vertical") )
      {
         if (y == (int)(transform.position.y) && Math.abs(transform.position.y-y) <= 0.1)
         {
            transform.position.y = (int)(transform.position.y+0.1);
            isSliding = false;
         }
         else if (y > transform.position.y)
         {
            move(-velocity);
         }
         else if (y < transform.position.y)
         {
            move(velocity);
         }
      }
      else if ( transform.axis.equals("Horizontal") )
      {
         if (x == (int)transform.position.x && Math.abs(transform.position.x-x) <=0.1)
         {
            transform.position.x = (int)(transform.position.x+0.1);
            isSliding = false;
         }
         else if (x > transform.position.x)
         {
            move(velocity);
         }
         else if (x < transform.position.x)
         {
            move(-velocity);
         }
      }
   }
   @Override
   public void updateImages()
   {
      if ( !player && transform.length == 2 )
      {
         image = ThemeManager.instance.getShortVehicleImage();
      }
      else if ( !player && transform.length == 3 )
      {
         image = ThemeManager.instance.getLongVehicleImage();
      }
      else if ( player && !this.special )
      {
         image = ThemeManager.instance.getPlayerImage();
      }
      else if ( player )
      {
         image = ThemeManager.instance.getSpecialPlayerImage();
      }
   }

   public void moveToPoint(int x, int y)
   {
      transform.position.gridX = x;
      transform.position.gridY = y;
      findOccupiedCells();
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

      at = AffineTransform.getTranslateInstance(transform.position.x * gridPixelSize, transform.position.y * gridPixelSize);

      if ( transform.direction.equals("Upwards") )
      {
         graphics2d.drawImage(image, at, null);
      }
      else if ( transform.direction.equals("Downwards") )
      {
         at.rotate(Math.toRadians(180), image.getWidth() / 2.0, image.getHeight() / 2.0);
         graphics2d.drawImage(image, at, null);
      }
      else if ( transform.direction.equals("Left") )
      {
         at.rotate(Math.toRadians(90), image.getWidth() / 2.0, image.getHeight() / 2.0 / transform.length);
         at.translate(0, -60 * ( transform.length - 1 ));
         graphics2d.drawImage(image, at, null);
      }
      else
      {
         at.rotate(Math.toRadians(270), image.getWidth() / 2.0, image.getHeight() / 2.0 / transform.length);
         graphics2d.drawImage(image, at, null);
      }
   }
}
