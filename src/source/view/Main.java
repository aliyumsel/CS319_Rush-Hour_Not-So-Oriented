package source.view;

import source.controller.DataConfiguration;
import source.controller.GameEngine;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        DataConfiguration dataConfiguration= new DataConfiguration();
        GameEngine gameEngine = new GameEngine();
        GuiPanelManager guiManager = new GuiPanelManager();
        MyThread myThread = new MyThread(guiManager, gameEngine);

        Timer timer = new Timer();
        timer.schedule(myThread, 0, 1000 / 60);
    }

    public static class MyThread extends TimerTask {
        GuiPanelManager GUIManager;
        GameEngine gameEngine;

        MyThread(GuiPanelManager _GUIManager, GameEngine _gameEngine) {
            GUIManager = _GUIManager;
            gameEngine = _gameEngine;
        }

        public void run() {
            GUIManager.updatePanels();
            gameEngine.run();
        }
    }
}

