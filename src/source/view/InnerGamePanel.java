package source.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import source.controller.GameEngine;
import source.controller.SoundManager;
import source.model.*;

import javax.swing.JPanel;

import source.model.Map;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel
{
	private GuiPanelManager guiManager;

   private EndOfLevelPanel endOfLevelPanel;

   private BufferedImage background;

   private Map map;

   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException {
		super(null);
		this.guiManager = guiManager;
		setPreferredSize(new Dimension(480, 480));

		loadImages();
		createEndOfLevelPanel();
		setOpaque(false);
		setVisible(true);
	}

	void updatePanel() {
		if (!isShowing()) {
			return;
		}
		map = GameEngine.instance.mapController.getMap();
		endOfLevelPanel.updatePanel();
		repaint();
	}

   private void loadImages()
   {
      background = guiManager.LoadImage("src/image/roadBackground.png");
   }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//setBackground(Color.WHITE);
      	//drawBackground(g);

		if (map == null) {
			return;
		}

		for (GameObject gameObject : map.getGameObjects()) {
         gameObject.draw(g);
		}
	}

//   private void drawBackground(Graphics graphics) {
//
//      Graphics2D graphics2d = (Graphics2D) graphics;
//
//      graphics2d.drawImage(background, 0, 0, null);
//
//   }

	void setEndOfLevelPanelVisible(boolean bool, int starAmount)
   {
		if(bool) //bool ne ????????????????????????????????????????????????????????????? bool ne
      {
         SoundManager.instance.successSound();
      }
		endOfLevelPanel.showStars(starAmount);
		endOfLevelPanel.setVisible(bool);
	}

	private void createEndOfLevelPanel() {

		endOfLevelPanel = new EndOfLevelPanel(guiManager);
		add(endOfLevelPanel);
		endOfLevelPanel.setVisible(false);

		Dimension size = endOfLevelPanel.getPreferredSize();
		endOfLevelPanel.setBounds(25 , 100 , size.width, size.height);
	}
}