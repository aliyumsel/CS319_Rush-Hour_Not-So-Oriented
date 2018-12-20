package source.model;

import source.controller.ThemeManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Space extends GameObject
{
   private BufferedImage blackedOutImage;

   public Space(int x, int y, int length, String direction)
   {
      super(x, y, length, direction);
      blackedOutImage = ThemeManager.instance.getDisabledImage("obstacle");
   }

   @SuppressWarnings("Duplicates")
   @Override
   public void draw(Graphics2D graphics)
   {
      if (isBlackedOut)
      {
         Graphics2D temp = (Graphics2D) graphics.create();
         Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
         temp.setComposite(composite);
         temp.drawImage(blackedOutImage,(int) transform.position.x * 60, (int) transform.position.y * 60, null);
      }
   }

   @Override
   public String getType()
   {
      return "SS";
   }
}
