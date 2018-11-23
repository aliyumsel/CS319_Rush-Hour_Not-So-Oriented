package source.view;

import source.controller.GameEngine;
import source.controller.GameManager;
import source.controller.PlayerManager;
import source.controller.SoundManager;
import source.model.LevelInformation;

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
	private JLabel moveCount;
   private JLabel[] stars;

	private BufferedImage background;
	private BufferedImage backButtonImage;
	private BufferedImage backButtonHighlightedImage;
	private BufferedImage retryButtonImage;
	private BufferedImage retryButtonHighlightedImage;
	private BufferedImage playButtonImage;
	private BufferedImage playButtonHighlightedImage;
	private BufferedImage starImage;
	private BufferedImage starLockedImage;

   private int destinationLevel;

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

	public void initialize(int level)
   {
		this.destinationLevel = level;
		if (destinationLevel > 5)
      {
         return;
      }
      LevelInformation currentLevelInfo = GameEngine.instance.playerManager.getCurrentPlayer().getLevels().get(destinationLevel);
		if (currentLevelInfo.getStatus().equals("notStarted"))
      {
         showStars(-1);
         showNumberOfMoves(-1);
      }
      else if (currentLevelInfo.getStatus().equals("inProgress"))
      {
         showStars(-1);
         showNumberOfMoves(currentLevelInfo.getCurrentNumberOfMoves());
      }
      else if (currentLevelInfo.getStatus().equals("finished"))
      {
         showStars(currentLevelInfo.getStars());
         showNumberOfMoves(currentLevelInfo.getCurrentNumberOfMoves());
      }
	}

	private void createComponents()
	{
	   System.out.println();
		heading = new JLabel("Level Completed!", SwingConstants.CENTER);
		heading.setPreferredSize(new Dimension(300, 60));
		heading.setFont(new Font("Odin Rounded", Font.PLAIN, 35));
		heading.setForeground(Color.white);

		moveCount = new JLabel("Moves: 0", SwingConstants.CENTER);
      moveCount.setPreferredSize(new Dimension(150, 30));
      moveCount.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      moveCount.setForeground(Color.white);


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
		this.add(back);
		add(play);
		add(heading);
      add(moveCount);

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

		moveCount.setBounds(guiManager.findCenter(panelWidth, moveCount), 162, moveCount.getPreferredSize().width, moveCount.getPreferredSize().height);

		back.setBounds(55 , 150 , size.width, size.height);

		play.setBounds(295 , 150 , size.width, size.height);

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

   private void showNumberOfMoves(int _moveCount)
   {
      if (_moveCount == -1)
      {
         moveCount.setVisible(false);
         return;
      }
      moveCount.setText("Moves: " + _moveCount);
      moveCount.setVisible(true);
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
				System.out.println(PlayerManager.instance.getCurrentPlayer().getLevels().get(0).getStatus());
				
				GameEngine.instance.gameManager.loadLevel(destinationLevel);
				GameEngine.instance.gameManager.setLevel(destinationLevel);
				
				guiManager.setPanelVisible("Game");
			}
		}
	};
}
