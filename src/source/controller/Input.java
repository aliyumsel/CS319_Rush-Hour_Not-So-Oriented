package source.controller;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Input
{
    private static boolean[] mouseButtons = new boolean[5];

    private static java.util.Map<String, Boolean> keys = new HashMap<String, Boolean>()
    {
        {
            put("w", false);
            put("a", false);
            put("s", false);
            put("d", false);
        }
    };

    private static int mouseX;
    private static int mouseY;

    static boolean getKeyPressed(String keyID)
    {
        return keys.get(keyID);
    }

    static boolean getMouseButtonPressed(int buttonID)
    {
        return mouseButtons[buttonID];
    }

    static double getMousePositionX()
    {
        return mouseX;
        //return MouseInfo.getPointerInfo().getLocation().getX();
    }

    static double getMousePositionY()
    {
        return mouseY;
        //return MouseInfo.getPointerInfo().getLocation().y;
    }

    public static MouseListener getMouseListener()
    {
        return new MouseEventHandler();
    }

    public static KeyListener getKeyListener()
    {
        return new KeyEventHandler();
    }

    static void reset()
    {
        for (int i  = 0; i < mouseButtons.length; i++)
        {
            mouseButtons[i] = false;
        }

        for (Map.Entry<String, Boolean> entry : keys.entrySet() )
        {
            keys.put(entry.getKey(), false);
        }
    }

    static int[] getMouseMatrixPosition()
    {
        int[] mousePos = new int[2];
        mousePos[0] = mouseX / 50;
        mousePos[1] = mouseY / 50;

        return mousePos;
    }

    private static class MouseEventHandler extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            //System.out.println("mousePressed");
            if (e.getButton() - 1 < mouseButtons.length && e.getButton() - 1 >= 0)
            {
                mouseButtons[e.getButton() - 1] = true;
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
            if (keys.containsKey(e.getKeyChar() + ""))
            {
                keys.put(e.getKeyChar() + "", true);
            }
        }
    }
}
