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
   private BufferedImage title;
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

   private int panelWidth;
   private int panelHeight;

   SettingsPanel(GuiPanelManager _guiManager)
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
      title = guiManager.LoadImage("src/image/icons/settingsTitle.png");
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
      music = UIFactory.createButton(musicImage,musicHighlightedImage,"square",actionListener);
      sfx = UIFactory.createButton(sfxImage,sfxHighlightedImage,"square",actionListener);
      back = UIFactory.createButton(backButtonImage,backButtonHighlightedImage,"square",actionListener);
      simple = UIFactory.createButton(backButtonImage,backButtonHighlightedImage,"square",actionListener);
      classic = UIFactory.createButton(backButtonImage,backButtonHighlightedImage,"square",actionListener);
      safari = UIFactory.createButton(backButtonImage,backButtonHighlightedImage,"square",actionListener);
      space = UIFactory.createButton(backButtonImage,backButtonHighlightedImage,"square",actionListener);

      heading = new JLabel();
      heading.setIcon(new ImageIcon(title));
      heading.setPreferredSize(new Dimension(263, 81));

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
      heading.setBounds(guiManager.findCenter(panelWidth, heading) , 25 , heading.getPreferredSize().width, heading.getPreferredSize().height);

      volume.setBounds(50, 125, volume.getPreferredSize().width, volume.getPreferredSize().height);
      music.setBounds(75, 175, music.getPreferredSize().width, music.getPreferredSize().height);
      sfx.setBounds(175, 175, sfx.getPreferredSize().width, sfx.getPreferredSize().height);

      back.setBounds(30, 30, back.getPreferredSize().width, back.getPreferredSize().height);

      theme.setBounds(50, 300, theme.getPreferredSize().width, theme.getPreferredSize().height);

      simple.setBounds(75, 350, simple.getPreferredSize().width, simple.getPreferredSize().height);

      classic.setBounds(175, 350, classic.getPreferredSize().width, classic.getPreferredSize().height);

      safari.setBounds(275, 350, safari.getPreferredSize().width, safari.getPreferredSize().height);

      space.setBounds(375, 350, space.getPreferredSize().width, space.getPreferredSize().height);
   }

   private ActionListener actionListener = e ->
   {
      SoundManager.instance.buttonClick();
      if (e.getSource() == back)
      {
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

      drawBackground(g);
      // setBackground(Color.WHITE);
   }

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }
}
