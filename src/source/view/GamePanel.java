package source.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister.Pack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import source.controller.*;

public class GamePanel extends JPanel {

	GuiPanelManager guiManager;

	private int index;
	private InnerGamePanel innerGamePanel;
	private BufferedImage background;
	private JButton menu;
	private JButton reset;
	private JLabel timerIcon;
	private JLabel moveLabel;
	private JLabel numberLabel;
	private JProgressBar timer;

	private BufferedImage menuButtonImage;
	private BufferedImage menuButtonHighlightedImage;
	private BufferedImage resetButtonImage;
	private BufferedImage resetButtonHighlightedImage;
	private BufferedImage pauseButtonImage;
	private BufferedImage pauseButtonHighlightedImage;

	private Dimension squareButtonDimension;

	private int panelWidth = 764;
	private int panelHeight = 468;

	public GamePanel(int index, GuiPanelManager _guiManager) {
		super(null);
		guiManager = _guiManager;

		setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.index = index;

		loadImages();

		createComponents();

		add(menu);
		add(reset);
		add(moveLabel);
		add(numberLabel);
		add(timerIcon);
		add(timer);

		createInnerGamePanel();

		setBoundsOfComponents();

		setVisible(true);
		setOpaque(false);
		
		squareButtonDimension = new Dimension(49, 55); // evet kare degil biliyom ellemeyin
	}

	public void updatePanel() {
		repaint();
		if (!isShowing()) {
			return;
		}
	
		innerGamePanel.updatePanel();

		updateNumberOfMoves();

		repaint();
	}

	private void loadImages() {

      background = guiManager.LoadImage("src/image/background.png");


      menuButtonImage = LoadImage("src/image/icons/menu.png");
		menuButtonHighlightedImage = LoadImage("src/image/icons/menuH.png");

		resetButtonImage = LoadImage("src/image/icons/reset.png");
		resetButtonHighlightedImage = LoadImage("src/image/icons/resetH.png");

		pauseButtonImage = LoadImage("src/image/icons/pause.png");
		pauseButtonHighlightedImage = LoadImage("src/image/icons/pauseH.png");
	}

	private void createComponents() {
		menu = new JButton();
		reset = new JButton();

		setupButton(menu, menuButtonImage, menuButtonHighlightedImage);
		setupButton(reset, resetButtonImage, resetButtonHighlightedImage);

		timerIcon = new JLabel(new ImageIcon("src/image/timer.png"));
		timerIcon.setPreferredSize(new Dimension(32, 32));

		moveLabel = new JLabel("Moves", SwingConstants.CENTER);
		moveLabel.setFont(new Font("Odin Rounded", Font.BOLD, 35));
		moveLabel.setPreferredSize(new Dimension(107, 25));

		numberLabel = new JLabel("0", SwingConstants.CENTER);
		numberLabel.setPreferredSize(new Dimension(107, 68));
		numberLabel.setFont(new Font("Odin Rounded", Font.BOLD, 60));

		timer = new JProgressBar(SwingConstants.VERTICAL);
		timer.setPreferredSize(new Dimension(30, 300));

	}

	private void setBoundsOfComponents() {
		Insets insets = getInsets();

		menu.setBounds(30 + insets.left, 30 + insets.top, menu.getPreferredSize().width,
				menu.getPreferredSize().height);

		reset.setBounds(panelWidth - 30 - reset.getPreferredSize().width + insets.left, 30 + insets.top,
				reset.getPreferredSize().width, reset.getPreferredSize().height);

		moveLabel.setBounds(635 + insets.left, 164 + insets.top, moveLabel.getPreferredSize().width,
				moveLabel.getPreferredSize().height);

		numberLabel.setBounds(635 + insets.left, 200 + insets.top, numberLabel.getPreferredSize().width,
				numberLabel.getPreferredSize().height);

		timerIcon.setBounds(70 + insets.left, 116 + insets.top, timerIcon.getPreferredSize().width,
				timerIcon.getPreferredSize().height);

		timer.setBounds(71 + insets.left, 160 + insets.top, timer.getPreferredSize().width,
				timer.getPreferredSize().height);

		innerGamePanel.setBounds(156 + insets.left, 9 + insets.top, innerGamePanel.getPreferredSize().width,
				innerGamePanel.getPreferredSize().height);

	}

	private void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage) {
		button.addActionListener(actionListener);
		button.setPreferredSize(squareButtonDimension);

		button.setIcon(new ImageIcon(normalImage));
		button.setRolloverIcon(new ImageIcon(highlightedImage));
		button.setPressedIcon(new ImageIcon(highlightedImage));

		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SoundManager.instance.buttonClick();
			if (e.getSource() == reset) {
				GameEngine.instance.gameManager.resetLevel();
			}

			if (e.getSource() == menu) {
				guiManager.setPanelVisible("MainMenu");
			}
		}
	};

	public void setEndOfLevelPanelVisible() {
		innerGamePanel.setEndOfLevelPanelVisible(true);
	}

	public void setLevelPanelVisible() {
		System.out.println("Should have shown end of level panel");
		innerGamePanel.setVisible(true);
		innerGamePanel.setEndOfLevelPanelVisible(false);
	}

	private void createInnerGamePanel() {
		try {
			innerGamePanel = new InnerGamePanel(guiManager);
			add(innerGamePanel);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setVisible(false);
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

	public InnerGamePanel getInnerGamePanel() {
		return innerGamePanel;
	}

	public void updateNumberOfMoves() {
		numberLabel.setText(GameEngine.instance.vehicleController.getNumberOfMoves() + "");
	}

	private BufferedImage LoadImage(String FileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(FileName));
		} catch (IOException e) {
		}
		return image;
	}
}