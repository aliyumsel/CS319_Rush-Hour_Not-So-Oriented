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

public class LevelSelectionPanel extends JPanel {

	private JButton[] buttonArray;
	private JButton rightArrowbutton;
	private JButton leftArrowbutton;
	private JButton menuButton;
	private GuiPanelManager guiManager;
	private BufferedImage background;
	private BufferedImage levelBackground;
	private BufferedImage rightArrow;
	private BufferedImage leftArrow;
	private BufferedImage title;
	private BufferedImage back;
	private BufferedImage backHighlighted;
	private JButton button = new JButton("-");
	private int panelWidth = 764;
	private int panelHeight = 468;
	private int page = 0;
	private int numberOfLevels = 40;
	private LevelSelectionPopUp popUp;

	public LevelSelectionPanel(GuiPanelManager guiPanelManager) {

		super(null);
		buttonArray = new JButton[40];
		rightArrowbutton = new JButton();
		leftArrowbutton = new JButton();
		menuButton = new JButton();
		popUp = new LevelSelectionPopUp(guiPanelManager);
		guiManager = guiPanelManager;
		setPreferredSize(new Dimension(panelWidth, panelHeight));

		loadImages();
		add(popUp);
		createComponents();
		add(leftArrowbutton);
		add(rightArrowbutton);
		add(menuButton);

		setBoundsOfComponents(page);
		this.setVisible(false);

	}

	private void loadImages() {
		background = guiManager.LoadImage("src/image/orange.png");
		levelBackground = guiManager.LoadImage("src/image/icons/levelbackground.png");
		rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
		leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
		back = guiManager.LoadImage("src/image/icons/back.png");
		backHighlighted = guiManager.LoadImage("src/image/icons/backH.png");
		title = guiManager.LoadImage("src/image/icons/title.png");

	}

	private void createComponents() {
		guiManager.setupButton(rightArrowbutton, rightArrow, rightArrow, "arrow", actionListener);
		guiManager.setupButton(leftArrowbutton, leftArrow, leftArrow, "arrow", actionListener);
		guiManager.setupButton(menuButton, back, backHighlighted, "square", actionListener);
		for (int i = 0; i < buttonArray.length; i++) {
			buttonArray[i] = new JButton("" + (1 + i));
			guiManager.setupButton(buttonArray[i], levelBackground, levelBackground, "level", actionListener);
			buttonArray[i].setVerticalTextPosition(SwingConstants.CENTER);
			buttonArray[i].setHorizontalTextPosition(SwingConstants.CENTER);
			buttonArray[i].setFont(new Font("Odin Rounded", Font.PLAIN, 25));
			buttonArray[i].revalidate();
			add(buttonArray[i]);
		}

	}

	private void setBoundsOfComponents(int page) {
		Insets insets = getInsets();
		int gap = 0;
		int pageLength = 12;
		int limit = page * pageLength;

		for (int i = 0; i < numberOfLevels; i++)
			buttonArray[i].setVisible(false);
		for (int i = 0 + limit; i < 12 + limit && i < numberOfLevels; i++) {

			if (i % 4 == 0)
				gap = 0;
			if (i > -1 + limit && i < 4 + limit) {
				gap += 133;
				buttonArray[i].setBounds(gap + insets.left,
						guiManager.findCenterHorizontal(panelHeight, buttonArray[i]) + insets.top - 135,
						buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
			} else if (i > 3 + limit && i < 8 + limit) {
				gap += 133;
				buttonArray[i].setBounds(gap + insets.left,
						guiManager.findCenterHorizontal(panelHeight, buttonArray[i]) + insets.top,
						buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
			} else if (i > 7 + limit && i < 12 + limit) {
				gap += 133;
				buttonArray[i].setBounds(gap + insets.left,
						135 + guiManager.findCenterHorizontal(panelHeight, buttonArray[i]) + insets.top,
						buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
			}
			buttonArray[i].setVisible(true);

		}

		leftArrowbutton.setBounds(5, guiManager.findCenterHorizontal(panelHeight, leftArrowbutton) + insets.top,
				leftArrowbutton.getPreferredSize().width, leftArrowbutton.getPreferredSize().height);
		rightArrowbutton.setBounds(panelWidth - 135,
				guiManager.findCenterHorizontal(panelHeight, rightArrowbutton) + insets.top,
				rightArrowbutton.getPreferredSize().width, rightArrowbutton.getPreferredSize().height);
		menuButton.setBounds(30 + insets.left, 30 + insets.top, menuButton.getPreferredSize().width,
				menuButton.getPreferredSize().height);

		Dimension size = popUp.getPreferredSize();
		popUp.setBounds(guiManager.findCenterHorizontal(panelWidth, popUp), 100 + insets.top, size.width, size.height);
		//popUp.setVisible(true); in order to test pop up panel design remove the comment
	}

	private ActionListener actionListener = e -> {
		SoundManager.instance.buttonClick();
		if (e.getSource() == leftArrowbutton) {
			if (page == 0)
				page = 3;
			else
				page -= 1;

			setBoundsOfComponents(page);
		} else if (e.getSource() == rightArrowbutton) {
			if (page == 3)
				page = 0;
			else
				page += 1;
			setBoundsOfComponents(page);
		} else if (e.getSource() == menuButton) {
			// this.setVisible(false);
			guiManager.setPanelVisible("MainMenu");
		} else {
			for (int index = 0; index < numberOfLevels; index++) {
				if (e.getSource() == buttonArray[index])
					popUp.initialize(index + 1); // buna player objesi de eklenecek
				// GameEngine.instance.gameManager.loadLevel(index+1);;

				popUp.setVisible(true);

				// guiManager.setPanelVisible("Game");
			}
		}

	};

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBackground(g); // change the background png for changing the background

	}

	private void drawBackground(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		graphics2d.drawImage(background, 0, 0, null);

	}

}
