package source.controller;

import javax.swing.*;
import java.io.File;

class DataErrorHandler {

    private static DataErrorHandler instance = null;

    private DataErrorHandler()
    {
        //It exists to make the constructor private
    }

    public static DataErrorHandler getInstance() {
        if(instance == null) {
            instance = new DataErrorHandler();
        }
        return instance;
    }

    void handlePlayersNotFoundError()
    {
        int reply = JOptionPane.showConfirmDialog(null, "Couldn't find 'players' folder. Do you accept to setup the game again?", "ERROR LOADING THE GAME", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (reply == JOptionPane.YES_OPTION)
        {
            recreate();
        }
        else
        {
            System.exit(0);
        }
    }

    void handleInfoDamagedError()
    {
        int reply = JOptionPane.showConfirmDialog(null, "'info.json' file is damaged or missing. Do you accept to setup the game again?", "ERROR LOADING THE GAME", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (reply == JOptionPane.YES_OPTION)
        {
            recreate();
        }
        else
        {
            System.exit(0);
        }
    }

    static void handlePlayerDamagedError(String playerName)
    {
        int reply = JOptionPane.showConfirmDialog(null, "Player " + playerName + " is damaged. Do you accept to delete it?", "ERROR LOADING THE GAME", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (reply == JOptionPane.YES_OPTION)
        {
            File file = new File(DataConfiguration.getInstance().dataPath + "\\players\\" + playerName + "\\playerInfo.json");
            if (file.exists())
            {
                System.out.println("inside delte if");
                System.out.println(file.delete());
            }

            file = new File(DataConfiguration.getInstance().dataPath + "\\players\\" + playerName);
            file.delete();
        }
        else
        {
            System.exit(0);
        }
    }

    private void recreate()
    {
        File gameFolder = new File(DataConfiguration.getInstance().dataPath);
        deleteFiles(gameFolder.listFiles());
        DataConfiguration.getInstance().setup();
    }

    private static void deleteFiles(File[] files)
    {
        if (files.length == 0)
        {
            return;
        }
        for (File file : files)
        {
            if (file.isDirectory())
            {
                deleteFiles(file.listFiles());
            }
            file.delete();
        }
    }
}
