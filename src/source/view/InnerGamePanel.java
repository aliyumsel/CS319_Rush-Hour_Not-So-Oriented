package source.view;

import source.controller.GameEngine;
import source.model.GameObject;
import source.model.Map;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel
{
	private GuiPanelManager guiManager;
   EndOfLevelPanel endOfLevelPanel;
   private TimeOverPopUp timeOverPopUp;
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

    public void paintComponent(Graphics2D g) {
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