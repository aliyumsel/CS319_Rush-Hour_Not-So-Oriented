package source.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.*;


/**
 * The class for giving information of the game.
 */
public class GameInfo
{
    final static int numberOfMaps = 50;
    static String lastActivePlayer;
    static GameInfo instance;

    /**
     * The inner class which holds last active player's info.
     */
    private class Info
    {
         String lastActivePlayer;
    }
    private Info info;


    /**
     * Empty constructor that initializes values to their specified initial values.
     */
    public GameInfo()
    {
        instance = this;
        info = new Info();
    }


    /**
     * Extracts info.
     * @return true if it is extractad, false otherwise.
     */
    boolean extractInfo()
    {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader(DataConfiguration.dataPath + "\\info.json");

            try {
                info = gson.fromJson(reader, Info.class);
            }
            catch (JsonParseException e)
            {
                DataErrorHandler.instance.handleInfoDamagedError();
            }
            if (info == null)
            {
                DataErrorHandler.instance.handleInfoDamagedError();
            }
            lastActivePlayer = info.lastActivePlayer;
            if (lastActivePlayer == null)
            {
                DataErrorHandler.instance.handleInfoDamagedError();
            }

            return true;

        } catch (FileNotFoundException e) {
            return false;
        }

    }


    /**
     * Updates the last players info.
     * @param lastPlayer the last player who played the game.
     */
    void updateInfo(String lastPlayer)
    {
        Gson gson = new Gson();
        lastActivePlayer = lastPlayer;

        info.lastActivePlayer = lastPlayer;

        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter(DataConfiguration.dataPath + "\\info.json");
            fileOut.write(gson.toJson(info));
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
