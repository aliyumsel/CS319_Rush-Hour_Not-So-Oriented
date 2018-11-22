package source.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager.Limit;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

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

	private static final long serialVersionUID = 1L;
	private JButton[] buttonArray;
	private JButton rightArrowbutton;
	private JButton leftArrowbutton;
	private GuiPanelManager guiManager;
	private BufferedImage background;
	private BufferedImage levelBackground;
	private BufferedImage rightArrow;
	private BufferedImage leftArrow;
	private BufferedImage title;
	private JButton button = new JButton("-");
	private int panelWidth = 764;
	private int panelHeight = 468;
	private int page = 0;

	public LevelSelectionPanel(GuiPanelManager guiPanelManager) {

		super(null);
		buttonArray = new JButton[40];
		rightArrowbutton = new JButton();
		leftArrowbutton = new JButton();
		guiManager = guiPanelManager;
		setPreferredSize(new Dimension(panelWidth, panelHeight));

		loadImages();

		createComponents();
		add(leftArrowbutton);
		add(rightArrowbutton);
		setBoundsOfComponents(page);
		this.setVisible(true);

	}

	private void loadImages() {
		background = guiManager.LoadImage("src/image/orange.png");
		levelBackground = guiManager.LoadImage("src/image/icons/levelbackground.png");
		rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
		leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
		title = guiManager.LoadImage("src/image/icons/title.png");

	}

	private ActionListener actionListener = e -> {
		SoundManager.instance.buttonClick();
		if (e.getSource() == leftArrowbutton) {
			if (page == 0)
				page = 2;
			else
				page -= 1;

			setBoundsOfComponents(page);
		}
		if (e.getSource() == rightArrowbutton) {
			if (page == 2)
				page = 0;
			else
				page += 1;
			setBoundsOfComponents(page);
		}

	};

	private void createComponents() {
		guiManager.setupButton(rightArrowbutton, rightArrow, rightArrow, "arrow", actionListener);
		guiManager.setupButton(leftArrowbutton, leftArrow, leftArrow, "arrow", actionListener);
		for (int i = 0; i < buttonArray.length; i++) {
			buttonArray[i] = new JButton("" + (1 + i));
			guiManager.setupButton(buttonArray[i], levelBackground, levelBackground, "arrow", actionListener);
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
		int buttons = 0;
		for(int i = 0; i<40;i++)
			buttonArray[i].setVisible(false);
		for (int i = 0 + limit; i < 12 + limit; i++) {
			buttons++;
			
			if (i == 4 || i == 8 || i == 12 || i == 16 || i == 20 || i == 24 || i == 28 || i == 32 || i == 36
					|| i == 40)
				gap = 0;
			if (i > -1 + limit && i < 4 + limit) {
				gap += 122;
				buttonArray[i].setBounds(gap + insets.left, 20 + insets.top, buttonArray[i].getPreferredSize().width,
						buttonArray[i].getPreferredSize().height);
			} else if (i > 3 + limit && i < 8 + limit) {
				gap += 122;
				buttonArray[i].setBounds(gap + insets.left,
						guiManager.findCenterHorizontal(panelHeight, buttonArray[i]) + insets.top,
						buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
			} else if (i > 7 + limit && i < 12 + limit) {
				gap += 122;
				buttonArray[i].setBounds(gap + insets.left,
						135 + guiManager.findCenterHorizontal(panelHeight, buttonArray[i]) + insets.top,
						buttonArray[i].getPreferredSize().width, buttonArray[i].getPreferredSize().height);
			}
			buttonArray[i].setVisible(true);
			
		}
		
		leftArrowbutton.setBounds(5, guiManager.findCenterHorizontal(panelHeight, leftArrowbutton) + insets.top,
				leftArrowbutton.getPreferredSize().width, leftArrowbutton.getPreferredSize().height);
		rightArrowbutton.setBounds(600, guiManager.findCenterHorizontal(panelHeight, rightArrowbutton) + insets.top,
				rightArrowbutton.getPreferredSize().width, rightArrowbutton.getPreferredSize().height);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBackground(g); // change the background png for changing the background
		// setBackground(Color.WHITE);
	}

	private void drawBackground(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D) graphics;

		graphics2d.drawImage(background, 0, 0, null);

	}

}
