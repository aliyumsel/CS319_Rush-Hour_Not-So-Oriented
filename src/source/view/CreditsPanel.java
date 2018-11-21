package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by ASTOD on 11/21/2018.
 */
public class CreditsPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private JLabel heading;
   private JLabel subHeading;
   private JLabel name1;
   private JLabel name2;
   private JLabel name3;
   private JLabel name4;
   private JLabel name5;

   private JButton back;

   private BufferedImage background;
   private BufferedImage title;
   private BufferedImage backButtonImage;
   private BufferedImage backButtonHighlightedImage;

   private int panelWidth = 764;
   private int panelHeight = 468;

   CreditsPanel(GuiPanelManager _guiManager)
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
      background = guiManager.LoadImage("src/image/orange.png");
      title = guiManager.LoadImage("src/image/icons/creditsTitle.png");
      backButtonImage = guiManager.LoadImage("src/image/icons/back.png");
      backButtonHighlightedImage = guiManager.LoadImage("src/image/icons/backH.png");
   }

   private void createComponents()
   {
      back = new JButton();
      guiManager.setupButton(back,backButtonImage,backButtonHighlightedImage,"square",actionListener);

      heading = new JLabel();
      heading.setIcon(new ImageIcon(title));
      heading.setPreferredSize(new Dimension(233, 65));

      subHeading = new JLabel("Developers", SwingConstants.CENTER);
      subHeading.setPreferredSize(new Dimension(300, 50));
      subHeading.setFont(new Font("Odin Rounded", Font.PLAIN, 50));
      subHeading.setForeground(Color.white);
      //subHeading.setBorder(BorderFactory.createLineBorder(Color.cyan, 2));

      name1 = new JLabel("Ahmet Ayrancioglu", SwingConstants.CENTER);
      name1.setPreferredSize(new Dimension(300, 35));
      name1.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      name1.setForeground(Color.white);

      name2 = new JLabel("Deniz Dalkilic", SwingConstants.CENTER);
      name2.setPreferredSize(new Dimension(300, 30));
      name2.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      name2.setForeground(Color.white);

      name3 = new JLabel("Kaan Gonc", SwingConstants.CENTER);
      name3.setPreferredSize(new Dimension(300, 30));
      name3.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      name3.setForeground(Color.white);

      name4 = new JLabel("Ali Yumsel", SwingConstants.CENTER);
      name4.setPreferredSize(new Dimension(300, 30));
      name4.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      name4.setForeground(Color.white);

      name5 = new JLabel("Sina Sahan", SwingConstants.CENTER);
      name5.setPreferredSize(new Dimension(300, 30));
      name5.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      name5.setForeground(Color.white);
   }

   private void addComponents()
   {
      add(heading);
      add(subHeading);
      add(name1);
      add(name2);
      add(name3);
      add(name4);
      add(name5);
      add(back);
   }

   private void setBoundsOfComponents()
   {
      Insets insets = getInsets();

      back.setBounds(30 + insets.left, 30 + insets.top, back.getPreferredSize().width, back.getPreferredSize().height);

      heading.setBounds(guiManager.findCenterHorizontal(panelWidth, heading) + insets.left, 25 + insets.top, heading.getPreferredSize().width, heading.getPreferredSize().height);

      subHeading.setBounds(guiManager.findCenterHorizontal(panelWidth, subHeading) + insets.left, 100 + insets.top, subHeading.getPreferredSize().width, subHeading.getPreferredSize().height);

      name1.setBounds(guiManager.findCenterHorizontal(panelWidth, name1) + insets.left, 175 + insets.top, name1.getPreferredSize().width, name1.getPreferredSize().height);

      name2.setBounds(guiManager.findCenterHorizontal(panelWidth, name1) + insets.left, 225 + insets.top, name2.getPreferredSize().width, name2.getPreferredSize().height);

      name3.setBounds(guiManager.findCenterHorizontal(panelWidth, name1) + insets.left, 275 + insets.top, name3.getPreferredSize().width, name3.getPreferredSize().height);

      name4.setBounds(guiManager.findCenterHorizontal(panelWidth, name1) + insets.left, 325 + insets.top, name4.getPreferredSize().width, name4.getPreferredSize().height);

      name5.setBounds(guiManager.findCenterHorizontal(panelWidth, name1) + insets.left, 375 + insets.top, name5.getPreferredSize().width, name5.getPreferredSize().height);

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

      drawBackground(g); // change the bacground png for changing the background
      // setBackground(Color.WHITE);
   }

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }
}
