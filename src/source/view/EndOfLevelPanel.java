package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EndOfLevelPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private JButton retry;
   private JButton menu;
   private JButton nextLevel;
   private JLabel heading;
   private JLabel[] stars;

   private BufferedImage background;

   private BufferedImage menuButtonImage;
   private BufferedImage menuButtonHighlightedImage;
   private BufferedImage retryButtonImage;
   private BufferedImage retryButtonHighlightedImage;
   private BufferedImage nextLevelButtonImage;
   private BufferedImage nextLevelButtonHighlightedImage;
   private BufferedImage starImage;
   private BufferedImage starLockedImage;

   private int panelWidth = 400;
   private int panelHeight = 250;

   public EndOfLevelPanel(GuiPanelManager _guiManager)
   {
      super(null);
      guiManager = _guiManager;
      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      setOpaque(false);

//      showStars(2); for testing the showStars method it works
   }

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/endOfLevelPanelBackground.png");

      menuButtonImage = guiManager.LoadImage("src/image/icons/menu.png");
      menuButtonHighlightedImage = guiManager.LoadImage("src/image/icons/menuH.png");

      retryButtonImage = guiManager.LoadImage("src/image/icons/reset.png");
      retryButtonHighlightedImage = guiManager.LoadImage("src/image/icons/resetH.png");

      nextLevelButtonImage = guiManager.LoadImage("src/image/icons/next.png");
      nextLevelButtonHighlightedImage = guiManager.LoadImage("src/image/icons/nextH.png");

      starImage = guiManager.LoadImage("src/image/icons/star.png");
      starLockedImage = guiManager.LoadImage("src/image/icons/starLocked.png");
   }

   void updatePanel()
   {
      if ( !isShowing() )
      {
         return;
      }
      repaint();
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      drawBackground(g);
   }

   private void drawBackground(Graphics graphics)
   {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

   private void createComponents()
   {
      heading = new JLabel("Level Completed!", SwingConstants.CENTER);
      heading.setPreferredSize(new Dimension(300, 60));
      heading.setFont(new Font("Odin Rounded", Font.PLAIN, 35));
      heading.setForeground(Color.white);

      stars = new JLabel[3];
      for ( int i = 0; i < stars.length; i++ )
      {
         stars[i] = new JLabel();
         stars[i].setIcon(new ImageIcon(starLockedImage));
      }

      menu = UIFactory.createButton(menuButtonImage, menuButtonHighlightedImage, "square", actionListener);
      retry = UIFactory.createButton(retryButtonImage, retryButtonHighlightedImage, "square", actionListener);
      nextLevel = UIFactory.createButton(nextLevelButtonImage, nextLevelButtonHighlightedImage, "square", actionListener);
   }

   private void addComponents()
   {
      add(retry);
      add(menu);
      add(nextLevel);
      add(heading);

      for ( int i = 0; i < stars.length; i++ )
      {
         add(stars[i]);
      }
   }

   private void setBoundsOfComponents()
   {
      heading.setBounds(50, 0, heading.getPreferredSize().width,
              heading.getPreferredSize().height);

      menu.setBounds(105, 150, menu.getPreferredSize().width, menu.getPreferredSize().height);
      retry.setBounds(175, 150, retry.getPreferredSize().width, retry.getPreferredSize().height);
      nextLevel.setBounds(245, 150, nextLevel.getPreferredSize().width, nextLevel.getPreferredSize().height);

      for ( int i = 0; i < stars.length; i++ )
      {
         stars[i].setBounds(guiManager.findCenter(panelWidth, stars[i]) + ( 85 * ( i - 1 ) ), 60, stars[i].getPreferredSize().width, stars[i].getPreferredSize().height);
      }

   }

   private void showStars(int starAmount)
   {
      for (int i = 0; i < stars.length; i++)
      {
         if (i < starAmount)
         {
            stars[i].setIcon(new ImageIcon(starImage));
         }
      }
   }

   private ActionListener actionListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         SoundManager.instance.buttonClick();
         if ( e.getSource() == retry )
         {
            GameEngine.instance.gameManager.resetLevel();
         }

         if ( e.getSource() == menu )
         {
            guiManager.setPanelVisible("MainMenu");
         }

         if ( e.getSource() == nextLevel )
         {
            GameEngine.instance.gameManager.nextLevel();
         }
      }
   };
}
