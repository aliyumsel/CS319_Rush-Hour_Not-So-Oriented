package source.view;

import javax.swing.*;

import java.awt.*;

import source.controller.SoundManager;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LevelSelectionPanel extends JPanel {

   private GuiPanelManager guiManager;

	private JButton[] buttonArray;
	private JButton rightArrowButton;
	private JButton leftArrowButton;
	private JButton menuButton;

	private BufferedImage background;
	private BufferedImage levelBackground;
   private BufferedImage levelBackgroundHighlighted;
	private BufferedImage rightArrow;
   private BufferedImage rightArrowHighlighted;
	private BufferedImage leftArrow;
   private BufferedImage leftArrowHighlighted;
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
		rightArrowButton = new JButton();
		leftArrowButton = new JButton();
		menuButton = new JButton();
		popUp = new LevelSelectionPopUp(guiPanelManager);
		guiManager = guiPanelManager;
		setPreferredSize(new Dimension(panelWidth, panelHeight));

		loadImages();
		add(popUp);
		createComponents();
		add(leftArrowButton);
		add(rightArrowButton);
		add(menuButton);

		setBoundsOfComponents(page);
		this.setVisible(false);

	}

	private void loadImages()
   {
		background = guiManager.LoadImage("src/image/background.png");
		levelBackground = guiManager.LoadImage("src/image/icons/levelbackground.png");
      levelBackgroundHighlighted = guiManager.LoadImage("src/image/icons/levelbackgroundH.png");
      rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
      rightArrowHighlighted = guiManager.LoadImage("src/image/icons/rightarrowH.png");
      leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
      leftArrowHighlighted = guiManager.LoadImage("src/image/icons/leftarrowH.png");
      back = guiManager.LoadImage("src/image/icons/back.png");
		backHighlighted = guiManager.LoadImage("src/image/icons/backH.png");
		title = guiManager.LoadImage("src/image/icons/title.png");

	}

	private void createComponents() {
		guiManager.setupButton(rightArrowButton, rightArrow, rightArrowHighlighted, "arrow", actionListener);
		guiManager.setupButton(leftArrowButton, leftArrow, leftArrowHighlighted, "arrow", actionListener);
		guiManager.setupButton(menuButton, back, backHighlighted, "square", actionListener);
		for (int i = 0; i < buttonArray.length; i++) {
			buttonArray[i] = new JButton("" + (1 + i));
			guiManager.setupButton(buttonArray[i], levelBackground, levelBackgroundHighlighted, "level", actionListener);
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

		leftArrowButton.setBounds(5, guiManager.findCenterHorizontal(panelHeight, leftArrowButton) + insets.top,
				leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
		rightArrowButton.setBounds(panelWidth - 135,
				guiManager.findCenterHorizontal(panelHeight, rightArrowButton) + insets.top,
				rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);
		menuButton.setBounds(30 + insets.left, 30 + insets.top, menuButton.getPreferredSize().width,
				menuButton.getPreferredSize().height);

		Dimension size = popUp.getPreferredSize();
		popUp.setBounds(guiManager.findCenterHorizontal(panelWidth, popUp), 100 + insets.top, size.width, size.height);
		//popUp.setVisible(true); in order to test pop up panel design remove the comment
	}

	private ActionListener actionListener = e -> {
		SoundManager.instance.buttonClick();
		if (e.getSource() == leftArrowButton ) {
			if (page == 0)
				page = 3;
			else
				page -= 1;

			setBoundsOfComponents(page);
		} else if (e.getSource() == rightArrowButton ) {
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
