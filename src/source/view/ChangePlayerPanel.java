package source.view;

import javax.swing.*;

import java.awt.*;

import source.controller.GameEngine;
import source.controller.GameManager;
import source.controller.SoundManager;
import source.controller.ThemeManager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangePlayerPanel extends JPanel
{
   private GuiPanelManager guiManager;
   private GameManager gameManager;
   public CreatePlayerPopUp popUp;

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

   private ArrayList<String> playerNameArray;

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

   private int currentPage = 0;
   private int numberOfPlayers;
   private int limit;

   ChangePlayerPanel(GuiPanelManager _guiManager)
   {
      super(null);

      guiManager = _guiManager;
      gameManager = GameManager.instance;
      panelWidth = guiManager.panelWidth;
      panelHeight = guiManager.panelHeight;
      numberOfPlayers = gameManager.playerManager.numberOfPlayers;
      playerNameArray = new ArrayList<>();
      setPreferredSize(new Dimension(panelWidth, panelHeight));

      popUp = new CreatePlayerPopUp(_guiManager, this);
      add(popUp);

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents(0);
      this.setVisible(false);
   }

   public void loadImages()
   {
      background = ThemeManager.instance.getBackgroundImage();
      Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
      background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = background.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

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
      edit = guiManager.LoadImage("src/image/icons/edit.png");
      editH = guiManager.LoadImage("src/image/icons/editH.png");

      popUp.loadImages();
   }

   private void createComponents()
   {
      rightArrowButton = UIFactory.createButton(rightArrow, rightArrowH, "arrow", actionListener);
      leftArrowButton = UIFactory.createButton(leftArrow, leftArrowH, "arrow", actionListener);
      menuButton = UIFactory.createButton(back, backHighlighted, "square", actionListener);
      addButton = UIFactory.createButton(add, addH, "square", actionListener);
      deleteButton1 = UIFactory.createButton(delete, deleteH, "square", actionListener);
      deleteButton2 = UIFactory.createButton(delete, deleteH, "square", actionListener);
      deleteButton3 = UIFactory.createButton(delete, deleteH, "square", actionListener);
      editButton1 = UIFactory.createButton(edit, editH, "square", actionListener);
      editButton2 = UIFactory.createButton(edit, editH, "square", actionListener);
      editButton3 = UIFactory.createButton(edit, editH, "square", actionListener);

      buttonArray = new ArrayList<>();
      numberOfPlayers = GameEngine.instance.playerManager.getPlayers().size();
      for ( int i = 0; i < numberOfPlayers; i++ )
      {
         JButton temp = UIFactory.createPlayerButton(levelBackground, levelBackgroundH, gameManager.playerManager.getPlayers().get(i).getPlayerName(), actionListener);
         playerNameArray.add(gameManager.playerManager.getPlayers().get(i).getPlayerName());
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
      numberOfPlayers = GameEngine.instance.playerManager.getPlayers().size();
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

      currentPage = page;

      int gap = 0;
      int pageLength = 3; //amount of buttons in one page
      limit = page * pageLength;
      boolean showDelete;

      for ( int i = 0; i < numberOfPlayers; i++ )
      {
         showDelete = true;
         if (buttonArray.get(i).getText().equals(GameEngine.instance.playerManager.getCurrentPlayer().getPlayerName()))
         {
            showDelete = false;
         }
         if ( i % 3 == 0 )
         {
            gap = 0;
         }
         if ( i == limit )
         {
            gap = 50;
            buttonArray.get(i).setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)), gap, buttonArray.get(i).getPreferredSize().width,
                    buttonArray.get(i).getPreferredSize().height);
            buttonArray.get(i).setVisible(true);
            if (showDelete) {
               deleteButton1.setVisible(true);
               deleteButton1.setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)) + buttonArray.get(i).getPreferredSize().width + 25, gap + 30,
                       deleteButton1.getPreferredSize().width, deleteButton1.getPreferredSize().height);
            }
            editButton1.setVisible(true);
            editButton1.setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)) - 75, gap + 30, editButton1.getPreferredSize().width,
                    editButton1.getPreferredSize().height);
         }
         else if ( i == 1 + limit )
         {
            gap = 180;
            buttonArray.get(i).setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)), gap, buttonArray.get(i).getPreferredSize().width,
                    buttonArray.get(i).getPreferredSize().height);
            buttonArray.get(i).setVisible(true);
            if (showDelete) {
               deleteButton2.setVisible(true);
               deleteButton2.setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)) + buttonArray.get(i).getPreferredSize().width + 25, gap + 30,
                       deleteButton2.getPreferredSize().width, deleteButton2.getPreferredSize().height);
            }
            editButton2.setVisible(true);
            editButton2.setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)) - 75, gap + 30, editButton2.getPreferredSize().width,
                    editButton2.getPreferredSize().height);
         }
         else if ( i == 2 + limit )
         {
            gap = 310;
            buttonArray.get(i).setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)), gap, buttonArray.get(i).getPreferredSize().width,
                    buttonArray.get(i).getPreferredSize().height);
            buttonArray.get(i).setVisible(true);
            if (showDelete) {
               deleteButton3.setVisible(true);
               deleteButton3.setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)) + buttonArray.get(i).getPreferredSize().width + 25, gap + 30,
                       deleteButton3.getPreferredSize().width, deleteButton3.getPreferredSize().height);
            }
            editButton3.setVisible(true);
            editButton3.setBounds(guiManager.findCenter(panelWidth, buttonArray.get(i)) - 75, gap + 30, editButton3.getPreferredSize().width,
                    editButton3.getPreferredSize().height);
         }

      }
      leftArrowButton.setBounds(5, guiManager.findCenter(panelHeight, leftArrowButton),
              leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
      rightArrowButton.setBounds(panelWidth - 135, guiManager.findCenter(panelHeight, rightArrowButton),
              rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);
      menuButton.setBounds(30, 30, menuButton.getPreferredSize().width,
              menuButton.getPreferredSize().height);
      addButton.setBounds(panelWidth - 30 - addButton.getPreferredSize().width, 30,
              addButton.getPreferredSize().width, menuButton.getPreferredSize().height);

      Dimension size = popUp.getPreferredSize();
      popUp.setBounds(guiManager.findCenter(panelWidth, popUp), 120, size.width, size.height);
   }


   private void selectPlayer(String name)
   {
      gameManager.playerManager.selectPlayer(name);
      ThemeManager.instance.update();
   }

   void addPlayer(String name)
   {
      if ( gameManager.playerManager.createPlayer(name) == 0 )
      {
         numberOfPlayers++;
         JButton temp = UIFactory.createPlayerButton(levelBackground, levelBackgroundH, name, actionListener);
         playerNameArray.add(name);
         buttonArray.add(temp);
         add(temp);

         guiManager.updateImages();
         ThemeManager.instance.update();
      }
   }

   private void deletePlayer(String name)
   {
      int deleteIndex = gameManager.playerManager.deletePlayer(name);
      if ( deleteIndex == -1 )
      {
         return;
      }
      remove(buttonArray.get(buttonArray.size() - 1));
      buttonArray.remove(buttonArray.size() - 1);
      playerNameArray.remove(deleteIndex);
      updatePages();
   }

   void editPlayer(String name)
   {
      int index = GameEngine.instance.playerManager.editPlayer(name, "_"+ name);
      if(index > -1)
      {
         playerNameArray.set(index, "_" + name);
         updatePages();
      }
   }

   private ActionListener actionListener = e ->
   {
      GameEngine.instance.soundManager.buttonClick();

      if ( e.getSource() == leftArrowButton )
      {
         if ( currentPage == 0 )
         {
            if ( numberOfPlayers % 3 == 0 )
            {
               currentPage = numberOfPlayers / 3 - 1;
            }
            else
            {
               currentPage = numberOfPlayers / 3;
            }
         }
         else
         {
            currentPage--;
         }
         System.out.println("currentPage: " + currentPage);
         setBoundsOfComponents(currentPage);
      }
      else if ( e.getSource() == rightArrowButton )
      {
         if ( numberOfPlayers % 3 == 0 )
         {
            if ( currentPage == numberOfPlayers / 3 - 1 )
            {
               currentPage = 0;
            }
            else
            {
               currentPage++;
            }
         }
         else
         {
            if ( currentPage == numberOfPlayers / 3 )
            {
               currentPage = 0;
            }
            else
            {
               currentPage++;
            }
         }
         System.out.println("currentPage: " + currentPage);
         setBoundsOfComponents(currentPage);
      }
      else if ( e.getSource() == menuButton )
      {
         guiManager.setPanelVisible("MainMenu");
         popUp.setVisible(false);
      }
      else if ( e.getSource() == addButton )
      {
         popUp.setVisible(true);
         //needs to be thought about
         //popUp.requestFocusForTextField();
      }
      else if ( e.getSource() == deleteButton1 )
      {
         deletePlayer(playerNameArray.get(limit));
      }
      else if ( e.getSource() == deleteButton2 )
      {
         deletePlayer(playerNameArray.get(limit + 1));
      }
      else if ( e.getSource() == deleteButton3 )
      {
         deletePlayer(playerNameArray.get(limit + 2));
      }
      else if ( e.getSource() == editButton1 )
      {
         editPlayer(playerNameArray.get(limit));
      }
      else if ( e.getSource() == editButton2 )
      {
         editPlayer(playerNameArray.get(limit + 1));
      }
      else if ( e.getSource() == editButton3 )
      {
         editPlayer(playerNameArray.get(limit + 2));
      }
      else
      {
         for ( int i = 0; i < numberOfPlayers; i++ )
         {
            if ( e.getSource() == buttonArray.get(i) )
            {
               selectPlayer(playerNameArray.get(i));
               guiManager.setPanelVisible("MainMenu");
               guiManager.updateImages();
            }
         }
      }
   };

   public void updatePanel()
   {
      this.currentPage = 0;
      setBoundsOfComponents(currentPage);
   }

   private void updatePages()
   {
      numberOfPlayers = GameEngine.instance.playerManager.getPlayers().size();
      updateButtons();

      if ( numberOfPlayers % 3 == 0 )
      {
         if ( currentPage == 0 )
         {
            setBoundsOfComponents(currentPage);
         }
         else if ( currentPage == numberOfPlayers / 3 )
         {
            setBoundsOfComponents(currentPage - 1);
         }
         else
         {
            setBoundsOfComponents(currentPage);
         }

      }
      else
      {
         setBoundsOfComponents(currentPage);
      }
      repaint();
   }

   private void updateButtons()
   {
      for ( int i = 0; i < numberOfPlayers; i++ )
      {
         buttonArray.get(i).setText(playerNameArray.get(i));
      }
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
