package source.controller;

import javax.swing.*;
import java.io.File;

/**
 * It is class to handle data errors.
 */
public class DataErrorHandler {

    static DataErrorHandler instance;

    /**
     * Empty constructor that initializes values to their specified initial values.
     */
    public DataErrorHandler()
    {
        instance = this;
    }


    /**
     * Handles the problem when the player is not found.
     */
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

    /**
     * Handles the error when the information is damaged.
     */
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


    /**
     * Handles the problem of any specific player when
     * there are any damages in players's info.
     * @param playerName
     */
    static void handlePlayerDamagedError(String playerName)
    {
        int reply = JOptionPane.showConfirmDialog(null, "Player " + playerName + " is damaged. Do you accept to delete it?", "ERROR LOADING THE GAME", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (reply == JOptionPane.YES_OPTION)
        {
            File file = new File(DataConfiguration.dataPath + "\\players\\" + playerName + "\\playerInfo.json");
            if (file.exists())
            {
                System.out.println("inside delte if");
                System.out.println(file.delete());
            }

            file = new File(DataConfiguration.dataPath + "\\players\\" + playerName);
            file.delete();
        }
        else
        {
            System.exit(0);
        }
    }


    /**
     * Recreates the files.
     */
    private void recreate()
    {
        File gameFolder = new File(DataConfiguration.dataPath);
        deleteFiles(gameFolder.listFiles());
        DataConfiguration.instance.setup();
    }


    /**
     * Deletes the desired files
     * @param files the files to be deleted.
     */
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
