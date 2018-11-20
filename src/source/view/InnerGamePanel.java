package source.view;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import source.controller.GameEngine;
import source.model.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import interfaces.*;
import source.controller.Input;

import source.model.Map;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel {
	private GuiPanelManager guiPanelManager;
	private Map map;
	private EndOfLevelPanel endOfLevelPanel;

	public InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException {
		this.guiPanelManager = guiManager;
		setPreferredSize(new Dimension(450, 450));
		createEndOfLevelPanel();
		
		
		
		setVisible(true);
		//setOpaque(false);

	}

	void updatePanel() {
		if (!isShowing()) {
			return;
		}
		map = GameEngine.instance.mapController.getMap();
		endOfLevelPanel.updatePanel();
		// vehicleArray = map.getVehicleArray();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);

		if (map == null) {
			return;
		}

		for (Vehicle vehicle : map.getVehicleArray()) {
			vehicle.draw(g);
		}
	}

	public void setEndOfLevelPanelVisible(boolean bool) {
		endOfLevelPanel.setVisible(bool);
	}

	private void createEndOfLevelPanel() {

		endOfLevelPanel = new EndOfLevelPanel(guiPanelManager);
		add(endOfLevelPanel);
		endOfLevelPanel.setVisible(false);
		Insets insets = getInsets();
		Dimension size = endOfLevelPanel.getPreferredSize();
		//endOfLevelPanel.setBounds(50 + insets.left, 100 + insets.top, size.width, size.height);
	}
}