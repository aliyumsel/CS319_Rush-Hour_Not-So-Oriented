package source.model;

import source.controller.ThemeManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Obstacle extends GameObject
{
   private BufferedImage image;

   public Obstacle(int x, int y, int length, String direction)
   {
      super(x, y, length, direction);
      updateImages();
   }

   @Override
   public void draw(Graphics graphics)
   {
      Image scaledImage = image.getScaledInstance(58, 58, Image.SCALE_DEFAULT);
      graphics.drawImage(scaledImage, (int) transform.position.x * 60, (int) transform.position.y * 60, null);
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
   }
}
