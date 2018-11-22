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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ChangePlayerPanel extends JPanel {

	// private JButton[] buttonArray;
	private ArrayList<JButton> buttonArray;
	private JButton rightArrowbutton;
	private JButton leftArrowbutton;
	private JButton menuButton;
	private JButton addButton;
	private JButton deleteButton1;
	private JButton deleteButton2;
	private JButton deleteButton3;
	private JButton editButton1;
	private JButton editButton2;
	private JButton editButton3;
	private GuiPanelManager guiManager;
	private BufferedImage background;
	private BufferedImage levelBackground;
	private BufferedImage levelBackgroundH;
	private BufferedImage rightArrow;
	private BufferedImage leftArrow;
	private BufferedImage leftArrowH;
	private BufferedImage rightArrowH;
	private BufferedImage add;
	private BufferedImage addH;
	private BufferedImage edit;
	private BufferedImage editH;
	private BufferedImage delete;
	private BufferedImage deleteH;
	private BufferedImage title;
	private BufferedImage back;
	private BufferedImage backHighlighted;
	private int panelWidth = 764;
	private int panelHeight = 468;
	private int page = 0;
	private int numberOfLevels = 40;
	private LevelSelectionPopUp popUp;
	private int numberOfPlayers = 5;

	public ChangePlayerPanel(GuiPanelManager guiPanelManager) {

		super(null);
		buttonArray = new ArrayList<JButton>();
		rightArrowbutton = new JButton();
		leftArrowbutton = new JButton();
		addButton = new JButton();
		deleteButton1 = new JButton();
		deleteButton2 = new JButton();
		deleteButton3 = new JButton();
		editButton1 = new JButton();
		editButton2 = new JButton();
		editButton3 = new JButton();
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
		add(addButton);
		add(deleteButton1);
		add(deleteButton2);
		add(deleteButton3);
		add(editButton1);
		add(editButton2);
		add(editButton3);
		setBoundsOfComponents(0);
		this.setVisible(false);

	}

	private void loadImages() {
		background = guiManager.LoadImage("src/image/orange.png");
		levelBackground = guiManager.LoadImage("src/image/icons/playerSelection.png");
		levelBackgroundH = guiManager.LoadImage("src/image/icons/playerSelectionH.png");
		rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
		leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
		rightArrowH = guiManager.LoadImage("src/image/icons/rightarrowH.png");
		leftArrowH = guiManager.LoadImage("src/image/icons/leftarrowH.png");
		back = guiManager.LoadImage("src/image/icons/back.png");
		backHighlighted = guiManager.LoadImage("src/image/icons/backH.png");
		title = guiManager.LoadImage("src/image/icons/title.png");
		add = guiManager.LoadImage("src/image/icons/addPlayer.png");
		addH = guiManager.LoadImage("src/image/icons/addPlayerH.png");
		delete = guiManager.LoadImage("src/image/icons/quit.png");
		deleteH = guiManager.LoadImage("src/image/icons/quitH.png");
		edit = guiManager.LoadImage("src/image/icons/help.png");
		editH = guiManager.LoadImage("src/image/icons/helpH.png");

	}

	private void createComponents() {
		guiManager.setupButton(rightArrowbutton, rightArrow, rightArrowH, "arrow", actionListener);
		guiManager.setupButton(leftArrowbutton, leftArrow, leftArrowH, "arrow", actionListener);
		guiManager.setupButton(menuButton, back, backHighlighted, "square", actionListener);
		guiManager.setupButton(addButton, add, addH, "square", actionListener);
		guiManager.setupButton(deleteButton1, delete, deleteH, "square", actionListener);
		guiManager.setupButton(deleteButton2, delete, deleteH, "square", actionListener);
		guiManager.setupButton(deleteButton3, delete, deleteH, "square", actionListener);
		guiManager.setupButton(editButton1, edit, editH, "square", actionListener);
		guiManager.setupButton(editButton2, edit, editH, "square", actionListener);
		guiManager.setupButton(editButton3,edit, editH, "square", actionListener);
		for (int i = 0; i < numberOfPlayers; i++) {//bu gidecek her player eklendiðinde addmethodu caðýrýlacak
			buttonArray.add(new JButton(""+ "ddalkilic10"));
			guiManager.setupButton(buttonArray.get(i), levelBackground, levelBackgroundH, "player", actionListener);
			buttonArray.get(i).setVerticalTextPosition(SwingConstants.CENTER);
			buttonArray.get(i).setHorizontalTextPosition(SwingConstants.CENTER);
			buttonArray.get(i).setFont(new Font("Odin Rounded", Font.PLAIN, 25));
			buttonArray.get(i).revalidate();
			add(buttonArray.get(i));
		}

	}

	private void setBoundsOfComponents(int page) {
		Insets insets = getInsets();
		int gap = 0;
		int pageLength = 3;
		int limit = page * pageLength;

		for (int i = 0; i < numberOfPlayers; i++) {
			buttonArray.get(i).setVisible(false);
		}
		deleteButton1.setVisible(false);
		deleteButton2.setVisible(false);
		deleteButton3.setVisible(false);
		editButton1.setVisible(false);
		editButton2.setVisible(false);
		editButton3.setVisible(false);
		
		for (int i = 0; i < numberOfPlayers; i++) {

			if (i % 3 == 0)
				gap = 0;
			if (i == 0 + limit) {
				gap = 50;
				buttonArray.get(i).setBounds(235 + insets.left, gap, buttonArray.get(i).getPreferredSize().width,
						buttonArray.get(i).getPreferredSize().height);
				buttonArray.get(i).setVisible(true);
				deleteButton1.setVisible(true);
				deleteButton1.setBounds(565 + insets.left, gap +30 + insets.top, deleteButton1.getPreferredSize().width,
						deleteButton1.getPreferredSize().height);
				editButton1.setVisible(true);
				editButton1.setBounds(160 + insets.left, gap +30 + insets.top, editButton1.getPreferredSize().width,
						editButton1.getPreferredSize().height);
			} else if (i == 1 + limit) {
				gap = 180;
				buttonArray.get(i).setBounds(235 + insets.left, gap, buttonArray.get(i).getPreferredSize().width,
						buttonArray.get(i).getPreferredSize().height);
				buttonArray.get(i).setVisible(true);
				deleteButton2.setVisible(true);
				deleteButton2.setBounds(565 + insets.left, gap + 30 + insets.top,deleteButton2.getPreferredSize().width,
						deleteButton2.getPreferredSize().height);
				editButton2.setVisible(true);
				editButton2.setBounds(160 + insets.left, gap +30 + insets.top, editButton2.getPreferredSize().width,
						editButton2.getPreferredSize().height);
			} else if (i == 2 + limit) {
				gap = 310;
				buttonArray.get(i).setBounds(235 + insets.left, gap, buttonArray.get(i).getPreferredSize().width,
						buttonArray.get(i).getPreferredSize().height);
				buttonArray.get(i).setVisible(true);
				deleteButton3.setVisible(true);
				deleteButton3.setBounds(565 + insets.left, gap + 30 + insets.top, deleteButton3.getPreferredSize().width,
						deleteButton3.getPreferredSize().height);
				editButton3.setVisible(true);
				editButton3.setBounds(160 + insets.left, gap +30 + insets.top, editButton3.getPreferredSize().width,
						editButton3.getPreferredSize().height);
			}

		}

		leftArrowbutton.setBounds(5, guiManager.findCenterHorizontal(panelHeight, leftArrowbutton) + insets.top,
				leftArrowbutton.getPreferredSize().width, leftArrowbutton.getPreferredSize().height);
		rightArrowbutton.setBounds(panelWidth - 135,
				guiManager.findCenterHorizontal(panelHeight, rightArrowbutton) + insets.top,
				rightArrowbutton.getPreferredSize().width, rightArrowbutton.getPreferredSize().height);
		menuButton.setBounds(30 + insets.left, 30 + insets.top, menuButton.getPreferredSize().width,
				menuButton.getPreferredSize().height);
		addButton.setBounds(panelWidth - 30 - addButton.getPreferredSize().width, 30 + insets.top, addButton.getPreferredSize().width,
				menuButton.getPreferredSize().height);
		
		Dimension size = popUp.getPreferredSize();
		popUp.setBounds(guiManager.findCenterHorizontal(panelWidth, popUp), 100 + insets.top, size.width, size.height);
		// popUp.setVisible(true); in order to test pop up panel design remove the
		// comment
	}

	private ActionListener actionListener = e -> {
		SoundManager.instance.buttonClick();
		if (e.getSource() == leftArrowbutton) {
			if (page == 0)
				page = numberOfPlayers / 3;
			else
				page -= 1;

			setBoundsOfComponents(page);
		} else if (e.getSource() == rightArrowbutton) {
			if (page == numberOfPlayers / 3)
				page = 0;
			else
				page += 1;

			setBoundsOfComponents(page);
		} else if (e.getSource() == menuButton) {
			// this.setVisible(false);
			guiManager.setPanelVisible("MainMenu");
		} else {
			for (int index = 0; index < numberOfLevels; index++) {
				if (e.getSource() == buttonArray.get(index))
					popUp.initialize(index + 1); // buna player objesi de eklenecek

				popUp.setVisible(true);

			}
		}

	};

	public void reset() {
		this.page = 0;
		setBoundsOfComponents(page);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBackground(g); // change the background png for changing the background

	}

	private void drawBackground(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		graphics2d.drawImage(background, 0, 0, null);

	}

}
