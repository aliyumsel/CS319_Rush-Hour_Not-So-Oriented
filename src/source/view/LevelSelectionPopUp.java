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
	private JButton back;
	private JButton play;
	private JLabel heading;
	private JLabel star1;
	private JLabel star2;
	private JLabel star3;
	private int destinationLevel;
	private BufferedImage background;

	private BufferedImage backButtonImage;
	private BufferedImage backButtonHighlightedImage;
	private BufferedImage retryButtonImage;
	private BufferedImage retryButtonHighlightedImage;
	private BufferedImage playButtonImage;
	private BufferedImage playButtonHighlightedImage;
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
		setVisible(false);
	}

	private void loadImages() {
		background = guiManager.LoadImage("src/image/endOfLevelPanelBackground.png");

		backButtonImage = guiManager.LoadImage("src/image/icons/back.png");
		backButtonHighlightedImage = guiManager.LoadImage("src/image/icons/backH.png");

		retryButtonImage = guiManager.LoadImage("src/image/icons/reset.png");
		retryButtonHighlightedImage = guiManager.LoadImage("src/image/icons/resetH.png");

		playButtonImage = guiManager.LoadImage("src/image/icons/miniPlay.png");
		playButtonHighlightedImage = guiManager.LoadImage("src/image/icons/miniPlayH.png");

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

	public void initialize(int level) {
		this.destinationLevel = level;
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

		back = new JButton();
		retry = new JButton();
		play = new JButton();

		guiManager.setupButton(back, backButtonImage, backButtonHighlightedImage, "square", actionListener);
		guiManager.setupButton(retry, retryButtonImage, retryButtonHighlightedImage, "square", actionListener);
		guiManager.setupButton(play, playButtonImage, playButtonHighlightedImage, "square", actionListener);

	}

	private void addComponents() {
		// add(retry);
		add(back);
		add(play);
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

		back.setBounds(105 + insets.left, 150 + insets.top, size.width, size.height);
		// retry.setBounds(175 + insets.left, 150 + insets.top, size.width,
		// size.height);
		play.setBounds(245 + insets.left, 150 + insets.top, size.width, size.height);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SoundManager.instance.buttonClick();
			if (e.getSource() == retry) {
				GameEngine.instance.gameManager.resetLevel();
			}

			if (e.getSource() == back) {
				setVisible(false);
			}

			if (e.getSource() == play) {
				setVisible(false);
				GameEngine.instance.gameManager.loadLevel(destinationLevel);
				guiManager.setPanelVisible("Game");

			}
		}
	};
}
