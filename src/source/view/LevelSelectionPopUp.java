package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LevelSelectionPopUp extends JPanel {
	private GuiPanelManager guiManager;

	private JButton retry;
	private JButton menu;
	private JButton nextLevel;
	private JLabel heading;
	private JLabel star1;
	private JLabel star2;
	private JLabel star3;

	private BufferedImage background;

	private BufferedImage menuButtonImage;
	private BufferedImage menuButtonHighlightedImage;
	private BufferedImage retryButtonImage;
	private BufferedImage retryButtonHighlightedImage;
	private BufferedImage nextLevelButtonImage;
	private BufferedImage nextLevelButtonHighlightedImage;
	private BufferedImage starImage;
	private BufferedImage starLockedImage;

	private int panelWidth = 400;
	private int panelHeight = 250;

	public LevelSelectionPopUp(GuiPanelManager _guiManager) {
		super(null);
		guiManager = _guiManager;
		setPreferredSize(new Dimension(panelWidth, panelHeight));

		loadImages();
		createComponents();
		addComponents();
		setBoundsOfComponents();
		setOpaque(false);
	}

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/icons/endOfLevelBackground.png");

      menuButtonImage = guiManager.LoadImage("src/image/icons/menu.png");
      menuButtonHighlightedImage = guiManager.LoadImage("src/image/icons/menuH.png");

		retryButtonImage = guiManager.LoadImage("src/image/icons/reset.png");
		retryButtonHighlightedImage = guiManager.LoadImage("src/image/icons/resetH.png");

      nextLevelButtonImage = guiManager.LoadImage("src/image/icons/next.png");
      nextLevelButtonHighlightedImage = guiManager.LoadImage("src/image/icons/nextH.png");

		starImage = guiManager.LoadImage("src/image/icons/star.png");
		starLockedImage = guiManager.LoadImage("src/image/icons/starLocked.png");
	}

	void updatePanel() {
		if (!isShowing()) {
			return;
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

      drawBackground(g);
	}

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

	private void createComponents() {
		heading = new JLabel("Level Completed!", SwingConstants.CENTER);
		heading.setPreferredSize(new Dimension(300, 60));
		heading.setFont(new Font("Odin Rounded", Font.PLAIN, 35));
		heading.setForeground(Color.white);

		star1 = new JLabel();
		star2 = new JLabel();
		star3 = new JLabel();

		star1.setIcon(new ImageIcon(starImage));
		star2.setIcon(new ImageIcon(starImage));
		star3.setIcon(new ImageIcon(starLockedImage));

		menu = new JButton();
		retry = new JButton();
		nextLevel = new JButton();

		guiManager.setupButton(menu, menuButtonImage, menuButtonHighlightedImage, "square", actionListener);
		guiManager.setupButton(retry, retryButtonImage, retryButtonHighlightedImage, "square", actionListener);
		guiManager.setupButton(nextLevel, nextLevelButtonImage, nextLevelButtonHighlightedImage, "square",
				actionListener);

	}

	private void addComponents() {
		add(retry);
		add(menu);
		add(nextLevel);
		add(heading);
		add(star1);
		add(star2);
		add(star3);
	}

	private void setBoundsOfComponents() {
		Insets insets = getInsets();

		Dimension size;
		size = retry.getPreferredSize();

		heading.setBounds(50 + insets.left, insets.top, heading.getPreferredSize().width,
				heading.getPreferredSize().height);

		star1.setBounds(guiManager.findCenterHorizontal(panelWidth, star1) - 85 + insets.left, 60 + insets.top,
				star1.getPreferredSize().width, star1.getPreferredSize().height);
		star2.setBounds(guiManager.findCenterHorizontal(panelWidth, star2) + insets.left, 60 + insets.top,
				star1.getPreferredSize().width, star1.getPreferredSize().height);
		star3.setBounds(guiManager.findCenterHorizontal(panelWidth, star3) + 85 + insets.left, 60 + insets.top,
				star1.getPreferredSize().width, star1.getPreferredSize().height);

		menu.setBounds(105 + insets.left, 150 + insets.top, size.width, size.height);
		retry.setBounds(175 + insets.left, 150 + insets.top, size.width, size.height);
		nextLevel.setBounds(245 + insets.left, 150 + insets.top, size.width, size.height);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SoundManager.instance.buttonClick();
			if (e.getSource() == retry) {
				GameEngine.instance.gameManager.resetLevel();
			}

			if (e.getSource() == menu) {
				guiManager.setPanelVisible("MainMenu");
			}

			if (e.getSource() == nextLevel) {
				GameEngine.instance.gameManager.nextLevel();
			}
		}
	};
}
