package source.view;

import source.controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndOfLevelPanel extends JPanel {
	GuiPanelManager guiManager;

	private JButton retry;
	private JButton menu;
	private JButton nextLevel;
	private JLabel heading;
	private JLabel stars;

	public EndOfLevelPanel(GuiPanelManager _guiManager) {
		super(null);
		guiManager = _guiManager;
		setPreferredSize(new Dimension(250, 250));
		createComponents();
		addComponents();
		setBoundsOfComponents();
	}

	void updatePanel() {
		if (!isShowing()) {
			return;
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.orange);
	}

	private void createComponents() {
		heading = new JLabel("DONE!", SwingConstants.CENTER);
		heading.setPreferredSize(new Dimension(600, 40));
		heading.setFont(new Font("Calibri", Font.BOLD + Font.ITALIC, 15));
		stars = new JLabel("stars", SwingConstants.CENTER);
		stars.setPreferredSize(new Dimension(600, 40));
		stars.setFont(new Font("Calibri", Font.ITALIC, 15));
		retry = new JButton(new ImageIcon("src/image/back.png"));
		retry.setPreferredSize(new Dimension(50, 50));
		retry.setFocusable(false);
		retry.addActionListener(actionListener);

		menu = new JButton(new ImageIcon("src/image/back.png"));
		menu.setPreferredSize(new Dimension(50, 50));
		menu.setFocusable(false);
		menu.addActionListener(actionListener);

		nextLevel = new JButton(new ImageIcon("src/image/back.png"));
		nextLevel.setPreferredSize(new Dimension(50, 50));
		nextLevel.setFocusable(false);
		nextLevel.addActionListener(actionListener);
	}

	private void addComponents() {
		add(retry);
		add(menu);
		add(nextLevel);
		add(heading);
		add(stars);
	}

	private void setBoundsOfComponents() {
		Insets insets = getInsets();

		Dimension size;
		size = retry.getPreferredSize();

		heading.setBounds(100 + insets.left, 30 + insets.top, size.width, size.height);
		stars.setBounds(100 + insets.left, 100 + insets.top, size.width, size.height);
		retry.setBounds(30 + insets.left, 170 + insets.top, size.width, size.height);
		menu.setBounds(100 + insets.left, 170 + insets.top, size.width, size.height);
		nextLevel.setBounds(170 + insets.left, 170 + insets.top, size.width, size.height);
	}

	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == retry) {
				GameEngine.instance.gameManager.resetLevel();
			}

			if (e.getSource() == menu) {
				guiManager.setPanelVisible("MainMenu");
			}

			if (e.getSource() == nextLevel) {
				GameEngine.instance.gameManager.nextLevel();
			}
		}
	};
}
