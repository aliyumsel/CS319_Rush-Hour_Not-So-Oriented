package source.controller;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Input
{
   private static Component gamePanel;

   private static boolean[] mouseButtons = new boolean[5];

   private static String[] mouseButtons2 = new String[5];

   private static java.util.Map<String, Boolean> keys = new HashMap<String, Boolean>()
   {
      {
         put("w", false);
         put("a", false);
         put("s", false);
         put("d", false);
         put("n", false);
      }
   };

   private static int mouseX;
   private static int mouseY;

   static boolean getKeyPressed(String keyID)
   {
      return keys.get(keyID);
   }

   @SuppressWarnings("SameParameterValue")
   static boolean getMouseButtonPressed(int buttonID)
   {
      return mouseButtons[buttonID];
   }

   @SuppressWarnings("SameParameterValue")
   static boolean getMouseButtonReleased(int buttonID)
   {
      return mouseButtons2[buttonID].equals("Released");
   }

   public static MouseListener getMouseListener()
   {
      return new MouseEventHandler();
   }

   public static KeyListener getKeyListener()
   {
      return new KeyEventHandler();
   }

   public static void setGamePanel(Component component)
   {
      gamePanel = component;
   }

   static void reset()
   {
      for ( int i = 0; i < mouseButtons.length; i++ )
      {
         mouseButtons[i] = false;
         mouseButtons2[i] = "default";
      }

      for ( Map.Entry<String, Boolean> entry : keys.entrySet() )
      {
         keys.put(entry.getKey(), false);
      }
   }

   static int[] getMousePosition()
   {
      int[] mousePos = new int[2];

      if ( !gamePanel.isShowing() )
      {
         return mousePos;
      }

      mousePos[0] = (int) ( MouseInfo.getPointerInfo().getLocation().getX() - gamePanel.getLocationOnScreen().getX() );
      mousePos[1] = (int) ( MouseInfo.getPointerInfo().getLocation().getY() - gamePanel.getLocationOnScreen().getY() );
      return mousePos;
   }

   static int[] getMouseMatrixPosition()
   {
      int[] mousePos = new int[2];
      int pixelMultiplier = 60;
      mousePos[0] = mouseX / pixelMultiplier;
      mousePos[1] = mouseY / pixelMultiplier;

      return mousePos;
   }

   private static class MouseEventHandler extends MouseAdapter
   {
      @Override
      public void mousePressed(MouseEvent e)
      {
         System.out.println("mousePressed");
         if ( e.getButton() - 1 < mouseButtons.length && e.getButton() - 1 >= 0 )
         {
            mouseButtons2[e.getButton() - 1] = "Pressed";

            mouseButtons[e.getButton() - 1] = true;
            mouseX = e.getX();
            mouseY = e.getY();
         }
      }

      @Override
      public void mouseReleased(MouseEvent e)
      {
         System.out.println("mouseReleased");
         if ( e.getButton() - 1 < mouseButtons.length && e.getButton() - 1 >= 0 )
         {
            mouseButtons2[e.getButton() - 1] = "Released";

            mouseButtons[e.getButton() - 1] = false;
            mouseX = e.getX();
            mouseY = e.getY();
         }
      }
   }

   private static class KeyEventHandler extends KeyAdapter
   {
      @Override
      public void keyReleased(KeyEvent e)
      {
         System.out.println("keyPressed");
         if ( keys.containsKey(e.getKeyChar() + "") )
         {
            keys.put(e.getKeyChar() + "", true);
         }
      }
   }
}
