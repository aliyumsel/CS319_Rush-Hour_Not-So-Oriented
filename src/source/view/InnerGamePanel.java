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
   public EndOfLevelPanel endOfLevelPanel;
   public TimeOverPopUp timeOverPopUp;
   private Map map;

    InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException {
        super(null);
        this.guiManager = guiManager;
        setPreferredSize(new Dimension(480, 480));

        createEndOfLevelPanel();
        createTimeOverPopUp();
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (map == null) {
            return;
        }

        for (GameObject gameObject : map.getGameObjects()) {
            gameObject.draw(g);
        }
    }

    void setEndOfLevelPanelVisible(boolean visible, int starAmount) {

        if (visible)
        {
            GameEngine.instance.soundManager.successSound();
        }
        endOfLevelPanel.showStars(starAmount);
        endOfLevelPanel.setVisible(visible);
    }

    void setTimeOverPopUpVisible(boolean visible)
    {
        if (visible)
        {
            //failure sound will be added
        }
        timeOverPopUp.setVisible(visible);
    }

    private void createEndOfLevelPanel() {

        endOfLevelPanel = new EndOfLevelPanel(guiManager);
        add(endOfLevelPanel);
        endOfLevelPanel.setVisible(false);

        Dimension size = endOfLevelPanel.getPreferredSize();
        endOfLevelPanel.setBounds(25, 100, size.width, size.height);
    }

    private void createTimeOverPopUp()
    {
        timeOverPopUp = new TimeOverPopUp(guiManager);
        add(timeOverPopUp);
        timeOverPopUp.setVisible(false);

        Dimension size = timeOverPopUp.getPreferredSize();
        timeOverPopUp.setBounds(25, 100, size.width, size.height);
    }
}