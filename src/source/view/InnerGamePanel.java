package source.view;

import source.controller.GameEngine;
import source.model.GameObject;
import source.model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Iterator;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel
{
   private GuiPanelManager guiManager;
   private Map map;
   private BufferedImage blackedOutImage;

   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException
   {
      super(null);
      this.guiManager = guiManager;
      setPreferredSize(new Dimension(480, 480));

      blackedOutImage = GameEngine.instance.themeManager.getDisabledImage("obstacle");

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
   }


}