package source.view;

import javax.imageio.ImageIO;
import javax.swing.*;
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

	private JButton back;
	private JButton reset;
	private JLabel timerIcon;
	private JLabel moveLabel;
	private JLabel numberLabel;
	private JProgressBar timer;

	private BufferedImage backButtonImage;
	private BufferedImage backButtonHighlightedImage;
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

		add(back);
		add(reset);
		add(moveLabel);
		add(numberLabel);
		add(timerIcon);
		add(timer);

		createInnerGamePanel();

		setBoundsOfComponents();

		setVisible(true);
		setOpaque(false);

      squareButtonDimension = new Dimension(49,55); // evet kare degil biliyom ellemeyin
	}

	public void updatePanel() {
		if (!isShowing()) {
			return;
		}

		innerGamePanel.updatePanel();

		updateNumberOfMoves();

		repaint();
	}

   private void loadImages()
   {
      backButtonImage = LoadImage("src/image/icons/back.png");
      backButtonHighlightedImage = LoadImage("src/image/icons/backH.png");

      resetButtonImage = LoadImage("src/image/icons/reset.png");
      resetButtonHighlightedImage = LoadImage("src/image/icons/resetH.png");

      pauseButtonImage = LoadImage("src/image/icons/pause.png");
      pauseButtonHighlightedImage = LoadImage("src/image/icons/pauseH.png");
   }

	private void createComponents()
   {
      back = new JButton();
      reset = new JButton();

      setupButton(back,backButtonImage,backButtonHighlightedImage);
      setupButton(reset,resetButtonImage,resetButtonHighlightedImage);

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

		back.setBounds(30 + insets.left, 30 + insets.top, back.getPreferredSize().width, back.getPreferredSize().height);

		reset.setBounds(panelWidth - 30 - reset.getPreferredSize().width + insets.left, 30 + insets.top, reset.getPreferredSize().width, reset.getPreferredSize().height);

		moveLabel.setBounds(635 + insets.left, 164 + insets.top, moveLabel.getPreferredSize().width, moveLabel.getPreferredSize().height);

		numberLabel.setBounds(635 + insets.left, 200 + insets.top, numberLabel.getPreferredSize().width,  numberLabel.getPreferredSize().height);

		timerIcon.setBounds(70 + insets.left, 116 + insets.top, timerIcon.getPreferredSize().width, timerIcon.getPreferredSize().height);

		timer.setBounds(71 + insets.left, 160 + insets.top, timer.getPreferredSize().width, timer.getPreferredSize().height);

		innerGamePanel.setBounds(156 + insets.left, 9 + insets.top, innerGamePanel.getPreferredSize().width, innerGamePanel.getPreferredSize().height);

	}

   private void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage)
   {
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
			if (e.getSource() == reset) {
				GameEngine.instance.gameManager.resetLevel();
			}

			if (e.getSource() == back) {
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