package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class HelpPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private JLabel heading;

   private JButton back;

   private BufferedImage background;
   private BufferedImage title;
   private BufferedImage backButtonImage;
   private BufferedImage backButtonHighlightedImage;

   private int panelWidth;
   private int panelHeight;

   HelpPanel(GuiPanelManager _guiManager)
   {
      super(null);

      guiManager = _guiManager;

      panelWidth = guiManager.panelWidth;
      panelHeight = guiManager.panelHeight;

      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      this.setVisible(false);
   }

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/background.png");
      title = guiManager.LoadImage("src/image/icons/howToPlayTitle.png");
      backButtonImage = guiManager.LoadImage("src/image/icons/back.png");
      backButtonHighlightedImage = guiManager.LoadImage("src/image/icons/backH.png");
   }

   private void createComponents()
   {
      back = UIFactory.createButton(backButtonImage,backButtonHighlightedImage,"square",actionListener);

      heading = new JLabel();
      heading.setIcon(new ImageIcon(title));
      heading.setPreferredSize(new Dimension(355, 78));
   }

   private void addComponents()
   {
      add(heading);
      add(back);
   }

   private void setBoundsOfComponents()
   {
      heading.setBounds(guiManager.findCenter(panelWidth, heading) , 25 , heading.getPreferredSize().width, heading.getPreferredSize().height);

      back.setBounds(30 , 30 , back.getPreferredSize().width, back.getPreferredSize().height);
   }

   private ActionListener actionListener = e -> {
      SoundManager.instance.buttonClick();
      if (e.getSource() == back) {
         GameEngine.instance.gameManager.loadLastLevel();
         guiManager.setPanelVisible("MainMenu");
      }
   };

   public void paintComponent(Graphics g) {
      super.paintComponent(g);

      drawBackground(g);
      // setBackground(Color.WHITE);
   }

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }
}
