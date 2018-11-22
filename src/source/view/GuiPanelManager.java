package source.view;

import source.controller.GameEngine;
import source.controller.Input;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.awt.AWTUtilities;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame {
	public static GuiPanelManager instance;

	private int currentPanelIndex;
	private GamePanel gamePanel;
	private MainMenuPanel mainMenuPanel;
	private CreditsPanel creditsPanel;
	private SettingsPanel settingsPanel;
	private LevelSelectionPanel levelSelectionPanel;
	private HelpPanel helpPanel;
	private ChangePlayerPanel playerPanel;
	private JPanel targetPanel;
	// private int panelWidth = 468;
	// private boolean transition = false;

   int panelWidth;
   int panelHeight;

   private BufferedImage cursorImage;

	public Font odinRounded;

	private Dimension longButtonDimension, arrowButtonDimension, squareButtonDimension, playButtonDimension,
			levelButtonDimension, playerButtonDimension;

	public GuiPanelManager()
   {
		super("Rush Hour");
		setUndecorated(true);

      instance = this;

		//setShape(new RoundRectangle2D.Double(0, 0, 764, 492, 51, 51));

		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		// cursorImage = LoadImage("src/image/icons/cursor1.png");
		// Cursor cursor = toolkit.createCustomCursor(cursorImage, new
		// Point(getX(),getY()),"custom");
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		setCursor(cursor);
		// AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0, 0, 748, 470,
		// 50, 50));
		// setBackground(new Color(1.0f,1.0f,1.0f,0.5f));



		File fontFile = new File("src/fonts/odin.ttf");
		try {
			odinRounded = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(odinRounded);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		panelWidth = 764;
		panelHeight = 468;

		longButtonDimension = new Dimension(171, 37);
		squareButtonDimension = new Dimension(49, 55); // evet kare degil biliyom ellemeyin
		playButtonDimension = new Dimension(131, 147);
		arrowButtonDimension = new Dimension(130, 150);
		levelButtonDimension = new Dimension(105, 120);
		playerButtonDimension = new Dimension(300, 120);
		setLayout(new CardLayout());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenuPanel = new MainMenuPanel(0, this);
		gamePanel = new GamePanel(1, this);
		creditsPanel = new CreditsPanel(this);
		settingsPanel = new SettingsPanel(this);
		helpPanel = new HelpPanel(this);
		levelSelectionPanel = new LevelSelectionPanel(this);
		playerPanel = new ChangePlayerPanel(this);
		add(mainMenuPanel);
		add(gamePanel);
		add(creditsPanel);
		add(levelSelectionPanel);
		add(settingsPanel);
		add(helpPanel);
		add(playerPanel);
		add(new JPanel());
		currentPanelIndex = 0;

		setListeners();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pack();
		setLocationRelativeTo(null);

		mainMenuPanel.setVisible(true);
		//gamePanel.setVisible(false);

		setVisible(true);
		pack();

	}

	public JPanel getCurrentPanel() {
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

			targetPanel = mainMenuPanel;
		} else if (panelName.equals("Game")) {

			targetPanel = gamePanel;
		} else if (panelName.equals("Credits")) {

			targetPanel = creditsPanel;

		} else if (panelName.equals("LevelSelection")) {

			targetPanel = levelSelectionPanel;

		} else if (panelName.equals("Settings")) {

			targetPanel = settingsPanel;
		} else if (panelName.equals("Help")) {

			targetPanel = helpPanel;
		} else if (panelName.equals("ChangePlayer")) {
			playerPanel.reset();
			targetPanel = (ChangePlayerPanel)playerPanel;
			
			
		} else {
			System.out.println("Error: Enter valid name");
		}
		targetPanel.setVisible(true);
		setContentPane(targetPanel);
		// transition = true;
	}

	// int i = 0;
	// int a = 0;

	void updatePanels() {// burda bi manas�zl�k var main asl�nda hangi panel active se o olmas�
							// gerekiyo sadece gibi ismi
		gamePanel.updatePanel();

//
//		Dimension size = gamePanel.getPreferredSize();
//		i+=7;
//		if(transition) {
//
//	        if (i % 7 == 0)
//	        	a -=3;
//			targetPanel.setBounds(0, panelWidth+a, size.width, size.height);
//			mainMenuPanel.setBounds(0, 0+a, size.width, size.height);
//			
//			if(panelWidth+a <= 0) {
//				transition = false;
//				mainMenuPanel.setVisible(false);
//			}
//		}

	}

	private void setListeners() {
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		gamePanel.getInnerGamePanel().addMouseListener(mouseListener);
	}

	void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage, String buttonType,
			ActionListener actionListener) {
		button.addActionListener(actionListener);
		if (buttonType.equals("long")) {
			button.setPreferredSize(longButtonDimension);
		} else if (buttonType.equals("square")) {
			button.setPreferredSize(squareButtonDimension);
		} else if (buttonType.equals("play")) {
			button.setPreferredSize(playButtonDimension);
		} else if (buttonType.equals("arrow")) {
			button.setPreferredSize(arrowButtonDimension);
		} else if (buttonType.equals("level")) {
			button.setPreferredSize(levelButtonDimension);
		} else if (buttonType.equals("player")) {
			button.setPreferredSize(playerButtonDimension);
		} else {
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

	public int findCenterHorizontal(int _panelWidth, Component _component) {
		return (_panelWidth - _component.getPreferredSize().width) / 2;
	}

}