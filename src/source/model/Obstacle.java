package source.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Obstacle extends GameObject
{
//   public Obstacle()
//   {
//      super();
//   }

   private BufferedImage image;

   public Obstacle(int x, int y, int length, String direction)
   {
      super(x, y, length, direction);
      image = LoadImage("src/image/Player.png");
   }

   @Override
   public void draw(Graphics graphics)
   {
      graphics.drawImage(image, transform.position.x * 60, transform.position.y * 60, null);
   }

   @Override
   public String getType()
   {
      return "OO";
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
