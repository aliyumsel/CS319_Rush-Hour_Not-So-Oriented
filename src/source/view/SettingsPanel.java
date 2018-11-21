package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SettingsPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private JLabel heading;
   private JLabel volume;
   private JLabel theme;

   private JButton music;
   private JButton sfx;

   private JButton back;
   private JButton simple;
   private JButton classic;
   private JButton safari;
   private JButton space;

   private BufferedImage background;
   private BufferedImage backButtonImage;
   private BufferedImage backButtonHighlightedImage;
   private BufferedImage musicImage;
   private BufferedImage musicHighlightedImage;
   private BufferedImage musicOffImage;
   private BufferedImage musicOffHighlightedImage;
   private BufferedImage sfxImage;
   private BufferedImage sfxHighlightedImage;
   private BufferedImage sfxOffImage;
   private BufferedImage sfxOffHighlightedImage;

   private int panelWidth = 764;
   private int panelHeight = 468;

   SettingsPanel(GuiPanelManager _guiManager)
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
      backButtonImage = guiManager.LoadImage("src/image/icons/back.png");
      backButtonHighlightedImage = guiManager.LoadImage("src/image/icons/backH.png");
      musicImage = guiManager.LoadImage("src/image/icons/musicon.png");
      musicHighlightedImage = guiManager.LoadImage("src/image/icons/musiconH.png");
      musicOffImage = guiManager.LoadImage("src/image/icons/musicoff.png");
      musicOffHighlightedImage = guiManager.LoadImage("src/image/icons/musicoffH.png");
      sfxImage = guiManager.LoadImage("src/image/icons/soundon.png");
      sfxHighlightedImage = guiManager.LoadImage("src/image/icons/soundonH.png");
      sfxOffImage = guiManager.LoadImage("src/image/icons/soundoff.png");
      sfxOffHighlightedImage = guiManager.LoadImage("src/image/icons/soundoffH.png");
   }

   private void createComponents()
   {
      music = new JButton();
      sfx = new JButton();
      back = new JButton();
      simple = new JButton();
      classic = new JButton();
      safari = new JButton();
      space = new JButton();

      guiManager.setupButton(back,backButtonImage,backButtonHighlightedImage,"square",actionListener);
      guiManager.setupButton(simple,backButtonImage,backButtonHighlightedImage,"square",actionListener);
      guiManager.setupButton(classic,backButtonImage,backButtonHighlightedImage,"square",actionListener);
      guiManager.setupButton(safari,backButtonImage,backButtonHighlightedImage,"square",actionListener);
      guiManager.setupButton(space,backButtonImage,backButtonHighlightedImage,"square",actionListener);
      guiManager.setupButton(music,musicImage,musicHighlightedImage,"square",actionListener);
      guiManager.setupButton(sfx,sfxImage,sfxHighlightedImage,"square",actionListener);

      heading = new JLabel("Settings", SwingConstants.CENTER);
      heading.setPreferredSize(new Dimension(300, 90));
      heading.setFont(new Font("Odin Rounded", Font.PLAIN, 75));
      heading.setForeground(Color.white);

      volume = new JLabel("Volume", SwingConstants.CENTER);
      volume.setPreferredSize(new Dimension(150, 30));
      volume.setFont(new Font("Odin Rounded", Font.PLAIN, 40));
      volume.setForeground(Color.white);

      theme = new JLabel("Theme", SwingConstants.CENTER);
      theme.setPreferredSize(new Dimension(150, 30));
      theme.setFont(new Font("Odin Rounded", Font.PLAIN, 40));
      theme.setForeground(Color.white);
   }

   private void addComponents()
   {
      System.out.print("");
      add(back);

      add(music);
      add(sfx);

      add(heading);
      add(volume);
      add(theme);

      add(simple);
      add(classic);
      add(safari);
      add(space);
   }

   private void setBoundsOfComponents()
   {
      Insets insets = getInsets();

      music.setBounds(75 + insets.left, 200 + insets.top, music.getPreferredSize().width, music.getPreferredSize().height);

      sfx.setBounds(175 + insets.left, 200 + insets.top, sfx.getPreferredSize().width, sfx.getPreferredSize().height);

      back.setBounds(30 + insets.left, 30 + insets.top, back.getPreferredSize().width, back.getPreferredSize().height);

      heading.setBounds(guiManager.findCenterHorizontal(panelWidth, heading) + insets.left, 25 + insets.top, heading.getPreferredSize().width, heading.getPreferredSize().height);

      volume.setBounds(50 + insets.left, 150 + insets.top, volume.getPreferredSize().width, volume.getPreferredSize().height);

      theme.setBounds(50 + insets.left, 300 + insets.top, theme.getPreferredSize().width, theme.getPreferredSize().height);

      simple.setBounds(75 + insets.left, 350 + insets.top, simple.getPreferredSize().width, simple.getPreferredSize().height);

      classic.setBounds(175 + insets.left, 350 + insets.top, classic.getPreferredSize().width, classic.getPreferredSize().height);

      safari.setBounds(275 + insets.left, 350 + insets.top, safari.getPreferredSize().width, safari.getPreferredSize().height);

      space.setBounds(375 + insets.left, 350 + insets.top, space.getPreferredSize().width, space.getPreferredSize().height);

   }

   private ActionListener actionListener = e ->
   {
      if (e.getSource() == back)
      {
         GameEngine.instance.gameManager.loadLastLevel();
         guiManager.setPanelVisible("MainMenu");
      }
      else if (e.getSource() == music)
      {
         //change the icon according to the music state which will be stored in somewhere in controllers
         music.setIcon(new ImageIcon(musicOffImage));
         music.setRolloverIcon(new ImageIcon(musicOffHighlightedImage));
      }
      else if (e.getSource() == sfx)
      {
         //change the icon according to the music state which will be stored in somewhere in controllers
         sfx.setIcon(new ImageIcon(sfxOffImage));
         sfx.setRolloverIcon(new ImageIcon(sfxOffHighlightedImage));
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
