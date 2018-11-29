package source.model;

import java.awt.*;

public class Obstacle extends GameObject
{
//   public Obstacle()
//   {
//      super();
//   }

   public Obstacle(int x, int y, int length, String direction)
   {
      super(x, y, length, direction);
   }

   @Override
   public void draw(Graphics graphics)
   {
      super.draw(graphics);
   }

   @Override
   public String getType()
   {
      return "OO";
   }
}
