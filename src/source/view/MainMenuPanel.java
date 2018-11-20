package source.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.awt.*;
import java.applet.*;

import source.controller.GameEngine;
import source.model.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuPanel extends JPanel {

	private GuiPanelManager guiManager;

	private int index;
	private JLabel heading;
	private JLabel player;
	private JButton cPlayer;
	private JButton play;
	private JButton credits;
	private JButton levels;
	private JButton settings;
	private JButton exit;
	private JButton help;
	//
	private BufferedImage background;
	private BufferedImage playButton;

	//
	public MainMenuPanel(int index, GuiPanelManager _guiManager) {
		super(null);

		guiManager = _guiManager;

		setPreferredSize(new Dimension(763, 468));
		this.index = index;
		createComponents();

		add(heading);
		add(player);
		add(cPlayer);
		add(play);
		add(credits);
		add(levels);
		add(settings);
		add(help);
		add(exit);
		setBoundsOfComponents();
		this.setVisible(true);
		// repaint();
	}

	private void createComponents() {

		ActionListener actionListener = e -> {
			if (e.getSource() == play) {
				GameEngine.instance.gameManager.loadLastLevel();
				guiManager.setPanelVisible("Game");
			}
		};
		background = LoadImage("src/image/goldImage.png"); // just change the png file to change the background
		playButton = LoadImage("src/image/play.png"); // just change the png file to change the background

		Font stdFont = new Font("Calibri", Font.PLAIN, 13);
		Dimension stdDimension = new Dimension(170, 36);
		heading = new JLabel("RUSH HOUR", SwingConstants.CENTER);
		heading.setPreferredSize(new Dimension(294, 65));
		heading.setFont(new Font("Calibri", Font.BOLD + Font.ITALIC, 57));
		player = new JLabel("Player0", SwingConstants.CENTER);
		player.setPreferredSize(new Dimension(600, 32));
		player.setFont(new Font("Calibri", Font.ITALIC, 24));
		cPlayer = new JButton("Change Player");
		cPlayer.setPreferredSize(stdDimension);
		cPlayer.setFont(stdFont);
		
		/////////////////////////////////////////////////////////////////////
		play = new JButton();
		play.addActionListener(actionListener);
		play.setPreferredSize(new Dimension(147, 147));
		play.setFont(new Font("Calibri", Font.BOLD, 28));

		play.setIcon(new ImageIcon(playButton)); //// playbutton bir buffered image o buffered image da a�a��daki
													//// loadImage methoduyla olu�turuluyo hedef butonun size�n� buna
													//// g�re ayarlay�p setIcon methoduna da �mage � att�ktan sonra
													//// buttonu transparan yapmal�s�n�z!!!!
		play.setRolloverIcon(new ImageIcon(background));  //rollover icon will be added here
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		/////////////////////////////////////////////////////////////////////
		credits = new JButton("Credits");
		credits.setPreferredSize(stdDimension);
		credits.setFont(stdFont);
		levels = new JButton("Levels");
		levels.setPreferredSize(stdDimension);
		levels.setFont(stdFont);
		settings = new JButton("Settings");
		settings.setPreferredSize(stdDimension);
		settings.setFont(stdFont);
		help = new JButton(new ImageIcon("src/image/info.png"));
		help.setPreferredSize(new Dimension(48, 48));
		exit = new JButton(new ImageIcon("src/image/exit.png"));
		exit.setPreferredSize(new Dimension(48, 48));

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
		size = cPlayer.getPreferredSize();
		cPlayer.setBounds(296 + insets.left, 189 + insets.top, size.width, size.height);
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

	public void drawBackground(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		graphics2d.drawImage(background, 0, 0, null);

	}
}
