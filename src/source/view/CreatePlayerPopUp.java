package source.view;

import source.controller.GameEngine;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class CreatePlayerPopUp extends JPanel
{
   private GuiPanelManager guiManager;
   private ChangePlayerPanel changePlayerPanel;

   private JTextField playerName;

   private JButton close;
   private JButton confirm;

   private BufferedImage background;
   private BufferedImage closeImage;
   private BufferedImage closeHighlightedImage;
   private BufferedImage confirmImage;
   private BufferedImage confirmHighlightedImage;

   private int panelWidth = 400;
   private int panelHeight = 250;

   enum Mode
   {
      Edit, New
   }

   private Mode currentMode;
   private String oldName;

   CreatePlayerPopUp(GuiPanelManager _guiManager, ChangePlayerPanel _changePlayerPanel)
   {
      super(null);
      guiManager = _guiManager;
      changePlayerPanel = _changePlayerPanel;
      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      setOpaque(false);
      setVisible(false);
   }

   public void loadImages()
   {
      background = ThemeManager.instance.getPopupBackgroundImage();

      closeImage = guiManager.LoadImage("image/icons/quit.png");
      closeHighlightedImage = guiManager.LoadImage("image/icons/quitH.png");

      confirmImage = guiManager.LoadImage("image/icons/miniPlay.png");
      confirmHighlightedImage = guiManager.LoadImage("image/icons/miniPlayH.png");
   }

   void createComponents()
   {
      playerName = new JTextField();
      playerName.setPreferredSize(new Dimension(300, 100));
      playerName.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      playerName.setHorizontalAlignment(JTextField.CENTER);
      playerName.setForeground(Color.gray);
      playerName.setBorder(BorderFactory.createEmptyBorder());
      playerName.setOpaque(false);
      playerName.setText("Enter Player name...");
      playerName.addMouseListener(new MouseAdapter()
      {
         @Override
         public void mousePressed(MouseEvent e)
         {
            if ( currentMode == Mode.New )
            {
               playerName.setText("");
               playerName.setForeground(Color.WHITE);
            }
            else
            {
               playerName.setForeground(Color.WHITE);
            }
         }
      });

      close = UIFactory.createButton(closeImage, closeHighlightedImage, "square", actionListener);
      confirm = UIFactory.createButton(confirmImage, confirmHighlightedImage, "square", actionListener);
   }

   private void addComponents()
   {
      add(playerName);
      add(close);
      add(confirm);
   }

   private void setBoundsOfComponents()
   {
      playerName.setBounds(guiManager.findCenter(panelWidth, playerName), 20, playerName.getPreferredSize().width, playerName.getPreferredSize().height);

      close.setBounds(guiManager.findCenter(panelWidth, close) - 120, 150, close.getPreferredSize().width, close.getPreferredSize().height);
      confirm.setBounds(guiManager.findCenter(panelWidth, close) + 120, 150, close.getPreferredSize().width, close.getPreferredSize().height);
   }

   void updatePanel()
   {
      //reset the panel when being set visible
   }

   private void updateDefaultTextField(String defaultText)
   {
      playerName.setText(defaultText);
      playerName.setForeground(Color.gray);
   }

   void showPopUp(Mode mode)
   {
      setVisible(true);
      currentMode = mode;
      if ( mode == Mode.New )
      {
         updateDefaultTextField("Enter player name...");
      }
   }

   void showPopUp(Mode mode, String playerName)
   {
      setVisible(true);
      currentMode = mode;
      updateDefaultTextField(playerName);
      oldName = playerName;
   }

   void hidePopUp()
   {
      setVisible(false);
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

   private ActionListener actionListener = e ->
   {
      GameEngine.instance.soundManager.buttonClick();
      if ( e.getSource() == close )
      {
         setVisible(false);
         playerName.setText("Enter Player name...");
         playerName.setForeground(Color.gray);
         requestFocus();
      }

      if ( e.getSource() == confirm )
      {
         if ( currentMode == Mode.New )
         {
            if ( playerName.getText().equals("") )
            {
               return;
            }

            if ( playerName.getText().equals("Enter Player name...") )
            {
               return;
            }
            changePlayerPanel.addPlayer(playerName.getText());
            playerName.setText("Enter Player name...");
            playerName.setForeground(Color.gray);
            setVisible(false);
            guiManager.setPanelVisible("MainMenu");
            requestFocus();
         }
         else
         {
            changePlayerPanel.editPlayer(oldName, playerName.getText());
            playerName.setText("Enter Player name...");
            playerName.setForeground(Color.gray);
            setVisible(false);
            guiManager.setPanelVisible("ChangePlayer");
         }

      }
      changePlayerPanel.hideBlackBackground();
   };


}
