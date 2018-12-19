package source.view;

import source.controller.GameEngine;
import source.model.GameObject;
import source.model.Map;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Iterator;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel
{
   private GuiPanelManager guiManager;
   private Map map;

   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException
   {
      super(null);
      this.guiManager = guiManager;
      setPreferredSize(new Dimension(480, 480));

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
   }


}