package source.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import source.controller.*;

public class GamePanel extends JPanel {

	GuiPanelManager guiManager;

	private int index;
	private InnerGamePanel innerGamePanel;
	private JButton back;
	private JButton reset;
	private JLabel timerIcon;
	private JLabel moveLabel;
	private JLabel numberLabel;
	private JProgressBar timer;

	public GamePanel(int index, GuiPanelManager _guiManager) {
		super(null);

		guiManager = _guiManager;

		setPreferredSize(new Dimension(763, 468));
		this.index = index;
		createComponents();

		add(back);
		add(reset);
		add(moveLabel);
		add(numberLabel);
		add(timerIcon);
		add(timer);

		createInnerGamePanel();
		setBoundsOfComponents();

		setVisible(true);
		setOpaque(false);
	}

	private void createComponents() {
		back = new JButton(new ImageIcon("src/image/back.png"));
		back.setPreferredSize(new Dimension(48, 48));
		reset = new JButton(new ImageIcon("src/image/reset.png"));
		reset.setPreferredSize(new Dimension(48, 48));
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

		back.addActionListener(actionListener);

		reset.addActionListener(actionListener);
		reset.setFocusable(false);
		back.setFocusable(false);
	}

	private void setBoundsOfComponents() {
		Insets insets = getInsets();
		Dimension size = back.getPreferredSize();
		back.setBounds(32 + insets.left, 46 + insets.top, size.width, size.height);
		size = reset.getPreferredSize();
		reset.setBounds(680 + insets.left, 46 + insets.top, size.width, size.height);
		size = moveLabel.getPreferredSize();
		moveLabel.setBounds(635 + insets.left, 164 + insets.top, size.width, size.height);
		size = numberLabel.getPreferredSize();
		numberLabel.setBounds(635 + insets.left, 200 + insets.top, size.width, size.height);
		size = timerIcon.getPreferredSize();
		timerIcon.setBounds(70 + insets.left, 116 + insets.top, size.width, size.height);
		size = timer.getPreferredSize();
		timer.setBounds(71 + insets.left, 160 + insets.top, size.width, size.height);
		size = innerGamePanel.getPreferredSize();
		innerGamePanel.setBounds(156 + insets.left, 9 + insets.top, size.width, size.height);
	}

	ActionListener actionListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == reset )
			{
			   GameEngine.instance.gameManager.resetLevel();
			}

			if (e.getSource() == back)
         {
            guiManager.setPanelVisible("MainMenu");
         }
		}
	};

	private void createInnerGamePanel() {
		try {
			innerGamePanel = new InnerGamePanel();
			add(innerGamePanel);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
      setVisible(false);
	}

	public void updatePanel()
   {
		innerGamePanel.updatePanel();

		updateNumberOfMoves();

		repaint();
	}

	public void paintComponent(Graphics g) {
	}

	public InnerGamePanel getInnerGamePanel() {
		return innerGamePanel;
	}

	public void updateNumberOfMoves()
   {
      numberLabel.setText(GameEngine.instance.vehicleController.getNumberOfMoves() + "");
   }
}