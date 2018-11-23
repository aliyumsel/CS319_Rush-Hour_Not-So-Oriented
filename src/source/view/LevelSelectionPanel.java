package source.view;

import javax.swing.*;

import java.awt.*;

import source.controller.SoundManager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LevelSelectionPanel extends JPanel
{

   private GuiPanelManager guiManager;

   private JButton[] buttonArray;
   private JLabel[] starsArray;
   private JButton rightArrowButton;
   private JButton leftArrowButton;
   private JButton menuButton;

   private BufferedImage background;
   private BufferedImage levelBackground;
   private BufferedImage levelBackgroundHighlighted;
   private BufferedImage rightArrow;
   private BufferedImage rightArrowHighlighted;
   private BufferedImage leftArrow;
   private BufferedImage leftArrowHighlighted;
   private BufferedImage back;
   private BufferedImage backHighlighted;
   private BufferedImage starActive;
   private BufferedImage starInactive;

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
      levelBackground = guiManager.LoadImage("src/image/icons/levelbackground.png");
      levelBackgroundHighlighted = guiManager.LoadImage("src/image/icons/levelbackgroundH.png");
      rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
      rightArrowHighlighted = guiManager.LoadImage("src/image/icons/rightarrowH.png");
      leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
      leftArrowHighlighted = guiManager.LoadImage("src/image/icons/leftarrowH.png");
      back = guiManager.LoadImage("src/image/icons/back.png");
      backHighlighted = guiManager.LoadImage("src/image/icons/backH.png");
      starActive = guiManager.LoadImage("src/image/icons/miniStar.png");
      starInactive = guiManager.LoadImage("src/image/icons/miniStarLocked.png");
   }

   private void createComponents()
   {
      rightArrowButton = UIFactory.createButton(rightArrow, rightArrowHighlighted, "arrow", actionListener);
      leftArrowButton = UIFactory.createButton(leftArrow, leftArrowHighlighted, "arrow", actionListener);
      menuButton = UIFactory.createButton(back, backHighlighted, "square", actionListener);

      //Ba�lant� burdan yap�lacak// updatelerken de bunu level�nfo ya g�re �a��r�p sonra setBounds �a��r�caz
      starsArray = new JLabel[40 * 3]; // variable a at 3 �
      for ( int i = 0; i < starsArray.length; i++ )
      {
         starsArray[i] = UIFactory.createLabelIcon(starInactive, "miniStar");
         add(starsArray[i]);
      }

      buttonArray = new JButton[40]; // variable a at 40 �
      for ( int i = 0; i < buttonArray.length; i++ )
      {
         buttonArray[i] = createLevelButton(i);
         add(buttonArray[i]);
      }
   }

   private void addComponents()
   {
      add(leftArrowButton);
      add(rightArrowButton);
      add(menuButton);
   }

   private void setBoundsOfComponents(int page)
   {
      int gap = 0;
      int pageLength = 12;
      int limit = page * pageLength;
      int starCount = 0;
      for ( int i = 0; i < numberOfLevels * 3; i++ )
      {
         starsArray[i].setVisible(false);
      }
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
            gap += 133;
            buttonArray[i].setBounds(gap, guiManager.findCenter(panelHeight, buttonArray[i]) - 135,
                    buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
            int horizontalStarGap = 10;
            for ( int a = 0; a < 3; a++ )
            {
               if ( a == 0 )
               {
                  //starsArray[starCount].setIcon(new ImageIcon(starActive));;
               }
               starsArray[starCount].setBounds(gap + horizontalStarGap,
                       guiManager.findCenter(panelHeight, starsArray[i]) - 160,
                       starsArray[starCount].getPreferredSize().width,
                       starsArray[starCount].getPreferredSize().height);
               starsArray[starCount].setVisible(true);
               starCount++;
               horizontalStarGap += 30;
            }

         }
         else if ( i > 3 + limit && i < 8 + limit )
         {
            gap += 133;
            buttonArray[i].setBounds(gap, guiManager.findCenter(panelHeight, buttonArray[i]),
                    buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
            int horizontalStarGap = 10;
            for ( int a = 0; a < 3; a++ )
            {
               starsArray[starCount].setBounds(gap + horizontalStarGap,
                       guiManager.findCenter(panelHeight, starsArray[i]) - 25,
                       starsArray[starCount].getPreferredSize().width,
                       starsArray[starCount].getPreferredSize().height);
               starsArray[starCount].setVisible(true);
               starCount++;
               horizontalStarGap += 30;
            }

         }
         else if ( i > 7 + limit && i < 12 + limit )
         {
            gap += 133;
            buttonArray[i].setBounds(gap, 135 + guiManager.findCenter(panelHeight, buttonArray[i]),
                    buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
            int horizontalStarGap = 10;
            for ( int a = 0; a < 3; a++ )
            {
               starsArray[starCount].setBounds(gap + horizontalStarGap,
                       guiManager.findCenter(panelHeight, starsArray[i]) + 110,
                       starsArray[starCount].getPreferredSize().width,
                       starsArray[starCount].getPreferredSize().height);
               starsArray[starCount].setVisible(true);
               starCount++;
               horizontalStarGap += 30;
            }

         }
         buttonArray[i].setVisible(true);
      }
      System.out.println(starCount);

      leftArrowButton.setBounds(5, guiManager.findCenter(panelHeight, leftArrowButton),
              leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
      rightArrowButton.setBounds(panelWidth - 135, guiManager.findCenter(panelHeight, rightArrowButton),
              rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);
      menuButton.setBounds(30, 30, menuButton.getPreferredSize().width, menuButton.getPreferredSize().height);

      Dimension size = popUp.getPreferredSize();
      popUp.setBounds(guiManager.findCenter(panelWidth, popUp), 100, size.width, size.height);
   }

   private JButton createLevelButton(int levelNo)
   {
      JButton temp = UIFactory.createButton(levelBackground, levelBackgroundHighlighted, "level",
              actionListener);
      temp.setText("" + (levelNo + 1));
      temp.setVerticalTextPosition(SwingConstants.CENTER);
      temp.setHorizontalTextPosition(SwingConstants.CENTER);
      temp.setFont(new Font("Odin Rounded", Font.PLAIN, 25));
      temp.revalidate();
      return temp;
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
         // this.setVisible(false);
         guiManager.setPanelVisible("MainMenu");
      }
      else
      {
         for ( int index = 0; index < numberOfLevels; index++ )
         {
            if ( e.getSource() == buttonArray[index] )
            {
               popUp.initialize(index + 1); // buna player objesi de eklenecek
            }
            // GameEngine.instance.gameManager.loadLevel(index+1);;

            popUp.setVisible(true);

            // guiManager.setPanelVisible("Game");
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
