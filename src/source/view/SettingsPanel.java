package source.view;

import source.controller.*;

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
   private JButton minimalistic;
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
   private BufferedImage simpleImage;
   private BufferedImage simpleHighlightedImage;
   private BufferedImage classicImage;
   private BufferedImage classicHighlightedImage;
   private BufferedImage safariImage;
   private BufferedImage safariHighlightedImage;
   private BufferedImage spaceImage;
   private BufferedImage spaceHighlightedImage;
   private String previousPanel = "";
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

   public void loadImages()
   {
      background = ThemeManager.instance.getBackgroundImage();
      Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
      background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = background.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

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
      simpleImage = guiManager.LoadImage("src/image/icons/simple.png");
      simpleHighlightedImage = guiManager.LoadImage("src/image/icons/simpleH.png");
      classicImage = guiManager.LoadImage("src/image/icons/classic.png");
      classicHighlightedImage = guiManager.LoadImage("src/image/icons/classicH.png");
      safariImage = guiManager.LoadImage("src/image/icons/safari.png");
      safariHighlightedImage = guiManager.LoadImage("src/image/icons/safariH.png");
      spaceImage = guiManager.LoadImage("src/image/icons/space.png");
      spaceHighlightedImage = guiManager.LoadImage("src/image/icons/spaceH.png");
   }

   @SuppressWarnings("Duplicates")
   private void createComponents()
   {
      music = UIFactory.createButton(musicImage, musicHighlightedImage, "square", actionListener);
      sfx = UIFactory.createButton(sfxImage, sfxHighlightedImage, "square", actionListener);
      back = UIFactory.createButton(backButtonImage, backButtonHighlightedImage, "square", actionListener);

      minimalistic = UIFactory.createButton(simpleImage, simpleHighlightedImage, "square", actionListener);
      classic = UIFactory.createButton(classicImage, classicHighlightedImage, "square", actionListener);
      safari = UIFactory.createButton(safariImage, safariHighlightedImage, "square", actionListener);
      space = UIFactory.createButton(spaceImage, spaceHighlightedImage, "square", actionListener);

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

      add(minimalistic);
      add(classic);
      add(safari);
      add(space);
   }

   private void setBoundsOfComponents()
   {
      heading.setBounds(guiManager.findCenter(panelWidth, heading), 25, heading.getPreferredSize().width, heading.getPreferredSize().height);

      volume.setBounds(50, 125, volume.getPreferredSize().width, volume.getPreferredSize().height);
      music.setBounds(75, 175, music.getPreferredSize().width, music.getPreferredSize().height);
      sfx.setBounds(175, 175, sfx.getPreferredSize().width, sfx.getPreferredSize().height);

      back.setBounds(30, 30, back.getPreferredSize().width, back.getPreferredSize().height);

      theme.setBounds(50, 300, theme.getPreferredSize().width, theme.getPreferredSize().height);

      minimalistic.setBounds(75, 350, minimalistic.getPreferredSize().width, minimalistic.getPreferredSize().height);

      classic.setBounds(175, 350, classic.getPreferredSize().width, classic.getPreferredSize().height);

      safari.setBounds(275, 350, safari.getPreferredSize().width, safari.getPreferredSize().height);

      space.setBounds(375, 350, space.getPreferredSize().width, space.getPreferredSize().height);
   }

   void updatePanel(String previousPanel)
   {
      this.previousPanel = previousPanel;
      updateSoundButtons("SFX");
      updateSoundButtons("Music");
      updateThemeButtons();
   }

   @SuppressWarnings("Duplicates")
   private void updateSoundButtons(String type)
   {
      boolean enabled;
      JButton button;

      if ( type.equals("Music") )
      {
         enabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getMusic();
      }
      else if ( type.equals("SFX") )
      {
         enabled = GameEngine.instance.playerManager.getCurrentPlayer().getSettings().getSfx();
      }
      else
      {
         enabled = false;
      }

      if ( type.equals("Music") )
      {
         button = music;
         if ( enabled )
         {
            button.setIcon(new ImageIcon(musicImage));
            button.setRolloverIcon(new ImageIcon(musicHighlightedImage));
            button.setPressedIcon(new ImageIcon(musicHighlightedImage));
         }
         else
         {
            button.setIcon(new ImageIcon(musicOffImage));
            button.setRolloverIcon(new ImageIcon(musicOffHighlightedImage));
            button.setPressedIcon(new ImageIcon(musicOffHighlightedImage));
         }
      }
      else if ( type.equals("SFX") )
      {
         button = sfx;
         if ( enabled )
         {
            button.setIcon(new ImageIcon(sfxImage));
            button.setRolloverIcon(new ImageIcon(sfxHighlightedImage));
            button.setPressedIcon(new ImageIcon(sfxHighlightedImage));
         }
         else
         {
            button.setIcon(new ImageIcon(sfxOffImage));
            button.setRolloverIcon(new ImageIcon(sfxOffHighlightedImage));
            button.setPressedIcon(new ImageIcon(sfxOffHighlightedImage));
         }
      }

   }

   //can be used in updateThemeButtons method below
   private String getThemeNameByButton(JButton button)
   {
      if ( button == minimalistic )
      {
         return "minimalistic";
      }
      if ( button == classic )
      {
         return "classic";
      }
      if ( button == safari )
      {
         return "safari";
      }
      if ( button == space )
      {
         return "space";
      }
      return ""; //should return null
   }

   private void updateThemeButtons()
   {
      ThemeManager themeManager = GameEngine.instance.themeManager;
      if ( !themeManager.minimalistic.isUnlocked() )
      {
         minimalistic.setIcon(new ImageIcon(simpleImage));
      }
      else
      {
         minimalistic.setIcon(new ImageIcon(simpleImage));
      }

      if ( !themeManager.classic.isUnlocked() )
      {
         classic.setIcon(new ImageIcon(classicImage));
      }
      else
      {
         classic.setIcon(new ImageIcon(classicImage));
      }

      if ( !themeManager.safari.isUnlocked() )
      {
         safari.setIcon(new ImageIcon(safariImage));
      }
      else
      {
         safari.setIcon(new ImageIcon(safariImage));
      }

      if ( !themeManager.minimalistic.isUnlocked() )
      {
         space.setIcon(new ImageIcon(spaceImage));
      }
      else
      {
         space.setIcon(new ImageIcon(spaceImage));
      }
   }

   private ActionListener actionListener = e ->
   {
      GameEngine.instance.soundManager.buttonClick();
      if ( e.getSource() == back )
      {
         guiManager.setPanelVisible(previousPanel);
      }
      else if ( e.getSource() == music )
      {
         GameEngine.instance.playerManager.toggleMusic();
         updateSoundButtons("Music");
         GameEngine.instance.soundManager.themeSongToggle();
      }
      else if ( e.getSource() == sfx )
      {
         GameEngine.instance.playerManager.toggleSfx();
         updateSoundButtons("SFX");
         GameEngine.instance.soundManager.effectsToggle();
      }
      else
      {
         String themeName = getThemeNameByButton((JButton) e.getSource());
         int themeStatus = GameEngine.instance.themeManager.getThemeStatus(themeName);
         if ( themeStatus == 1 )
         {
            GameEngine.instance.themeManager.unlockTheme(themeName);
         }
         else if ( themeStatus == 2 )
         {
            GameEngine.instance.themeManager.changeTheme(themeName);
         }
      }
      repaint();
   };

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
}
