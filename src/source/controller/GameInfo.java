package source.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class GameInfo {
    final static int numberOfMaps = 50;
    static String lastActivePlayer;
    static GameInfo instance;

    private class Info
    {
         String lastActivePlayer;
    }
    private Info info;

    public GameInfo()
    {
        instance = this;
        info = new Info();
    }
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
