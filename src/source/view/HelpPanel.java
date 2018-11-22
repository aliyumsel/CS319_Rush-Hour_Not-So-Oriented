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

   private int panelWidth = 764;
   private int panelHeight = 468;

   HelpPanel(GuiPanelManager _guiManager)
   {
      super(null);

      guiManager = _guiManager;

      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      this.setVisible(false);
   }

   private void loadImages()
   {
//      background = guiManager.LoadImage("src/image/orange.png");
      background = guiManager.LoadImage("src/image/background.png");
      title = guiManager.LoadImage("src/image/icons/howToPlayTitle.png");
      backButtonImage = guiManager.LoadImage("src/image/icons/back.png");
      backButtonHighlightedImage = guiManager.LoadImage("src/image/icons/backH.png");
   }

   private void createComponents()
   {
      back = new JButton();
      guiManager.setupButton(back,backButtonImage,backButtonHighlightedImage,"square",actionListener);

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
      Insets insets = getInsets();

      heading.setBounds(guiManager.findCenterHorizontal(panelWidth, heading) + insets.left, 25 + insets.top, heading.getPreferredSize().width, heading.getPreferredSize().height);

      back.setBounds(30 + insets.left, 30 + insets.top, back.getPreferredSize().width, back.getPreferredSize().height);
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

      drawBackground(g); // change the background png for changing the background
      // setBackground(Color.WHITE);
   }

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }
}
