package source.view;

import source.controller.GameEngine;
import source.model.GameObject;
import source.model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel
{
   private GuiPanelManager guiManager;
   private Map map;
   private BufferedImage blackedOutImage;

   private ArrayList<BufferedImage> poofImages;
   private BufferedImage poof0;
   private BufferedImage poof1;
   private BufferedImage poof2;
   private BufferedImage poof3;
   private BufferedImage poof4;
   private BufferedImage poof5;

   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException
   {
      super(null);
      this.guiManager = guiManager;
      setPreferredSize(new Dimension(480, 480));

      blackedOutImage = GameEngine.instance.themeManager.getDisabledImage("obstacle");

      poofImages = new ArrayList<>();

      loadImages();
      addPoofImages();

      setOpaque(false);
      setVisible(true);
   }

   void updatePanel()
   {
      if ( !isShowing() )
      {
         return;
      }
      map = GameEngine.instance.mapController.getMap();
      repaint();
   }

   public void loadImages()
   {
      poof0 = guiManager.LoadImage("image/icons/miniStar.png");
      poof1 = guiManager.LoadImage("image/icons/miniStarLocked.png");
      poof2 = guiManager.LoadImage("image/icons/miniStar.png");
      poof3 = guiManager.LoadImage("image/icons/miniStarLocked.png");
      poof4 = guiManager.LoadImage("image/icons/miniStar.png");
      poof5 = guiManager.LoadImage("image/icons/miniStarLocked.png");
   }

   private void addPoofImages()
   {
      poofImages.add(poof0);
      poofImages.add(poof1);
      poofImages.add(poof2);
      poofImages.add(poof3);
      poofImages.add(poof4);
      poofImages.add(poof5);
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      if ( map == null )
      {
         return;
      }

      Graphics2D g2D = (Graphics2D) g;

      for ( Iterator<GameObject> gameObjects = map.getGameObjects().iterator(); gameObjects.hasNext(); )
      {
         GameObject gameObject = gameObjects.next();
         gameObject.draw(g2D);
      }

      if (GameEngine.instance.powerUpManager.isPowerUpActive())
      {
         Graphics2D temp = (Graphics2D) g.create();
         Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
         temp.setComposite(composite);
         String[][] grid = GameEngine.instance.mapController.getMap().getGrid();
         for (int i = 0; i < grid.length; i++)
         {
            for (int j = 0; j < grid.length; j++)
            {
               if (grid[j][i].equals("Space"))
               {
                  temp.drawImage(blackedOutImage,i * 60, j * 60, null);
               }
            }
         }
      }

      int counter = GameEngine.instance.powerUpManager.getCurrentCount();
      if ( counter > 0)
      {
         Graphics2D temp = (Graphics2D) g.create();
         int x = GameEngine.instance.powerUpManager.getObstacleToRemoveX();
         int y = GameEngine.instance.powerUpManager.getObstacleToRemoveY();

         temp.drawImage(poofImages.get(counter / ( GameEngine.instance.powerUpManager.getPoofDuration()/ poofImages.size())), x  * 60, y * 60, null);
      }
   }
}