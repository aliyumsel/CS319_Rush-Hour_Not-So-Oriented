package source.view;

import source.controller.*;
import source.model.LevelInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

@SuppressWarnings("Duplicates")
public class GamePanel extends JPanel
{
   GuiPanelManager guiManager;

   private InnerGamePanel innerGamePanel;
   private EndOfLevelPanel endOfLevelPanel;
   private TimeOverPopUp timeOverPopUp;

   private JButton menu;
   private JButton reset;
   private JButton settings;
   private JButton shrink;
   private JButton space;

   private JLabel blackBackground;
   private JLabel moveLabel;
   private JLabel numberLabel;
   private JLabel shrinkAmountLabel;
   private JLabel spaceAmountLabel;
   private JLabel remainingTimeLabel;

   //timer stuff
   private JLabel timerBottomLabel;
   private JLabel timerBackgroundLabel;
   private JLabel timerForegroundLabel;

   //move count bar
   private JLabel firstStarBackgroundLabel;
   private JLabel firstStarForegroundLabel;
   private JLabel secondStarBackgroundLabel;
   private JLabel secondStarForegroundLabel;

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
   private BufferedImage timerBottomImage;
   private BufferedImage timerBackgroundImage;
   private BufferedImage timerForegroundImage;
   private BufferedImage firstStarForegroundImage;
   private BufferedImage firstStarBackgroundImage;
   private BufferedImage secondStarForegroundImage;
   private BufferedImage secondStarBackgroundImage;

   private int panelWidth;
   private int panelHeight;

   private int timerForegroundStartHeight;
   private int moveCountForegroundStartHeight;
   private int timerForegroundStartPosition;
   private int moveCountForegroundStartPosition;
   private int timerBottomPosition;

   GamePanel(GuiPanelManager _guiManager)
   {
      super(null);
      guiManager = _guiManager;

      panelWidth = guiManager.panelWidth;
      panelHeight = guiManager.panelHeight;

      setPreferredSize(new Dimension(panelWidth, panelHeight));
      loadImages();
      createComponents();
      createEndOfLevelPanel();
      createTimeOverPopUp();
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

      endOfLevelPanel.updatePanel();

      updatePowerUpButtons();
      updatePowerUpLabels();

      innerGamePanel.updatePanel();

      updateNumberOfMoves();

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

      menuButtonImage = guiManager.LoadImage("image/icons/menu.png");
      menuButtonHighlightedImage = guiManager.LoadImage("image/icons/menuH.png");

      settingsButtonImage = guiManager.LoadImage("image/icons/settingsIcon.png");
      settingsButtonHighlightedImage = guiManager.LoadImage("image/icons/settingsIconH.png");

      resetButtonImage = guiManager.LoadImage("image/icons/reset.png");
      resetButtonHighlightedImage = guiManager.LoadImage("image/icons/resetH.png");

      shrinkButtonImage = guiManager.LoadImage("image/icons/shrink.png");
      shrinkButtonHighlightedImage = guiManager.LoadImage("image/icons/shrinkH.png");
      shrinkDisabledImage = guiManager.LoadImage("image/icons/shrinkD.png");

      spaceButtonImage = guiManager.LoadImage("image/icons/poof.png");
      spaceButtonHighlightedImage = guiManager.LoadImage("image/icons/poofH.png");
      spaceDisabledImage = guiManager.LoadImage("image/icons/poofD.png");

      movesImage = guiManager.LoadImage("image/icons/movesCar.png");

      timerBackgroundImage = guiManager.LoadImage("image/timerBackground.png");
      timerForegroundImage = guiManager.LoadImage("image/timerForeground.png");
      timerBottomImage = guiManager.LoadImage("image/timerBottom.png");
      firstStarForegroundImage = guiManager.LoadImage("image/icons/miniStar.png");
      firstStarBackgroundImage = guiManager.LoadImage("image/icons/miniStarLocked.png");
      secondStarForegroundImage = guiManager.LoadImage("image/icons/miniStar.png");
      secondStarBackgroundImage = guiManager.LoadImage("image/icons/miniStarLocked.png");
   }

   private void createComponents()
   {
      blackBackground = new JLabel()
      {
         @Override
         protected void paintComponent(Graphics g)
         {
            super.paintComponent(g);
            Graphics2D temp = (Graphics2D) g.create();
            int width = panelWidth;
            int height = panelHeight;
            Color myColour = new Color(0, 0, 0, 200);
            temp.setColor(myColour);
            temp.fillRect(0, 0, width, height);
         }
      };
      blackBackground.setPreferredSize(new Dimension(panelWidth, panelHeight));
      blackBackground.setVisible(false);

      menu = UIFactory.createButton(menuButtonImage, menuButtonHighlightedImage, "square", actionListener);
      reset = UIFactory.createButton(resetButtonImage, resetButtonHighlightedImage, "square", actionListener);
      settings = UIFactory.createButton(settingsButtonImage, settingsButtonHighlightedImage, "square", actionListener);
      shrink = UIFactory.createButton(shrinkButtonImage, shrinkButtonHighlightedImage, "square", actionListener);
      space = UIFactory.createButton(spaceButtonImage, spaceButtonHighlightedImage, "square", actionListener);

      timerBackgroundLabel = UIFactory.createLabelIcon(timerBackgroundImage, new Dimension(30, 232));
      timerForegroundLabel = UIFactory.createLabelIcon(timerForegroundImage, new Dimension(30, 232));
      timerBottomLabel = UIFactory.createLabelIcon(timerBottomImage, new Dimension(30, 28));

      firstStarForegroundLabel = UIFactory.createLabelIcon(firstStarForegroundImage, new Dimension(26, 26));
      firstStarBackgroundLabel = UIFactory.createLabelIcon(firstStarBackgroundImage, new Dimension(26, 26));
      secondStarForegroundLabel = UIFactory.createLabelIcon(secondStarForegroundImage, new Dimension(26, 26));
      secondStarBackgroundLabel = UIFactory.createLabelIcon(secondStarBackgroundImage, new Dimension(26, 26));

      timerForegroundStartHeight = timerForegroundLabel.getPreferredSize().height;
      moveCountForegroundStartHeight = firstStarForegroundLabel.getPreferredSize().height;
      space.setDisabledIcon(new ImageIcon(spaceDisabledImage));
      shrink.setDisabledIcon(new ImageIcon(shrinkDisabledImage));

      spaceAmountLabel = new JLabel("0", SwingConstants.CENTER);
      spaceAmountLabel.setPreferredSize(new Dimension(20, 20));
      spaceAmountLabel.setFont(new Font("Odin Rounded", Font.BOLD, 15));
      spaceAmountLabel.setForeground(Color.white);

      shrinkAmountLabel = new JLabel("0", SwingConstants.CENTER);
      shrinkAmountLabel.setPreferredSize(new Dimension(20, 20));
      shrinkAmountLabel.setFont(new Font("Odin Rounded", Font.BOLD, 15));
      shrinkAmountLabel.setForeground(Color.white);

      moveLabel = UIFactory.createLabelIcon(movesImage, "movesCar");

      numberLabel = new JLabel("0", SwingConstants.CENTER);
      numberLabel.setPreferredSize(new Dimension(107, 68));
      numberLabel.setFont(new Font("Odin Rounded", Font.BOLD, 60));
      numberLabel.setForeground(Color.white);

      remainingTimeLabel = new JLabel("0", SwingConstants.LEFT);
      remainingTimeLabel.setPreferredSize(new Dimension(107, 68));
      remainingTimeLabel.setFont(new Font("Odin Rounded", Font.BOLD, 30));
      remainingTimeLabel.setForeground(Color.white);
   }

   private void addComponents()
   {
      add(blackBackground);

      this.add(menu);
      this.add(reset);
      add(shrink);
      add(space);
      add(shrinkAmountLabel);
      add(spaceAmountLabel);
      add(moveLabel);
      add(numberLabel);
      add(settings);
   }

   private void setBoundsOfComponents()
   {
      blackBackground.setBounds(0, 0, panelWidth, panelHeight);

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

      moveLabel.setBounds(panelWidth - moveLabel.getPreferredSize().width - 30, 215, moveLabel.getPreferredSize().width,
              moveLabel.getPreferredSize().height);

      numberLabel.setBounds(panelWidth - numberLabel.getPreferredSize().width - 15, 265, numberLabel.getPreferredSize().width,
              numberLabel.getPreferredSize().height);

      innerGamePanel.setBounds(guiManager.findCenter(panelWidth, innerGamePanel), guiManager.findCenterVertical(panelHeight, innerGamePanel), innerGamePanel.getPreferredSize().width,
              innerGamePanel.getPreferredSize().height);

      remainingTimeLabel.setBounds(80, panelHeight / 2, remainingTimeLabel.getPreferredSize().width, remainingTimeLabel.getPreferredSize().height);

      timerBottomLabel.setBounds(40, ( panelHeight + timerForegroundLabel.getPreferredSize().height ) / 2 - 45, timerBottomLabel.getPreferredSize().width, timerBottomLabel.getPreferredSize().height);

      timerForegroundLabel.setBounds(40, ( panelHeight - timerForegroundLabel.getPreferredSize().height ) / 2 - 45, timerForegroundLabel.getPreferredSize().width, timerForegroundLabel.getPreferredSize().height);

      timerBackgroundLabel.setBounds(40, ( panelHeight - timerBackgroundLabel.getPreferredSize().height ) / 2 - 45, timerBackgroundLabel.getPreferredSize().width, timerBackgroundLabel.getPreferredSize().height);

      firstStarForegroundLabel.setBounds(panelWidth - 60 - firstStarForegroundLabel.getPreferredSize().width, 15 + ( panelHeight - timerForegroundLabel.getPreferredSize().height ) / 2 - 45, timerForegroundLabel.getPreferredSize().width, timerForegroundLabel.getPreferredSize().height);

      firstStarBackgroundLabel.setBounds(panelWidth - 60 - firstStarBackgroundLabel.getPreferredSize().width, 15 + ( panelHeight - timerBackgroundLabel.getPreferredSize().height ) / 2 - 45, timerBackgroundLabel.getPreferredSize().width, timerBackgroundLabel.getPreferredSize().height);

      secondStarForegroundLabel.setBounds(panelWidth - 40 - firstStarForegroundLabel.getPreferredSize().width, 15 + ( panelHeight - timerForegroundLabel.getPreferredSize().height ) / 2 - 45, timerForegroundLabel.getPreferredSize().width, timerForegroundLabel.getPreferredSize().height);

      secondStarBackgroundLabel.setBounds(panelWidth - 40 - firstStarBackgroundLabel.getPreferredSize().width, 15 + ( panelHeight - timerBackgroundLabel.getPreferredSize().height ) / 2 - 45, timerBackgroundLabel.getPreferredSize().width, timerBackgroundLabel.getPreferredSize().height);

      timerForegroundStartPosition = ( panelHeight - timerForegroundLabel.getPreferredSize().height ) / 2 - 45;
      moveCountForegroundStartPosition = ( panelHeight - firstStarForegroundLabel.getPreferredSize().height ) / 2 - 45;
      timerBottomPosition = ( panelHeight + timerForegroundLabel.getPreferredSize().height ) / 2 - 45;

   }

   private void setEndOfLevelPanelVisible(boolean visible, int starAmount)
   {
      if ( visible )
      {
         GameEngine.instance.soundManager.successSound();
      }
      endOfLevelPanel.showStars(starAmount);
      endOfLevelPanel.setVisible(visible);
   }

   public void showEndOfLevelPopUp(int starAmount)
   {
      setEndOfLevelPanelVisible(true, starAmount);
      showBlackBackground();
   }

   public void showTimeOverPopUp()
   {
      setTimeOverPopUpVisible(true);
      showBlackBackground();
   }

   private void showBlackBackground()
   {
      blackBackground.setVisible(true);
   }

   void hideBlackBackground()
   {
      blackBackground.setVisible(false);
   }

   public void setInnerGamePanelVisible()
   {
      innerGamePanel.setVisible(true);
      setEndOfLevelPanelVisible(false, 0);
      setTimeOverPopUpVisible(false);
   }

   EndOfLevelPanel getEndOfLevelPanel()
   {
      return endOfLevelPanel;
   }

   private void setTimeOverPopUpVisible(boolean visible)
   {
//      if ( visible )
//      {
//         //failure sound will be added
//      }
      timeOverPopUp.setVisible(visible);
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

   private void createTimeOverPopUp()
   {
      timeOverPopUp = new TimeOverPopUp(guiManager);
      add(timeOverPopUp);
      timeOverPopUp.setVisible(false);

      Dimension size = timeOverPopUp.getPreferredSize();
      timeOverPopUp.setBounds(guiManager.findCenter(panelWidth, endOfLevelPanel), guiManager.findCenterVertical(panelHeight, endOfLevelPanel), size.width, size.height);
   }


   private void createEndOfLevelPanel()
   {
      endOfLevelPanel = new EndOfLevelPanel(guiManager);
      add(endOfLevelPanel);
      endOfLevelPanel.setVisible(false);

      Dimension size = endOfLevelPanel.getPreferredSize();
      endOfLevelPanel.setBounds(guiManager.findCenter(panelWidth, endOfLevelPanel), guiManager.findCenterVertical(panelHeight, endOfLevelPanel), size.width, size.height);
   }

   private ActionListener actionListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         if ( blackBackground.isVisible() )
         {
            return;
         }

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
      if ( GameEngine.instance.gameManager.isLevelBonus() && GameEngine.instance.gameManager.isGameActive() )
      {
         drawTimerForeground(g);
      }
      drawMoveCountForeground(g);
   }

   private void drawBackground(Graphics graphics)
   {
      graphics.drawImage(background, 0, 0, null);

      if ( GameEngine.instance.gameManager.isLevelBonus() && GameEngine.instance.gameManager.isGameActive() )
      {
         graphics.drawImage(timerBackgroundImage, 40, timerForegroundStartPosition, null);
         graphics.drawImage(timerBottomImage, 40, timerBottomPosition, null);
      }
      graphics.drawImage(background, 0, 0, null);

      if ( GameEngine.instance.gameManager.isLevelBonus() && GameEngine.instance.gameManager.isGameActive() )
      {
         graphics.drawImage(timerBackgroundImage, 40, timerForegroundStartPosition, null);
         graphics.drawImage(timerBottomImage, 40, timerBottomPosition, null);
      }
      //Backgrounds for stars
      graphics.drawImage(firstStarBackgroundImage, panelWidth - 90 - firstStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45, null);
      graphics.drawImage(secondStarBackgroundImage, panelWidth - 60 - secondStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45, null);
      graphics.drawImage(firstStarForegroundImage, panelWidth - 30 - firstStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45, null);
   }

   private void drawMoveCountForeground(Graphics g)
   {
      Graphics2D graphics2d = (Graphics2D) g;
      BufferedImage subImage = null;
      LevelInformation currentLevel = PlayerManager.instance.getCurrentPlayer().getLevels().get(GameManager.instance.level - 1);

      //For the first star
      if ( GameEngine.instance.vehicleController.getNumberOfMoves() <= currentLevel.getMaxNumberOfMovesForThreeStars() )
      {
         int remainingMoves = currentLevel.getMaxNumberOfMovesForThreeStars() - ( GameEngine.instance.vehicleController.getNumberOfMoves() );
         int moveCountStartValue = currentLevel.getMaxNumberOfMovesForThreeStars();
         double f = remainingMoves / (double) moveCountStartValue;
         int starHeight = moveCountForegroundStartHeight - (int) lerp(0, moveCountForegroundStartHeight, f); //26 yı değikene atcam da anlamadım ahmet kodunu
         if ( currentLevel.getMaxNumberOfMovesForThreeStars() - GameEngine.instance.vehicleController.getNumberOfMoves() > 0 )
         {
            //System.out.println(currentLevel.getMaxNumberOfMovesForThreeStars() -GameEngine.instance.vehicleController.getNumberOfMoves() + "  ,  " + starHeight);
            subImage = firstStarForegroundImage.getSubimage(0, firstStarBackgroundLabel.getPreferredSize().height - starHeight, moveCountForegroundStartHeight, starHeight);
            graphics2d.drawImage(subImage, panelWidth - 90 - firstStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45 + firstStarBackgroundLabel.getPreferredSize().height - starHeight, null);
         }
      }
      else if ( currentLevel.getMaxNumberOfMovesForThreeStars() < GameEngine.instance.vehicleController.getNumberOfMoves() )
      {
         graphics2d.drawImage(subImage, panelWidth - 90 - firstStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45 + firstStarBackgroundLabel.getPreferredSize().height, null);
      }
      //For the second star
      if ( GameEngine.instance.vehicleController.getNumberOfMoves() <= currentLevel.getMaxNumberOfMovesForTwoStars() )
      {
         if ( GameEngine.instance.vehicleController.getNumberOfMoves() > currentLevel.getMaxNumberOfMovesForThreeStars() )
         {
            int remainingMoves = currentLevel.getMaxNumberOfMovesForTwoStars() - GameEngine.instance.vehicleController.getNumberOfMoves();
            int moveCountStartValue = currentLevel.getMaxNumberOfMovesForTwoStars() - currentLevel.getMaxNumberOfMovesForThreeStars();
            double f = remainingMoves / (double) moveCountStartValue;
            int starHeight = moveCountForegroundStartHeight - (int) lerp(0, moveCountForegroundStartHeight, f);
            if ( currentLevel.getMaxNumberOfMovesForTwoStars() - GameEngine.instance.vehicleController.getNumberOfMoves() > 0 )
            {
               subImage = secondStarForegroundImage.getSubimage(0, secondStarBackgroundLabel.getPreferredSize().height - starHeight, moveCountForegroundStartHeight, starHeight);
            }
            graphics2d.drawImage(subImage, panelWidth - 60 - secondStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45 + firstStarBackgroundLabel.getPreferredSize().height - starHeight, null);
         }
         else
         {
            subImage = secondStarForegroundImage;
            int starHeight = moveCountForegroundStartHeight - (int) lerp(0, moveCountForegroundStartHeight, 1);
            graphics2d.drawImage(subImage, panelWidth - 60 - secondStarBackgroundLabel.getPreferredSize().width, 15 + moveCountForegroundStartPosition - 45 + firstStarBackgroundLabel.getPreferredSize().height - starHeight, null);
         }

      }
   }

   private void drawTimerForeground(Graphics g)
   {
      int remainingTime = GameEngine.instance.gameManager.getRemainingTime();
      int timerStartValue = GameEngine.instance.gameManager.getTimerStartValue();
      double f = remainingTime / (double) timerStartValue;
      int timerHeight = timerForegroundStartHeight - (int) lerp(0, timerForegroundStartHeight, f);
      int timerPosition = (int) lerp(timerForegroundStartPosition, timerForegroundStartPosition + timerForegroundLabel.getPreferredSize().height, f);

      BufferedImage subImage = timerForegroundImage.getSubimage(0, 0, 30, timerHeight);

      Graphics2D graphics2d = (Graphics2D) g;
      graphics2d.drawImage(subImage, 40, timerPosition, null);
   }

   InnerGamePanel getInnerGamePanel()
   {
      return innerGamePanel;
   }

   private void updateNumberOfMoves()
   {
      numberLabel.setText(GameEngine.instance.vehicleController.getNumberOfMoves() + "");
   }

   private double lerp(double a, double b, double f)
   {
      return ( b * ( 1.0 - f ) ) + ( a * f );
   }
}