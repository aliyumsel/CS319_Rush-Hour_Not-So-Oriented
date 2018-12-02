package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/endOfLevelPanelBackground.png");

      closeImage = guiManager.LoadImage("src/image/icons/quit.png");
      closeHighlightedImage = guiManager.LoadImage("src/image/icons/quitH.png");

      confirmImage = guiManager.LoadImage("src/image/icons/miniPlay.png");
      confirmHighlightedImage = guiManager.LoadImage("src/image/icons/miniPlayH.png");
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
         public void mouseClicked(MouseEvent e)
         {
            playerName.setText("");
            playerName.setForeground(Color.WHITE);
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

   void requestFocusForTextField()
   {
      playerName.requestFocus();
   }

   private ActionListener actionListener = e ->
   {
      GameEngine.instance.soundManager.buttonClick();
      if ( e.getSource() == close )
      {
         setVisible(false);
      }

      if ( e.getSource() == confirm )
      {
         if (playerName.getText().equals(""))
         {
            return;
         }

         if (playerName.getText().equals("Enter Player name..."))
         {
            return;
         }
         changePlayerPanel.addPlayer(playerName.getText());
         setVisible(false);
         guiManager.setPanelVisible("MainMenu");
      }
   };


}
