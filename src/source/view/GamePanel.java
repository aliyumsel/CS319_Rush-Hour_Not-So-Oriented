package source.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import source.controller.*;

public class GamePanel extends JPanel
{


   GuiPanelManager guiManager;

   private InnerGamePanel innerGamePanel;

   private JButton menu;
   private JButton reset;
   private JButton settings;
   private JButton shrink;
   private JButton space;

   //private JLabel timerIcon;
   private JLabel moveLabel;
   private JLabel numberLabel;

   //private JProgressBar timer;

   private BufferedImage background;
   private BufferedImage menuButtonImage;
   private BufferedImage menuButtonHighlightedImage;
   private BufferedImage resetButtonImage;
   private BufferedImage resetButtonHighlightedImage;
   private BufferedImage settingsButtonImage;
   private BufferedImage settingsButtonHighlightedImage;
   private BufferedImage shrinkButtonImage;
   private BufferedImage shrinkButtonHighlightedImage;
   private BufferedImage spaceButtonImage;
   private BufferedImage spaceButtonHighlightedImage;
   private BufferedImage movesImage;

   private int panelWidth;
   private int panelHeight;

   GamePanel(GuiPanelManager _guiManager)
   {
      super(null);
      guiManager = _guiManager;

      panelWidth = guiManager.panelWidth;
      panelHeight = guiManager.panelHeight;

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        loadImages();
        createComponents();
        addComponents();
        createInnerGamePanel();
        setBoundsOfComponents();
        setOpaque(false);
    }

   public void updatePanel()
   {
      repaint();
      if ( !isShowing() )
      {
         return;
      }

      if (!GameEngine.instance.gameManager.isShrinkPowerUpUsable())
      {
          UIFactory.disableButton(shrink, shrinkButtonHighlightedImage);
   }
      else
      {
          if (!shrink.isEnabled())
          {
              UIFactory.enableButton(shrink, shrinkButtonImage);
          }
      }

       if (!GameEngine.instance.gameManager.isSpacePowerUpUsable())
       {
           UIFactory.disableButton(space, spaceButtonHighlightedImage);
       }
       else
       {
           if (!space.isEnabled())
           {
               UIFactory.enableButton(shrink, spaceButtonImage);
           }
       }

      innerGamePanel.updatePanel();

      updateNumberOfMoves();

      repaint();
   }

    public void loadImages() {
        background = ThemeManager.instance.getGamePanelBackgroundImage();
        Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
        background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = background.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
        //innerGamePanel.endOfLevelPanel.loadImages();
        menuButtonImage = guiManager.LoadImage("src/image/icons/menu.png");
        menuButtonHighlightedImage = guiManager.LoadImage("src/image/icons/menuH.png");

      settingsButtonImage = guiManager.LoadImage("src/image/icons/settingsIcon.png");
      settingsButtonHighlightedImage = guiManager.LoadImage("src/image/icons/settingsIconH.png");

      resetButtonImage = guiManager.LoadImage("src/image/icons/reset.png");
      resetButtonHighlightedImage = guiManager.LoadImage("src/image/icons/resetH.png");

      shrinkButtonImage = guiManager.LoadImage("src/image/icons/hint.png");
      shrinkButtonHighlightedImage = guiManager.LoadImage("src/image/icons/hintH.png");

      spaceButtonImage = guiManager.LoadImage("src/image/icons/hint.png");
      spaceButtonHighlightedImage = guiManager.LoadImage("src/image/icons/hintH.png");

      movesImage = guiManager.LoadImage("src/image/icons/movesCar.png");
   }

   private void createComponents()
   {
      menu = UIFactory.createButton(menuButtonImage, menuButtonHighlightedImage, "square", actionListener);
      reset = UIFactory.createButton(resetButtonImage, resetButtonHighlightedImage, "square", actionListener);
      settings = UIFactory.createButton(settingsButtonImage, settingsButtonHighlightedImage, "square", actionListener);
      shrink = UIFactory.createButton(shrinkButtonImage, shrinkButtonHighlightedImage, "square", actionListener);
      space = UIFactory.createButton(spaceButtonImage, spaceButtonHighlightedImage, "square", actionListener);

      //timerIcon = new JLabel(new ImageIcon("src/image/timer.png"));
      //timerIcon.setPreferredSize(new Dimension(32, 32));

      moveLabel = UIFactory.createLabelIcon(movesImage, "movesCar");

      numberLabel = new JLabel("0", SwingConstants.CENTER);
      numberLabel.setPreferredSize(new Dimension(107, 68));
      numberLabel.setFont(new Font("Odin Rounded", Font.BOLD, 60));
      numberLabel.setForeground(Color.white);

      //timer = new JProgressBar(SwingConstants.VERTICAL);
      //timer.setPreferredSize(new Dimension(30, 300));
   }

   private void addComponents()
   {
      this.add(menu);
      this.add(reset);
      add(shrink);
      add(space);
      add(moveLabel);
      add(numberLabel);
      //add(timerIcon);
      //add(timer);
      add(settings);
   }

   private void setBoundsOfComponents()
   {
      menu.setBounds(30, 30, menu.getPreferredSize().width,
              menu.getPreferredSize().height);

      settings.setBounds(panelWidth - 30 - settings.getPreferredSize().width, 30,
              settings.getPreferredSize().width, settings.getPreferredSize().height);

      reset.setBounds(panelWidth - 30 - reset.getPreferredSize().width, panelHeight - 30 - reset.getPreferredSize().height,
              reset.getPreferredSize().width, reset.getPreferredSize().height);

      shrink.setBounds(30, panelHeight - 30 - shrink.getPreferredSize().height,
              shrink.getPreferredSize().width, shrink.getPreferredSize().height);

      space.setBounds(30, panelHeight - 100 - space.getPreferredSize().height,
              space.getPreferredSize().width, space.getPreferredSize().height);

      moveLabel.setBounds(panelWidth - moveLabel.getPreferredSize().width - 30, 200, moveLabel.getPreferredSize().width,
              moveLabel.getPreferredSize().height);

      numberLabel.setBounds(panelWidth - numberLabel.getPreferredSize().width - 15, 265, numberLabel.getPreferredSize().width,
              numberLabel.getPreferredSize().height);

//		timerIcon.setBounds(70, 116, timerIcon.getPreferredSize().width,
//				timerIcon.getPreferredSize().height);
//
//		timer.setBounds(71, 160, timer.getPreferredSize().width,
//				timer.getPreferredSize().height);

        innerGamePanel.setBounds(guiManager.findCenter(panelWidth, innerGamePanel), guiManager.findCenter(panelHeight, innerGamePanel), innerGamePanel.getPreferredSize().width,
                innerGamePanel.getPreferredSize().height);
        // System.out.println(innerGamePanel.getBounds().x + "," + innerGamePanel.getBounds().y);

   }

    public void setEndOfLevelPanelVisible(int starAmount, boolean success) {
        innerGamePanel.setEndOfLevelPanelVisible(true, starAmount, success);
    }

    public void setInnerGamePanelVisible() {
        System.out.println("Should have shown inner game panel");
        innerGamePanel.setVisible(true);
        innerGamePanel.setEndOfLevelPanelVisible(false, 0, false);
    }

   private void createInnerGamePanel()
   {
      try
      {
         innerGamePanel = new InnerGamePanel(guiManager);
         add(innerGamePanel);
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      setVisible(false);
   }

   private ActionListener actionListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         GameEngine.instance.soundManager.buttonClick();
         if ( e.getSource() == reset )
         {
            GameEngine.instance.gameManager.resetLevel();
            guiManager.setPanelVisible("Game");
         }
         else if ( e.getSource() == menu )
         {
            guiManager.setPanelVisible("MainMenu");
            GameEngine.instance.gameManager.stopMap();
         }
         else if ( e.getSource() == settings )
         {
            guiManager.setPanelVisible("Settings");
         }
         else if ( e.getSource() == shrink )
         {
            GameEngine.instance.powerUpManager.initializePowerUp(PowerUpManager.PowerUp.Shrink);
         }
         else if ( e.getSource() == space )
         {
            GameEngine.instance.powerUpManager.initializePowerUp(PowerUpManager.PowerUp.Space);
         }
         else if ( e.getSource() == settings )
         {
            guiManager.setPanelVisible("MainMenu");
         }
      }
   };

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

   InnerGamePanel getInnerGamePanel()
   {
      return innerGamePanel;
   }

   private void updateNumberOfMoves()
   {
      numberLabel.setText(GameEngine.instance.vehicleController.getNumberOfMoves() + "");
   }
}