package source.view;

import source.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import source.controller.*;

public class PlayGamePanel extends JPanel {

	GuiPanelManager guiManager;

	private int index;
	private GamePanel game;
	private JButton back;
	private JButton pause;
	private JLabel timerIcon;
	private JLabel moveLabel;
	private JLabel numberLabel;
	private JProgressBar timer;

	public PlayGamePanel(int index, GuiPanelManager _guiManager) {
		super(null);

		guiManager = _guiManager;

		setPreferredSize(new Dimension(763, 468));
		this.index = index;
		createComponents();

		add(back);
		add(pause);
		add(moveLabel);
		add(numberLabel);
		add(timerIcon);
		add(timer);

		setGamePanelVisible();
		setBoundsOfComponents();
		this.setVisible(true);
	}

	private void createComponents() {
		back = new JButton(new ImageIcon("src/image/back.png"));
		back.setPreferredSize(new Dimension(48, 48));
		pause = new JButton(new ImageIcon("src/image/pause.png"));
		pause.setPreferredSize(new Dimension(48, 48));
		timerIcon = new JLabel(new ImageIcon("src/image/timer.png"));
		timerIcon.setPreferredSize(new Dimension(32, 32));
		moveLabel = new JLabel("Number of Moves:");
		moveLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
		moveLabel.setPreferredSize(new Dimension(107, 21));
		numberLabel = new JLabel("0", SwingConstants.CENTER);
		numberLabel.setPreferredSize(new Dimension(107, 68));
		numberLabel.setFont(new Font("Calibri", Font.BOLD, 60));
		timer = new JProgressBar(SwingConstants.VERTICAL);
		timer.setPreferredSize(new Dimension(30, 300));
	}

	private void setBoundsOfComponents() {
		Insets insets = getInsets();
		Dimension size = back.getPreferredSize();
		back.setBounds(32 + insets.left, 46 + insets.top, size.width, size.height);
		size = pause.getPreferredSize();
		pause.setBounds(680 + insets.left, 46 + insets.top, size.width, size.height);
		size = moveLabel.getPreferredSize();
		moveLabel.setBounds(635 + insets.left, 164 + insets.top, size.width, size.height);
		size = numberLabel.getPreferredSize();
		numberLabel.setBounds(635 + insets.left, 200 + insets.top, size.width, size.height);
		size = timerIcon.getPreferredSize();
		timerIcon.setBounds(70 + insets.left, 116 + insets.top, size.width, size.height);
		size = timer.getPreferredSize();
		timer.setBounds(71 + insets.left, 160 + insets.top, size.width, size.height);
		size = game.getPreferredSize();
		game.setBounds(156 + insets.left, 9 + insets.top, size.width, size.height);
		
        
	}

	private void setGamePanelVisible() {

		try {
			game = new GamePanel();
			add(game);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		game.setVisible(true);
	}

	public void updateGamePanel() {

		game.updatePanel();

		game.setVisible(true);
	}

	public void updatePanel() {
		repaint();
	}

	public void paintComponent(Graphics g) {
	}

	public int getIndex() {
		return index;
	}

	public GamePanel getGamePanel() {
		return game;
	}

	void setListeners() {
		game.setListeners();
	}
}