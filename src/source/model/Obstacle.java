package source.model;

import source.controller.ThemeManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Obstacle extends GameObject
{
   private BufferedImage image;
   private BufferedImage blackedOutImage;

   public Obstacle(int x, int y, int length, String direction)
   {
      super(x, y, length, direction);
      updateImages();
      isBlackedOut = true;
   }

   @Override
   public void draw(Graphics graphics)
   {
      if (isBlackedOut)
      {
         graphics.drawImage(blackedOutImage,(int) transform.position.x * 60, (int) transform.position.y * 60, null);
      }
      graphics.drawImage(image, (int) transform.position.x * 60, (int) transform.position.y * 60, null);
   }

   @Override
   public String getType()
   {
      return "OO";
   }

   @Override
   public void updateImages()
   {
      image = ThemeManager.instance.getObstacleImage();
     // blackedOutImage =
   }
}
