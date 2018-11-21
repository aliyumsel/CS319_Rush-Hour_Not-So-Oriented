package source.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import source.controller.GameEngine;
import source.model.*;

import javax.swing.JPanel;

import source.model.Map;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel {
	private GuiPanelManager guiManager;
	private Map map;
	private EndOfLevelPanel endOfLevelPanel;

   private BufferedImage background;

   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException {
		super(null);
		this.guiManager = guiManager;
		setPreferredSize(new Dimension(450, 450));

		loadImages();

		createEndOfLevelPanel();

		setVisible(true);
		// setOpaque(false);

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

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/roadBackground.png");
   }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
      	drawBackground(g);

		if (map == null) {
			return;
		}

		for (Vehicle vehicle : map.getVehicleArray()) {
			vehicle.draw(g);
		}
	}

   private void drawBackground(Graphics graphics) {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

	public void setEndOfLevelPanelVisible(boolean bool) {
		endOfLevelPanel.setVisible(bool);
	}

	private void createEndOfLevelPanel() {

		endOfLevelPanel = new EndOfLevelPanel(guiManager);
		add(endOfLevelPanel);
		endOfLevelPanel.setVisible(false);
		Insets insets = getInsets();
		Dimension size = endOfLevelPanel.getPreferredSize();
		endOfLevelPanel.setBounds(25 + insets.left, 100 + insets.top, size.width, size.height);
	}
}