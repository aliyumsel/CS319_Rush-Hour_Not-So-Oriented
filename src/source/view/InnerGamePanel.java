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
   private TimeOverPopUp timeOverPopUp;
   private Map map;

   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException
   {
      super(null);
      this.guiManager = guiManager;
      setPreferredSize(new Dimension(480, 480));

      createTimeOverPopUp();
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
//      for ( GameObject gameObject : map.getGameObjects() )
//      {
//         gameObject.draw(g2D);
//      }
   }

   void setTimeOverPopUpVisible(boolean visible)
   {
      if ( visible )
      {
         //failure sound will be added
      }
      timeOverPopUp.setVisible(visible);
   }
   private void createTimeOverPopUp()
   {
      timeOverPopUp = new TimeOverPopUp(guiManager);
      add(timeOverPopUp);
      timeOverPopUp.setVisible(false);

      Dimension size = timeOverPopUp.getPreferredSize();
      timeOverPopUp.setBounds(25, 100, size.width, size.height);
   }
}