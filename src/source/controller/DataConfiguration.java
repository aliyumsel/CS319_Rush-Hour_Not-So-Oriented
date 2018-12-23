package source.controller;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * It is class for data configuration.
 */
public class DataConfiguration {

    static String dataPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour";
    public static boolean gameOpenedFirstTime = false;
    public GameInfo gameInfo;
    public DataErrorHandler handler;
    public static DataConfiguration instance;


    /**
     * Empty constructor that initializes values to their specified initial values.
     */
    public DataConfiguration()
    {
        instance = this;
        handler = new DataErrorHandler();
        gameInfo = new GameInfo();
        confirmData();
        gameInfo.extractInfo();

    }

    /**
     * It is to confirm the data.
     */
    private void confirmData()
    {
        File file;
        file = new File(dataPath);
        if (!file.exists())
        {
            setup();
        }

        file = new File (dataPath + "\\players");
        if (!file.exists())
        {
            handler.handlePlayersNotFoundError();
        }

        file = new File(dataPath + "\\info.json");
        if (!file.exists())
        {
            handler.handleInfoDamagedError();
        }
    }


    /**
     * It is for setup new file.
     */
    void setup()
    {
        File folder = new File(dataPath);
        folder.mkdirs();

        folder = new File(dataPath + "\\players");
        folder.mkdirs();

        createInfoFile();
        gameOpenedFirstTime = true;
    }


    /**
     * Creates information file.
     */
    private void createInfoFile()
    {
        File infoFile = new File(dataPath + "\\info.json");
        try {
            infoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameInfo.updateInfo("");
    }

}
