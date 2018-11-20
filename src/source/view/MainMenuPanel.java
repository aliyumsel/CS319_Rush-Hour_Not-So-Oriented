package source.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

import source.controller.GameEngine;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
   private Dimension longButtonDimension;
   private Dimension squareButtonDimension;
   private Dimension playButtonDimension;

	//
   MainMenuPanel(int index, GuiPanelManager _guiManager) {
		super(null);

		guiManager = _guiManager;

		setPreferredSize(new Dimension(763, 468));
		//this.index = index;

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

      longButtonDimension = new Dimension(171, 37);
      squareButtonDimension = new Dimension(49,55); // evet kare degil biliyom ellemeyin
      playButtonDimension = new Dimension(131,147);

	}

	private void loadImages()
   {
      background = LoadImage("src/image/goldImage.png");

      title = LoadImage("src/image/icons/title.png");

      playButtonImage = LoadImage("src/image/icons/play.png");
      playButtonImageHighlighted = LoadImage("src/image/icons/playH.png");

      creditsButtonImage = LoadImage("src/image/icons/credits.png");
      creditsButtonHighlightedImage = LoadImage("src/image/icons/creditsH.png");

      changePlayerButtonImage = LoadImage("src/image/icons/changePlayer.png");
      changePlayerButtonHighlightedImage = LoadImage("src/image/icons/changePlayerH.png");

      helpButtonImage = LoadImage("src/image/icons/help.png");
      helpButtonHighlightedImage = LoadImage("src/image/icons/helpH.png");

      levelsButtonImage = LoadImage("src/image/icons/levels.png");
      levelsButtonHighlightedImage = LoadImage("src/image/icons/levelsH.png");

      exitButtonImage = LoadImage("src/image/icons/quit.png");
      exitButtonHighlightedImage = LoadImage("src/image/icons/quitH.png");

      settingsButtonImage = LoadImage("src/image/icons/settings.png");
      settingsButtonHighlightedImage = LoadImage("src/image/icons/settingsH.png");
   }

   private ActionListener actionListener = e -> {
      if (e.getSource() == play) {
         GameEngine.instance.gameManager.loadLastLevel();
         guiManager.setPanelVisible("Game");
      }
   };

	private void createComponents()
   {
		//Font stdFont = new Font("Calibri", Font.PLAIN, 13);

		heading = new JLabel();
		heading.setIcon(new ImageIcon(title));
		heading.setPreferredSize(new Dimension(295, 58));

		player = new JLabel("Player1", SwingConstants.CENTER);
		player.setPreferredSize(new Dimension(600, 32));
		player.setFont(new Font("Calibri", Font.ITALIC, 24));

      changePlayer = new JButton();
      play = new JButton();
      credits = new JButton();
      levels = new JButton();
      settings = new JButton();
      help = new JButton();
      exit = new JButton();

      setupButton(changePlayer,changePlayerButtonImage,changePlayerButtonHighlightedImage,"long");
      setupButton(play,playButtonImage,playButtonImageHighlighted,"play");
      setupButton(credits,creditsButtonImage,creditsButtonHighlightedImage,"long");
      setupButton(levels,levelsButtonImage,levelsButtonHighlightedImage,"long");
      setupButton(settings,settingsButtonImage,settingsButtonHighlightedImage,"long");
      setupButton(help,helpButtonImage,helpButtonHighlightedImage,"square");
      setupButton(exit,exitButtonImage,exitButtonHighlightedImage,"square");
	}

	private void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage, String buttonType)
   {
      button.addActionListener(actionListener);
      if ( buttonType.equals("long") )
      {
         button.setPreferredSize(longButtonDimension);
      }
      else if (buttonType.equals("square"))
      {
         button.setPreferredSize(squareButtonDimension);
      }
      else if (buttonType.equals("play"))
      {
         button.setPreferredSize(playButtonDimension);
      }
      else
      {
         System.out.println("Error: Enter valid String");
      }

      button.setIcon(new ImageIcon(normalImage));
      button.setRolloverIcon(new ImageIcon(highlightedImage));
      button.setPressedIcon(new ImageIcon(highlightedImage));
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setFocusable(false);
   }

	private void setBoundsOfComponents() {
		Insets insets = getInsets();
		Dimension size = help.getPreferredSize();
		help.setBounds(32 + insets.left, 46 + insets.top, size.width, size.height);
		size = exit.getPreferredSize();
		exit.setBounds(680 + insets.left, 46 + insets.top, size.width, size.height);
		size = heading.getPreferredSize();
		heading.setBounds(234 + insets.left, 67 + insets.top, size.width, size.height);
		size = player.getPreferredSize();
		player.setBounds(80 + insets.left, 145 + insets.top, size.width, size.height);
		size = changePlayer.getPreferredSize();
		changePlayer.setBounds(296 + insets.left, 189 + insets.top, size.width, size.height);
		size = play.getPreferredSize();
		play.setBounds(305 + insets.left, 230 + insets.top, size.width, size.height);
		size = credits.getPreferredSize();
		credits.setBounds(32 + insets.left, 388 + insets.top, size.width, size.height);
		size = levels.getPreferredSize();
		levels.setBounds(296 + insets.left, 388 + insets.top, size.width, size.height);
		size = settings.getPreferredSize();
		settings.setBounds(558 + insets.left, 388 + insets.top, size.width, size.height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBackground(g); // change the bacground png for changing the background
		// setBackground(Color.WHITE);
	}

	private BufferedImage LoadImage(String FileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(FileName));
		} catch (IOException e) {
		}
		return image;
	}

	private void drawBackground(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		graphics2d.drawImage(background, 0, 0, null);

	}
}
