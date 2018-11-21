package source.view;

import source.controller.GameEngine;
import source.controller.Input;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame
{
	public static GuiPanelManager instance;

	private int currentPanelIndex;
	private GamePanel gamePanel;
	private MainMenuPanel mainMenuPanel;
	private CreditsPanel creditsPanel;
	private SettingsPanel settingsPanel;
	private HelpPanel helpPanel;

	public Font odinRounded;

   private Dimension longButtonDimension;
   private Dimension squareButtonDimension;
   private Dimension playButtonDimension;

	public GuiPanelManager() {
		super("Rush Hour");
		instance = this;

      File fontFile = new File("src/fonts/odin.ttf");
      try
      {
         odinRounded = Font.createFont(Font.TRUETYPE_FONT, fontFile);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(odinRounded);
      } catch (FontFormatException | IOException e)
      {
         e.printStackTrace();
      }

      longButtonDimension = new Dimension(171, 37);
      squareButtonDimension = new Dimension(49,55); // evet kare degil biliyom ellemeyin
      playButtonDimension = new Dimension(131,147);

      setLayout(new CardLayout());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenuPanel = new MainMenuPanel(0, this);
		gamePanel = new GamePanel(1, this);
		creditsPanel = new CreditsPanel(this);
		settingsPanel = new SettingsPanel(this);
		helpPanel = new HelpPanel(this);
		add(mainMenuPanel);
		add(gamePanel);
		add(creditsPanel);
		add(settingsPanel);
		add(helpPanel);

		currentPanelIndex = 0;

		setListeners();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pack();

		mainMenuPanel.setVisible(true);
		gamePanel.setVisible(false);

		setVisible(true);
	}

	public JPanel getCurrentPanel()
	{
		return (JPanel) getComponent(currentPanelIndex);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public MainMenuPanel getMainMenuPanel() {
		return mainMenuPanel;
	}

	void setPanelVisible(String panelName) {
		if (panelName.equals("MainMenu")) {
			mainMenuPanel.setVisible(true);
			setContentPane(mainMenuPanel);
		} else if (panelName.equals("Game")) {
			gamePanel.setVisible(true);
			setContentPane(gamePanel);
		}
		else if (panelName.equals("Credits"))
      {
         creditsPanel.setVisible(true);
         setContentPane(creditsPanel);
      }
      else if (panelName.equals("Settings"))
      {
         settingsPanel.setVisible(true);
         setContentPane(settingsPanel);
      }
      else if (panelName.equals("Help"))
      {
         helpPanel.setVisible(true);
         setContentPane(helpPanel);
      }
      else
      {
         System.out.println("Error: Enter valid name");
		}
	}

	void updatePanels() {// burda bi manas�zl�k var main asl�nda hangi panel active se o olmas� gerekiyo sadece gibi ismi
		gamePanel.updatePanel();
	}

	private void setListeners() {
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		gamePanel.getInnerGamePanel().addMouseListener(mouseListener);
	}

   void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage, String buttonType, ActionListener actionListener)
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

   BufferedImage LoadImage(String FileName) {
      BufferedImage image = null;
      try {
         image = ImageIO.read(new File(FileName));
      } catch (IOException e) {
      }
      return image;
   }

   public int findCenterHorizontal(int _panelWidth, Component _component)
   {
      return (_panelWidth - _component.getPreferredSize().width) / 2;
   }
}