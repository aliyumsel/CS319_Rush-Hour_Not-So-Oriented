package source.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class GameInfo {
    private static GameInfo instance = null;

    final int numberOfMaps = 60;
    String lastActivePlayer;

    private class Info
    {
         String lastActivePlayer;
    }
    private Info info;

    private GameInfo()
    {
        instance = this;
        info = new Info();
    }

    public static GameInfo getInstance()
    {
        if(instance == null) {
            instance = new GameInfo();
        }
        return instance;
    }

    boolean extractInfo()
    {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader(DataConfiguration.getInstance().dataPath + "\\info.json");

            try {
                info = gson.fromJson(reader, Info.class);
            }
            catch (JsonParseException e)
            {
                DataErrorHandler.getInstance().handleInfoDamagedError();
            }
            if (info == null)
            {
                DataErrorHandler.getInstance().handleInfoDamagedError();
            }
            lastActivePlayer = info.lastActivePlayer;
            if (lastActivePlayer == null)
            {
                DataErrorHandler.getInstance().handleInfoDamagedError();
            }

            return true;

        } catch (FileNotFoundException e) {
            return false;
        }

    }

    void updateInfo(String lastPlayer)
    {
        Gson gson = new Gson();
        lastActivePlayer = lastPlayer;

        if (info == null)
        {
            info = new Info();
        }

        info.lastActivePlayer = lastPlayer;

        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter(DataConfiguration.getInstance().dataPath + "\\info.json");
            fileOut.write(gson.toJson(info));
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
