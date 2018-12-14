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
   private JLabel shrinkAmountLabel;
   private JLabel spaceAmountLabel;
   private JLabel remaningTimeLabel;

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
   private BufferedImage shrinkDisabledImage;
   private BufferedImage spaceButtonImage;
   private BufferedImage spaceButtonHighlightedImage;
   private BufferedImage spaceDisabledImage;
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
      if ( !isShowing() )
      {
         return;
      }

      updatePowerUpButtons();
      updatePowerUpLabels();

      innerGamePanel.updatePanel();

      updateNumberOfMoves();

      updateRemainingTimeLabel();

      repaint();
   }

   private void updatePowerUpButtons()
   {
      if ( !GameEngine.instance.gameManager.isShrinkPowerUpUsable() )
      {
         disableButton(shrink, shrinkButtonHighlightedImage);
      }
      else
      {
         if ( !shrink.isEnabled() )
         {
            enableButton(shrink, shrinkButtonImage, shrinkButtonHighlightedImage);
         }
      }

      if ( !GameEngine.instance.gameManager.isSpacePowerUpUsable() )
      {
         disableButton(space, spaceButtonHighlightedImage);
      }
      else
      {
         if ( !space.isEnabled() )
         {
            enableButton(space, spaceButtonImage, spaceButtonHighlightedImage);
         }
      }
   }

   private void updatePowerUpLabels()
   {
      shrinkAmountLabel.setText(GameEngine.instance.playerManager.getCurrentPlayer().getRemainingShrinkPowerup() + "");
      spaceAmountLabel.setText(GameEngine.instance.playerManager.getCurrentPlayer().getRemainingSpacePowerup() + "");
   }

   private void updateRemainingTimeLabel()
   {
      int remainingTime = GameEngine.instance.gameManager.getRemainingTime();
      String countDown = String.format("%02d:%02d", remainingTime / 60, remainingTime % 60);
      remaningTimeLabel.setText(countDown + "");
   }

   private void disableButton(JButton button, BufferedImage lockedImage)
   {
      button.setIcon(new ImageIcon(lockedImage));
      button.setRolloverIcon(new ImageIcon(lockedImage));
      button.setEnabled(false);
   }

   private void enableButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage)
   {
      button.setIcon(new ImageIcon(normalImage));
      button.setRolloverIcon(new ImageIcon(highlightedImage));
      button.setEnabled(true);
   }

   public void loadImages()
   {
      background = ThemeManager.instance.getGamePanelBackgroundImage();
      Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
      background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = background.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

      menuButtonImage = guiManager.LoadImage("src/image/icons/menu.png");
      menuButtonHighlightedImage = guiManager.LoadImage("src/image/icons/menuH.png");

      settingsButtonImage = guiManager.LoadImage("src/image/icons/settingsIcon.png");
      settingsButtonHighlightedImage = guiManager.LoadImage("src/image/icons/settingsIconH.png");

      resetButtonImage = guiManager.LoadImage("src/image/icons/reset.png");
      resetButtonHighlightedImage = guiManager.LoadImage("src/image/icons/resetH.png");

      shrinkButtonImage = guiManager.LoadImage("src/image/icons/shrink.png");
      shrinkButtonHighlightedImage = guiManager.LoadImage("src/image/icons/shrinkH.png");
      shrinkDisabledImage = guiManager.LoadImage("src/image/icons/shrinkD.png");

      spaceButtonImage = guiManager.LoadImage("src/image/icons/poof.png");
      spaceButtonHighlightedImage = guiManager.LoadImage("src/image/icons/poofH.png");
      spaceDisabledImage = guiManager.LoadImage("src/image/icons/poofD.png");

      movesImage = guiManager.LoadImage("src/image/icons/movesCar.png");
   }

   private void createComponents()
   {
      menu = UIFactory.createButton(menuButtonImage, menuButtonHighlightedImage, "square", actionListener);
      reset = UIFactory.createButton(resetButtonImage, resetButtonHighlightedImage, "square", actionListener);
      settings = UIFactory.createButton(settingsButtonImage, settingsButtonHighlightedImage, "square", actionListener);
      shrink = UIFactory.createButton(shrinkButtonImage, shrinkButtonHighlightedImage, "square", actionListener);
      space = UIFactory.createButton(spaceButtonImage, spaceButtonHighlightedImage, "square", actionListener);

      space.setDisabledIcon(new ImageIcon(spaceDisabledImage));
      shrink.setDisabledIcon(new ImageIcon(shrinkDisabledImage));

      spaceAmountLabel = new JLabel("0",SwingConstants.CENTER);
      spaceAmountLabel.setPreferredSize(new Dimension(20, 20));
      spaceAmountLabel.setFont(new Font("Odin Rounded", Font.BOLD, 15));
      spaceAmountLabel.setForeground(Color.white);

      shrinkAmountLabel = new JLabel("0",SwingConstants.CENTER);
      shrinkAmountLabel.setPreferredSize(new Dimension(20, 20));
      shrinkAmountLabel.setFont(new Font("Odin Rounded", Font.BOLD, 15));
      shrinkAmountLabel.setForeground(Color.white);

      moveLabel = UIFactory.createLabelIcon(movesImage, "movesCar");

      numberLabel = new JLabel("0", SwingConstants.CENTER);
      numberLabel.setPreferredSize(new Dimension(107, 68));
      numberLabel.setFont(new Font("Odin Rounded", Font.BOLD, 60));
      numberLabel.setForeground(Color.white);

      remaningTimeLabel = new JLabel("0", SwingConstants.LEFT);
      remaningTimeLabel.setPreferredSize(new Dimension(107, 68));
      remaningTimeLabel.setFont(new Font("Odin Rounded", Font.BOLD, 30));
      remaningTimeLabel.setForeground(Color.white);
   }

   private void addComponents()
   {
      this.add(menu);
      this.add(reset);
      add(shrink);
      add(space);
      add(shrinkAmountLabel);
      add(spaceAmountLabel);
      add(moveLabel);
      add(numberLabel);
      add(settings);
      add(remaningTimeLabel);
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

      shrinkAmountLabel.setBounds(80, panelHeight - 60 - shrinkAmountLabel.getPreferredSize().height,
              shrinkAmountLabel.getPreferredSize().width, shrinkAmountLabel.getPreferredSize().height);

      spaceAmountLabel.setBounds(80, panelHeight - 130 - spaceAmountLabel.getPreferredSize().height,
              spaceAmountLabel.getPreferredSize().width, spaceAmountLabel.getPreferredSize().height);

      moveLabel.setBounds(panelWidth - moveLabel.getPreferredSize().width - 30, 200, moveLabel.getPreferredSize().width,
              moveLabel.getPreferredSize().height);

      numberLabel.setBounds(panelWidth - numberLabel.getPreferredSize().width - 15, 265, numberLabel.getPreferredSize().width,
              numberLabel.getPreferredSize().height);

      innerGamePanel.setBounds(guiManager.findCenter(panelWidth, innerGamePanel), guiManager.findCenter(panelHeight, innerGamePanel), innerGamePanel.getPreferredSize().width,
              innerGamePanel.getPreferredSize().height);

      remaningTimeLabel.setBounds( 50, panelHeight / 2, remaningTimeLabel.getPreferredSize().width, remaningTimeLabel.getPreferredSize().height);
   }

   public void showEndOfLevelPopUp(int starAmount)
   {
      innerGamePanel.setEndOfLevelPanelVisible(true, starAmount);
   }

   public void showTimeOverPopUp()
   {
      innerGamePanel.setTimeOverPopUpVisible(true);
   }

   public void setInnerGamePanelVisible()
   {
      System.out.println("Should have shown inner game panel");
      innerGamePanel.setVisible(true);
      innerGamePanel.setEndOfLevelPanelVisible(false, 0);
      innerGamePanel.setTimeOverPopUpVisible(false);
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
            GameEngine.instance.powerUpManager.togglePowerUp(PowerUpManager.PowerUp.Shrink);
         }
         else if ( e.getSource() == space )
         {
            GameEngine.instance.powerUpManager.togglePowerUp(PowerUpManager.PowerUp.Space);
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