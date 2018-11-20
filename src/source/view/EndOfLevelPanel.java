package source.view;

import source.controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndOfLevelPanel extends JPanel
{
   GuiPanelManager guiManager;

   private JButton retry;
   private JButton menu;
   private JButton nextLevel;

   public EndOfLevelPanel(GuiPanelManager _guiManager)
   {
	  guiManager = _guiManager;
      setPreferredSize(new Dimension(250, 250));
      createComponents();
      addComponents();
      setBoundsOfComponents();
   }

   void updatePanel()
   {
      if (!isShowing())
      {
         return;
      }
      repaint();
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      setBackground(Color.orange);
   }

   private void createComponents()
   {
      retry = new JButton(new ImageIcon("src/image/back.png"));
      retry.setPreferredSize(new Dimension(48,48));
      retry.setFocusable(false);
      retry.addActionListener(actionListener);

      menu = new JButton(new ImageIcon("src/image/back.png"));
      menu.setPreferredSize(new Dimension(48,48));
      retry.setFocusable(false);
      retry.addActionListener(actionListener);

      nextLevel = new JButton(new ImageIcon("src/image/back.png"));
      nextLevel.setPreferredSize(new Dimension(48,48));
      retry.setFocusable(false);
      retry.addActionListener(actionListener);
   }

   private void addComponents()
   {
      add(retry);
      add(menu);
      add(nextLevel);
   }

   private void setBoundsOfComponents()
   {
      Insets insets = getInsets();

      Dimension size;
      size = retry.getPreferredSize();
      retry.setBounds(32 + insets.left, 46 + insets.top, size.width, size.height);
   }

   ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == retry)
         {
            GameEngine.instance.gameManager.resetLevel();
         }

         if (e.getSource() == menu)
         {
            guiManager.setPanelVisible("MainMenu");
         }

         if (e.getSource() == nextLevel)
         {
            GameEngine.instance.gameManager.nextLevel();
         }
      }
   };
}
