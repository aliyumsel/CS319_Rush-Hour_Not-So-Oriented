package source.controller;

import java.io.File;
import java.io.IOException;

public class DataConfiguration {

    private static DataConfiguration instance = null;

    static String dataPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour";
    public static boolean gameOpenedFirstTime = false;
    private GameInfo gameInfo;
    private DataErrorHandler handler;

    private DataConfiguration()
    {
        handler = DataErrorHandler.getInstance();
        gameInfo = GameInfo.getInstance();
        confirmData();
        gameInfo.extractInfo();
    }

    public static DataConfiguration getInstance()
    {
        if(instance == null) {
            instance = new DataConfiguration();
        }
        return instance;
    }

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
    void setup()
    {
        File folder = new File(dataPath);
        folder.mkdirs();

        folder = new File(dataPath + "\\players");
        folder.mkdirs();

        createInfoFile();
        gameOpenedFirstTime = true;
    }

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
