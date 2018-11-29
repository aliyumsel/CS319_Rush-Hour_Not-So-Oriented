package source.view;

import javax.swing.*;

import java.awt.*;

import source.controller.GameEngine;
import source.controller.SoundManager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LevelSelectionPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private LevelButton[] buttonArray;

   private JButton rightArrowButton;
   private JButton leftArrowButton;
   private JButton menuButton;

   private BufferedImage background;
   private BufferedImage rightArrow;
   private BufferedImage rightArrowHighlighted;
   private BufferedImage leftArrow;
   private BufferedImage leftArrowHighlighted;
   private BufferedImage back;
   private BufferedImage backHighlighted;

   private int panelWidth;
   private int panelHeight;
   private int page = 0;
   private int numberOfLevels = 40;

   private LevelSelectionPopUp popUp;

   LevelSelectionPanel(GuiPanelManager _guiManager)
   {
      super(null);

      guiManager = _guiManager;

      panelWidth = guiManager.panelWidth;
      panelHeight = guiManager.panelHeight;

      setPreferredSize(new Dimension(panelWidth, panelHeight));

      popUp = new LevelSelectionPopUp(_guiManager);
      add(popUp);

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents(page);

      this.setVisible(false);
   }

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/background.png");
      Image scaledImage = background.getScaledInstance(panelWidth,panelHeight,Image.SCALE_DEFAULT);
      background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = background.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

      rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
      rightArrowHighlighted = guiManager.LoadImage("src/image/icons/rightarrowH.png");
      leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
      leftArrowHighlighted = guiManager.LoadImage("src/image/icons/leftarrowH.png");
      back = guiManager.LoadImage("src/image/icons/back.png");
      backHighlighted = guiManager.LoadImage("src/image/icons/backH.png");
   }

   private void createComponents()
   {
      rightArrowButton = UIFactory.createButton(rightArrow, rightArrowHighlighted, "arrow", actionListener);
      leftArrowButton = UIFactory.createButton(leftArrow, leftArrowHighlighted, "arrow", actionListener);
      menuButton = UIFactory.createButton(back, backHighlighted, "square", actionListener);

      buttonArray = new LevelButton[numberOfLevels];
      for ( int i = 0; i < buttonArray.length; i++ )
      {
         buttonArray[i] = UIFactory.createLevelButton(actionListener);
         buttonArray[i].setLevelNo(i);
         //System.out.println("current Player: " + GameEngine.instance.playerManager.getCurrentPlayer().getLevels().get(i).getStars());

         //For initial testing, not final
         add(buttonArray[i]);
      }
      updateButtons();
   }

   private void addComponents()
   {
      add(leftArrowButton);
      add(rightArrowButton);
      add(menuButton);
   }

   private void setBoundsOfComponents(int page)
   {
      leftArrowButton.setBounds(5, guiManager.findCenter(panelHeight, leftArrowButton),
              leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
      rightArrowButton.setBounds(panelWidth - 135, guiManager.findCenter(panelHeight, rightArrowButton),
              rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);

      menuButton.setBounds(30, 30, menuButton.getPreferredSize().width, menuButton.getPreferredSize().height);

      popUp.setBounds(guiManager.findCenter(panelWidth, popUp), 100, popUp.getPreferredSize().width, popUp.getPreferredSize().height);

      for ( int i = 0; i < buttonArray.length; i++ )
      {
         buttonArray[i].setBounds(10, guiManager.findCenter(panelHeight, buttonArray[i]) - 135,
                 buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
      }

      int gap = 0;
      int pageLength = 12;
      int limit = page * pageLength;
      int gapValue = 140;
      for ( int i = 0; i < numberOfLevels; i++ )
      {
         buttonArray[i].setVisible(false);
      }
      for ( int i = limit; i < 12 + limit && i < numberOfLevels; i++ )
      {
         if ( i % 4 == 0 )
         {
            gap = 0;
         }
         if ( i > -1 + limit && i < 4 + limit )
         {
            gap += gapValue;
            buttonArray[i].setBounds(gap, guiManager.findCenter(panelHeight, buttonArray[i]) - 135,
                    buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
         }
         else if ( i > 3 + limit && i < 8 + limit )
         {
            gap += gapValue;
            buttonArray[i].setBounds(gap, guiManager.findCenter(panelHeight, buttonArray[i]),
                    buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
         }
         else if ( i > 7 + limit && i < 12 + limit )
         {
            gap += gapValue;
            buttonArray[i].setBounds(gap, 135 + guiManager.findCenter(panelHeight, buttonArray[i]),
                    buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
         }
         buttonArray[i].setVisible(true);
      }
   }

   private void updateButtons()
   {
      for ( int i = 0; i < buttonArray.length; i++ )
      {
         if ( i < GameEngine.instance.playerManager.getCurrentPlayer().getLevels().size() )
         {
            if ( GameEngine.instance.playerManager.isLevelLocked(i + 1) )
            {
               buttonArray[i].toggleLock(true);
            }
            else
            {
               buttonArray[i].toggleLock(false);
               System.out.println("starAmount: " + GameEngine.instance.playerManager.getCurrentPlayer().getLevels().get(i).getStars());
               buttonArray[i].showStars(GameEngine.instance.playerManager.getCurrentPlayer().getLevels().get(i).getStars()); // from controllers player info
            }
         }
         else
         {
            buttonArray[i].toggleLock(true);
         }
      }
   }

   void updatePanel()
   {
      updateButtons();
   }

   private ActionListener actionListener = e ->
   {
      SoundManager.instance.buttonClick();
      if ( e.getSource() == leftArrowButton )
      {
         if ( page == 0 )
         {
            page = 3;
         }
         else
         {
            page -= 1;
         }

         setBoundsOfComponents(page);
      }
      else if ( e.getSource() == rightArrowButton )
      {
         if ( page == 3 )
         {
            page = 0;
         }
         else
         {
            page += 1;
         }
         setBoundsOfComponents(page);
      }
      else if ( e.getSource() == menuButton )
      {
         guiManager.setPanelVisible("MainMenu");
      }

      //clicked one of the level buttons
      else
      {
         for ( int index = 0; index < numberOfLevels; index++ )
         {
            if ( e.getSource() == buttonArray[index] )
            {
               System.out.println("Destinationlevel: " + index + 1);
               GameEngine.instance.gameManager.loadLevel(index + 1);
               guiManager.setPanelVisible("Game");
               break;
            }
         }
      }

   };

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      drawBackground(g); // change the background png for changing the background

   }

   private void drawBackground(Graphics graphics)
   {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

}
