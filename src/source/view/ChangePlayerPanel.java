package source.view;

import javax.swing.*;

import java.awt.*;

import source.controller.SoundManager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ChangePlayerPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private LevelSelectionPopUp popUp;

   private ArrayList<JButton> buttonArray;
   private JButton rightArrowButton;
   private JButton leftArrowButton;
   private JButton menuButton;
   private JButton addButton;
   private JButton deleteButton1;
   private JButton deleteButton2;
   private JButton deleteButton3;
   private JButton editButton1;
   private JButton editButton2;
   private JButton editButton3;

   private BufferedImage background;
   private BufferedImage levelBackground;
   private BufferedImage levelBackgroundH;
   private BufferedImage rightArrow;
   private BufferedImage leftArrow;
   private BufferedImage leftArrowH;
   private BufferedImage rightArrowH;
   private BufferedImage add;
   private BufferedImage addH;
   private BufferedImage edit;
   private BufferedImage editH;
   private BufferedImage delete;
   private BufferedImage deleteH;
   private BufferedImage back;
   private BufferedImage backHighlighted;

   private int panelWidth = 764;
   private int panelHeight = 468;
   private int page = 0;
   private int numberOfPlayers = 5;

   public ChangePlayerPanel(GuiPanelManager _guiManager)
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
      setBoundsOfComponents(0);
      this.setVisible(false);
   }

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/background.png");
      levelBackground = guiManager.LoadImage("src/image/icons/playerSelection.png");
      levelBackgroundH = guiManager.LoadImage("src/image/icons/playerSelectionH.png");
      rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
      leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
      rightArrowH = guiManager.LoadImage("src/image/icons/rightarrowH.png");
      leftArrowH = guiManager.LoadImage("src/image/icons/leftarrowH.png");
      back = guiManager.LoadImage("src/image/icons/back.png");
      backHighlighted = guiManager.LoadImage("src/image/icons/backH.png");
      add = guiManager.LoadImage("src/image/icons/addPlayer.png");
      addH = guiManager.LoadImage("src/image/icons/addPlayerH.png");
      delete = guiManager.LoadImage("src/image/icons/quit.png");
      deleteH = guiManager.LoadImage("src/image/icons/quitH.png");
      edit = guiManager.LoadImage("src/image/icons/help.png");
      editH = guiManager.LoadImage("src/image/icons/helpH.png");
   }

   private void createComponents()
   {
      rightArrowButton = UIFactory.createButton( rightArrow, rightArrowH, "arrow", actionListener);
      leftArrowButton = UIFactory.createButton( leftArrow, leftArrowH, "arrow", actionListener);
      menuButton = UIFactory.createButton( back, backHighlighted, "square", actionListener);
      addButton = UIFactory.createButton( add, addH, "square", actionListener);
      deleteButton1 = UIFactory.createButton( delete, deleteH, "square", actionListener);
      deleteButton2 = UIFactory.createButton( delete, deleteH, "square", actionListener);
      deleteButton3 = UIFactory.createButton( delete, deleteH, "square", actionListener);
      editButton1 = UIFactory.createButton( edit, editH, "square", actionListener);
      editButton2 = UIFactory.createButton( edit, editH, "square", actionListener);
      editButton3 = UIFactory.createButton( edit, editH, "square", actionListener);

      buttonArray = new ArrayList<JButton>();
      for ( int i = 0; i < numberOfPlayers; i++ )
      {
         //sanirim bu methodu kullanabilirz player button yaratmak icin sey yapariz string degilde Player objesi alir
         JButton temp = createPlayerButton("denizDalkilic");
         buttonArray.add(temp);
         add(buttonArray.get(i));
      }
   }

   private void addComponents()
   {
      this.add(leftArrowButton);
      add(rightArrowButton);
      add(menuButton);
      add(addButton);
      add(deleteButton1);
      add(deleteButton2);
      add(deleteButton3);
      add(editButton1);
      add(editButton2);
      add(editButton3);
   }

   private void setBoundsOfComponents(int page)
   {
      Insets insets = getInsets();
      int gap = 0;
      int pageLength = 3;
      int limit = page * pageLength;

      for ( int i = 0; i < numberOfPlayers; i++ )
      {
         buttonArray.get(i).setVisible(false);
      }
      deleteButton1.setVisible(false);
      deleteButton2.setVisible(false);
      deleteButton3.setVisible(false);
      editButton1.setVisible(false);
      editButton2.setVisible(false);
      editButton3.setVisible(false);

      for ( int i = 0; i < numberOfPlayers; i++ )
      {
         if ( i % 3 == 0 )
         {
            gap = 0;
         }
         if ( i == 0 + limit )
         {
            gap = 50;
            buttonArray.get(i).setBounds(235 + insets.left, gap, buttonArray.get(i).getPreferredSize().width,
                    buttonArray.get(i).getPreferredSize().height);
            buttonArray.get(i).setVisible(true);
            deleteButton1.setVisible(true);
            deleteButton1.setBounds(565 + insets.left, gap + 30 + insets.top, deleteButton1.getPreferredSize().width,
                    deleteButton1.getPreferredSize().height);
            editButton1.setVisible(true);
            editButton1.setBounds(160 + insets.left, gap + 30 + insets.top, editButton1.getPreferredSize().width,
                    editButton1.getPreferredSize().height);
         }
         else if ( i == 1 + limit )
         {
            gap = 180;
            buttonArray.get(i).setBounds(235 + insets.left, gap, buttonArray.get(i).getPreferredSize().width,
                    buttonArray.get(i).getPreferredSize().height);
            buttonArray.get(i).setVisible(true);
            deleteButton2.setVisible(true);
            deleteButton2.setBounds(565 + insets.left, gap + 30 + insets.top, deleteButton2.getPreferredSize().width,
                    deleteButton2.getPreferredSize().height);
            editButton2.setVisible(true);
            editButton2.setBounds(160 + insets.left, gap + 30 + insets.top, editButton2.getPreferredSize().width,
                    editButton2.getPreferredSize().height);
         }
         else if ( i == 2 + limit )
         {
            gap = 310;
            buttonArray.get(i).setBounds(235 + insets.left, gap, buttonArray.get(i).getPreferredSize().width,
                    buttonArray.get(i).getPreferredSize().height);
            buttonArray.get(i).setVisible(true);
            deleteButton3.setVisible(true);
            deleteButton3.setBounds(565 + insets.left, gap + 30 + insets.top, deleteButton3.getPreferredSize().width,
                    deleteButton3.getPreferredSize().height);
            editButton3.setVisible(true);
            editButton3.setBounds(160 + insets.left, gap + 30 + insets.top, editButton3.getPreferredSize().width,
                    editButton3.getPreferredSize().height);
         }

      }
      leftArrowButton.setBounds(5, guiManager.findCenter(panelHeight, leftArrowButton) + insets.top,
              leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
      rightArrowButton.setBounds(panelWidth - 135,
              guiManager.findCenter(panelHeight, rightArrowButton) + insets.top,
              rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);
      menuButton.setBounds(30 + insets.left, 30 + insets.top, menuButton.getPreferredSize().width,
              menuButton.getPreferredSize().height);
      addButton.setBounds(panelWidth - 30 - addButton.getPreferredSize().width, 30 + insets.top, addButton.getPreferredSize().width,
              menuButton.getPreferredSize().height);

      Dimension size = popUp.getPreferredSize();
      popUp.setBounds(guiManager.findCenter(panelWidth, popUp), 100 + insets.top, size.width, size.height);
      // popUp.setVisible(true); in order to test pop up panel design remove the
   }

   private JButton createPlayerButton(String playerName)
   {
      JButton temp = UIFactory.createButton(levelBackground, levelBackgroundH,  "player", actionListener);
      temp.setText(playerName);
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
            page = numberOfPlayers / 3;
         }
         else
         {
            page -= 1;
         }
         setBoundsOfComponents(page);
      }
      else if ( e.getSource() == rightArrowButton )
      {
         if ( page == numberOfPlayers / 3 )
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
      // If the user clicks one of the level buttons
      else
      {
         for ( int index = 0; index < numberOfPlayers; index++ )
         {
            if ( e.getSource() == buttonArray.get(index) )
            {
               popUp.initialize(index + 1); // buna player objesi de eklenecek
            }
            popUp.setVisible(true);
         }
      }
   };

   public void reset()
   {
      this.page = 0;
      setBoundsOfComponents(page);
   }

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
