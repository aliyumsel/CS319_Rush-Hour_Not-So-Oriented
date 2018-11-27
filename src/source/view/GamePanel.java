package source.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import source.controller.*;

public class GamePanel extends JPanel {


	GuiPanelManager guiManager;

	private InnerGamePanel innerGamePanel;

	private JButton menu;
	private JButton reset;
	private JButton settings;
	private JButton hint;

	private JLabel timerIcon;
	private JLabel moveLabel;
	private JLabel numberLabel;

	private JProgressBar timer;

   private BufferedImage background;
	private BufferedImage menuButtonImage;
	private BufferedImage menuButtonHighlightedImage;
	private BufferedImage resetButtonImage;
	private BufferedImage resetButtonHighlightedImage;
	private BufferedImage settingsButtonImage;
	private BufferedImage settingsButtonHighlightedImage;
   private BufferedImage hintButtonImage;
   private BufferedImage hintButtonHighlightedImage;
   private BufferedImage movesImage;

	private int panelWidth;
	private int panelHeight;

	public GamePanel(GuiPanelManager _guiManager) {
		super(null);
		guiManager = _guiManager;

		panelWidth = guiManager.panelWidth;
		panelHeight = guiManager.panelHeight;

		setPreferredSize(new Dimension(panelWidth, panelHeight));

		loadImages();
		createComponents();
      addComponents();

		createInnerGamePanel();

		setBoundsOfComponents();

		setOpaque(false);
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

      background = guiManager.LoadImage("src/image/gameBackground.png");
		Image scaledImage = background.getScaledInstance(panelWidth,panelHeight,Image.SCALE_DEFAULT);
		background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = background.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

      menuButtonImage = guiManager.LoadImage("src/image/icons/menu.png");
		menuButtonHighlightedImage = guiManager.LoadImage("src/image/icons/menuH.png");

		settingsButtonImage = guiManager.LoadImage("src/image/icons/settingsIcon.png");
      settingsButtonHighlightedImage = guiManager.LoadImage("src/image/icons/settingsIconH.png");

      resetButtonImage = guiManager.LoadImage("src/image/icons/reset.png");
		resetButtonHighlightedImage = guiManager.LoadImage("src/image/icons/resetH.png");

		hintButtonImage = guiManager.LoadImage("src/image/icons/hint.png");
		hintButtonHighlightedImage = guiManager.LoadImage("src/image/icons/hintH.png");

		movesImage = guiManager.LoadImage("src/image/icons/movesCar.png");
	}

	private void createComponents()
   {
		menu = UIFactory.createButton(menuButtonImage, menuButtonHighlightedImage,"square",actionListener);
		reset = UIFactory.createButton(resetButtonImage, resetButtonHighlightedImage,"square",actionListener);
		settings = UIFactory.createButton(settingsButtonImage, settingsButtonHighlightedImage, "square",actionListener);
		hint = UIFactory.createButton(hintButtonImage,hintButtonHighlightedImage,"square",actionListener);

		//timerIcon = new JLabel(new ImageIcon("src/image/timer.png"));
		//timerIcon.setPreferredSize(new Dimension(32, 32));

//		moveLabel = new JLabel("Moves", SwingConstants.CENTER);
//		moveLabel.setFont(new Font("Odin Rounded", Font.BOLD, 35));
//		moveLabel.setPreferredSize(new Dimension(107, 25));

      moveLabel = UIFactory.createLabelIcon(movesImage,"movesCar");

		numberLabel = new JLabel("0", SwingConstants.CENTER);
		numberLabel.setPreferredSize(new Dimension(107, 68));
		numberLabel.setFont(new Font("Odin Rounded", Font.BOLD, 60));
		numberLabel.setForeground(Color.white);

		//timer = new JProgressBar(SwingConstants.VERTICAL);
		//timer.setPreferredSize(new Dimension(30, 300));
	}

	private void addComponents()
   {
      this.add(menu);
      add(reset);
      add(hint);
      add(moveLabel);
      add(numberLabel);
      //add(timerIcon);
      //add(timer);
      add(settings);
   }

	private void setBoundsOfComponents()
   {
		menu.setBounds(30 , 30 , menu.getPreferredSize().width,
				menu.getPreferredSize().height);

      settings.setBounds(panelWidth - 30 - settings.getPreferredSize().width, 30,
              settings.getPreferredSize().width, settings.getPreferredSize().height);

		reset.setBounds(panelWidth - 30 - reset.getPreferredSize().width, panelHeight - 30 - reset.getPreferredSize().height,
				reset.getPreferredSize().width, reset.getPreferredSize().height);

      hint.setBounds(30, panelHeight - 30 - hint.getPreferredSize().height,
              hint.getPreferredSize().width, hint.getPreferredSize().height);

		moveLabel.setBounds(panelWidth - moveLabel.getPreferredSize().width - 30, 200, moveLabel.getPreferredSize().width,
				moveLabel.getPreferredSize().height);

		numberLabel.setBounds(panelWidth - numberLabel.getPreferredSize().width - 15, 265, numberLabel.getPreferredSize().width,
				numberLabel.getPreferredSize().height);

//		timerIcon.setBounds(70, 116, timerIcon.getPreferredSize().width,
//				timerIcon.getPreferredSize().height);
//
//		timer.setBounds(71, 160, timer.getPreferredSize().width,
//				timer.getPreferredSize().height);

		innerGamePanel.setBounds(guiManager.findCenter(panelWidth,innerGamePanel), guiManager.findCenter(panelHeight,innerGamePanel), innerGamePanel.getPreferredSize().width,
				innerGamePanel.getPreferredSize().height);

	}

	public void setEndOfLevelPanelVisible(int starAmount) {
		innerGamePanel.setEndOfLevelPanelVisible(true, starAmount);
	}

	public void setInnerGamePanelVisible() {
		System.out.println("Should have shown inner game panel");
		innerGamePanel.setVisible(true);
		innerGamePanel.setEndOfLevelPanelVisible(false, 0);
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

         if (e.getSource() == hint) {
            //guiManager.setPanelVisible("MainMenu");
         }
      }
   };

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBackground(g);
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
}