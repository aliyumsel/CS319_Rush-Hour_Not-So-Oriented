package source.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

import source.controller.GameEngine;
import source.controller.SoundManager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MainMenuPanel extends JPanel {

	private GuiPanelManager guiManager;

	//private int index;
	private JLabel heading;
	private JLabel player;
	private JButton changePlayer;
	private JButton play;
	private JButton credits;
	private JButton levels;
	private JButton settings;
	private JButton exit;
	private JButton help;
	//
	private BufferedImage background;
	private BufferedImage title;
	private BufferedImage playButtonImage;
   private BufferedImage playButtonImageHighlighted;
	private BufferedImage creditsButtonImage;
	private BufferedImage creditsButtonHighlightedImage;
   private BufferedImage changePlayerButtonImage;
   private BufferedImage changePlayerButtonHighlightedImage;
   private BufferedImage helpButtonImage;
   private BufferedImage helpButtonHighlightedImage;
   private BufferedImage levelsButtonImage;
   private BufferedImage levelsButtonHighlightedImage;
   private BufferedImage exitButtonImage;
   private BufferedImage exitButtonHighlightedImage;
   private BufferedImage settingsButtonImage;
   private BufferedImage settingsButtonHighlightedImage;
   //
   //   private Dimension longButtonDimension;
   //   private Dimension squareButtonDimension;
   //   private Dimension playButtonDimension;
	//

   private int panelWidth = 764;
   private int panelHeight = 468;

   MainMenuPanel(int index, GuiPanelManager _guiManager)
   {
		super(null);

		guiManager = _guiManager;

		setPreferredSize(new Dimension(panelWidth, panelHeight));

		loadImages();

		createComponents();
		add(heading);
		add(player);
		add(changePlayer);
		add(play);
		add(credits);
		add(levels);
		add(settings);
		add(help);
		add(exit);
		setBoundsOfComponents();
		this.setVisible(true);

	}

	private void loadImages()
   {
      background = guiManager.LoadImage("src/image/orange.png");

      title = guiManager.LoadImage("src/image/icons/title.png");

      playButtonImage = guiManager.LoadImage("src/image/icons/play.png");
      playButtonImageHighlighted = guiManager.LoadImage("src/image/icons/playH.png");

      creditsButtonImage = guiManager.LoadImage("src/image/icons/credits.png");
      creditsButtonHighlightedImage = guiManager.LoadImage("src/image/icons/creditsH.png");

      changePlayerButtonImage = guiManager.LoadImage("src/image/icons/changePlayer.png");
      changePlayerButtonHighlightedImage = guiManager.LoadImage("src/image/icons/changePlayerH.png");

      helpButtonImage = guiManager.LoadImage("src/image/icons/help.png");
      helpButtonHighlightedImage = guiManager.LoadImage("src/image/icons/helpH.png");

      levelsButtonImage = guiManager.LoadImage("src/image/icons/levels.png");
      levelsButtonHighlightedImage = guiManager.LoadImage("src/image/icons/levelsH.png");

      exitButtonImage = guiManager.LoadImage("src/image/icons/quit.png");
      exitButtonHighlightedImage = guiManager.LoadImage("src/image/icons/quitH.png");

      settingsButtonImage = guiManager.LoadImage("src/image/icons/settings.png");
      settingsButtonHighlightedImage = guiManager.LoadImage("src/image/icons/settingsH.png");
   }

	private void createComponents()
   {
		heading = new JLabel();
		heading.setIcon(new ImageIcon(title));
		heading.setPreferredSize(new Dimension(295, 58));

		player = new JLabel("Player1", SwingConstants.CENTER);
		player.setPreferredSize(new Dimension(100, 32));
		player.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
		player.setForeground(Color.white);

      changePlayer = new JButton();
      play = new JButton();
      credits = new JButton();
      levels = new JButton();
      settings = new JButton();
      help = new JButton();
      exit = new JButton();

      guiManager.setupButton(changePlayer,changePlayerButtonImage,changePlayerButtonHighlightedImage,"long",actionListener);
      guiManager.setupButton(play,playButtonImage,playButtonImageHighlighted,"play",actionListener);
      guiManager.setupButton(credits,creditsButtonImage,creditsButtonHighlightedImage,"long",actionListener);
      guiManager.setupButton(levels,levelsButtonImage,levelsButtonHighlightedImage,"long",actionListener);
      guiManager.setupButton(settings,settingsButtonImage,settingsButtonHighlightedImage,"long",actionListener);
      guiManager.setupButton(help,helpButtonImage,helpButtonHighlightedImage,"square",actionListener);
      guiManager.setupButton(exit,exitButtonImage,exitButtonHighlightedImage,"square",actionListener);
	}

//	private void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage, String buttonType)
//   {
//      button.addActionListener(actionListener);
//      if ( buttonType.equals("long") )
//      {
//         button.setPreferredSize(longButtonDimension);
//      }
//      else if (buttonType.equals("square"))
//      {
//         button.setPreferredSize(squareButtonDimension);
//      }
//      else if (buttonType.equals("play"))
//      {
//         button.setPreferredSize(playButtonDimension);
//      }
//      else
//      {
//         System.out.println("Error: Enter valid String");
//      }
//
//      button.setIcon(new ImageIcon(normalImage));
//      button.setRolloverIcon(new ImageIcon(highlightedImage));
//      button.setPressedIcon(new ImageIcon(highlightedImage));
//      button.setOpaque(false);
//      button.setContentAreaFilled(false);
//      button.setBorderPainted(false);
//      button.setFocusable(false);
//   }

	private void setBoundsOfComponents()
   {
		Insets insets = getInsets();

		help.setBounds(30 + insets.left, 30 + insets.top, help.getPreferredSize().width, help.getPreferredSize().height);

		exit.setBounds(panelWidth - 30 - exit.getPreferredSize().width + insets.left, 30 + insets.top, exit.getPreferredSize().width, exit.getPreferredSize().height);

		heading.setBounds(guiManager.findCenterHorizontal(panelWidth, heading) + insets.left, 25 + insets.top, heading.getPreferredSize().width, heading.getPreferredSize().height);

		player.setBounds(guiManager.findCenterHorizontal(panelWidth,player) + insets.left, 130 + insets.top, player.getPreferredSize().width, player.getPreferredSize().height);

		changePlayer.setBounds(guiManager.findCenterHorizontal(panelWidth,changePlayer) + insets.left, 175 + insets.top, changePlayer.getPreferredSize().width, changePlayer.getPreferredSize().height);

		play.setBounds(guiManager.findCenterHorizontal(panelWidth, play) + insets.left, 230 + insets.top, play.getPreferredSize().width, play.getPreferredSize().height);

		credits.setBounds(guiManager.findCenterHorizontal(panelWidth, credits) - 225 + insets.left, 395 + insets.top, credits.getPreferredSize().width, credits.getPreferredSize().height);

		levels.setBounds(guiManager.findCenterHorizontal(panelWidth, levels) + insets.left, 395 + insets.top, levels.getPreferredSize().width, levels.getPreferredSize().height);

		settings.setBounds(guiManager.findCenterHorizontal(panelWidth, settings) + 225 + insets.left, 395 + insets.top, settings.getPreferredSize().width, settings.getPreferredSize().height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBackground(g); // change the bacground png for changing the background
		// setBackground(Color.WHITE);
	}

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

   private ActionListener actionListener = e ->
   {
      SoundManager.instance.buttonClick();
      if (e.getSource() == play) {
         GameEngine.instance.gameManager.loadLastLevel();
         guiManager.setPanelVisible("Game");
      }

      if (e.getSource() == credits)
      {
         guiManager.setPanelVisible("Credits");
      }

      if (e.getSource() == settings)
      {
         guiManager.setPanelVisible("Settings");
      }

      if (e.getSource() == help)
      {
         guiManager.setPanelVisible("Help");
      }

      if (e.getSource() == exit)
      {
         System.exit(0);
      }
   };
}
