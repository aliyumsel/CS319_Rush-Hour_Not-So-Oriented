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
   private JLabel[] stars;
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

	private void createComponents()
	{
	   System.out.println();
		heading = new JLabel("Level Completed!", SwingConstants.CENTER);
		heading.setPreferredSize(new Dimension(300, 60));
		heading.setFont(new Font("Odin Rounded", Font.PLAIN, 35));
		heading.setForeground(Color.white);

      stars = new JLabel[3];
      for ( int i = 0; i < stars.length; i++ )
      {
         stars[i] = new JLabel();
         stars[i].setIcon(new ImageIcon(starLockedImage));
      }

		back = UIFactory.createButton(backButtonImage, backButtonHighlightedImage, "square", actionListener);
		retry = UIFactory.createButton(retryButtonImage, retryButtonHighlightedImage, "square", actionListener);
		play = UIFactory.createButton(playButtonImage, playButtonHighlightedImage, "square", actionListener);
	}

	private void addComponents() {
		add(back);
		add(play);
		add(heading);

      for ( int i = 0; i < stars.length; i++ )
      {
         add(stars[i]);
      }
	}

	private void setBoundsOfComponents() {
		Dimension size;
		size = play.getPreferredSize();

		heading.setBounds(50 , 0, heading.getPreferredSize().width,
				heading.getPreferredSize().height);

		back.setBounds(105 , 150 , size.width, size.height);

		play.setBounds(245 , 150 , size.width, size.height);

      for ( int i = 0; i < stars.length; i++ )
      {
         stars[i].setBounds(guiManager.findCenter(panelWidth, stars[i]) + ( 85 * ( i - 1 ) ), 60, stars[i].getPreferredSize().width, stars[i].getPreferredSize().height);
      }
	}

   private void showStars(int starAmount)
   {
      for (int i = 0; i < stars.length; i++)
      {
         if (i < starAmount)
         {
            stars[i].setIcon(new ImageIcon(starImage));
         }
      }
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
