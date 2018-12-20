
package source.controller;

import com.google.gson.Gson;
import interfaces.PlayerDao;
import source.model.*;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class PlayerDaoImpl implements PlayerDao{

    private Gson gson = new Gson();

    @Override
    public ArrayList<Player> extractPlayers()
    {
        ArrayList<Player> players = new ArrayList<>();
        File folder = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\players");
        File[] list = folder.listFiles();

        for (int i = 0; i < list.length; i++)
        {
            players.add(gson.fromJson(createReader(list[i].getPath() + "/playerInfo.json"), Player.class));
        }
        return players;
    }
    @Override
    public String extractLastPlayerName()
    {
        Info info = gson.fromJson(createReader("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\info.json"), Info.class);
        return info.lastActivePlayer;
    }

    @Override
    public Player createPlayer(String playerName, Settings settings) {
        Info info = gson.fromJson(createReader("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\info.json"), Info.class);
        OriginalLevel originalLevel;
        String playerPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\players\\" + playerName;

        //creates player folder
        File newFolder = new File(playerPath);
        newFolder.mkdirs();
        newFolder.setWritable(true);

        createFile(playerPath + "/playerInfo.json");


        boolean unlocked;
        ArrayList<LevelInformation> levels = new ArrayList<>();

        for (int i = 1; i <= info.numberOfMaps; i++)
        {
            InputStream input = PlayerDaoImpl.class.getClassLoader().getResourceAsStream("data/levels/level" + i + ".json");
            InputStreamReader reader = new InputStreamReader(input);
            originalLevel = gson.fromJson(reader, OriginalLevel.class);
            levels.add(new LevelInformation(0, "notStarted", i, originalLevel.expectedMovesForThreeStars, originalLevel.expectedMovesForTwoStars, 0, i == 1, "", originalLevel.time));
        }
        Player newPlayer = new Player(playerName, 0, levels, playerPath, settings, 3, 3);
        newPlayer.resetLastUnlockedLevelNo();
        String text = gson.toJson(newPlayer);
        writeFile(playerPath + "/playerInfo.json", text);
        return newPlayer;
    }

    @Override
    public boolean deletePlayer(Player player){
        File file = new File(player.getPath() + "/playerInfo.json");
        file.delete();

        File folder = new File(player.getPath());
        if (folder.delete()) {
            return true;
        }
        return false;
    }

    @Override
    public void saveLastActivePlayer(String playerName)
    {
        Info info = gson.fromJson(createReader("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\info.json"), Info.class);
        info.lastActivePlayer = playerName;
        String text = gson.toJson(info);
        writeFile("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\info.json", text);
    }

    @Override
    public void savePlayer(Player player)
    {
        String text = gson.toJson(player);
        writeFile(player.getPath() + "/playerInfo.json", text);
    }

    @Override
    public void changePlayerName(Player player){
        File folder = new File(player.getPath());
        folder.renameTo(new File(folder.getParent() + "/" + player.getPlayerName()));
        player.setPath("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour\\players\\"+  player.getPlayerName());
        savePlayer(player);
    }

    private void writeFile(String path, String text) {

        //text = decrypt(text);
        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter(path);
            fileOut.write(text);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileReader createReader(String path)
    {
        try {
            return new FileReader(path);

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private void createFile(String path)
    {
        File newFile = new File(path);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Info {
        private  String lastActivePlayer;
        private  int numberOfMaps;
    }

    // May be needed to fix file issues
    /*
    private void convertLevelsFromTxtToJson()
    {
        OriginalLevel level = new OriginalLevel();
        Scanner scan = null;
        boolean bonus;
        int time, movesForThreeStars, movesForTwoStars;
        String tmp, map, line;
        for (int i = 1; i <= 50; i++)
        {
            bonus = false;
            time = -1;
            movesForThreeStars = 0;
            movesForTwoStars = 0;
            map = "";
            try {
                scan = new Scanner (new File("src/data/levels/level" + i + ".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (scan.nextLine().trim().equals("<IsBonusMap>"))
            {
                time = Integer.parseInt(scan.nextLine().trim());
                bonus = true;
            }
            if (bonus) {
                while (!scan.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>")) ;
                tmp = scan.nextLine().trim();
                movesForThreeStars = Integer.parseInt(tmp);
            }
            else
            {
                tmp = scan.nextLine().trim();
                movesForThreeStars = Integer.parseInt(tmp);
            }

            while (!scan.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>")) ;
            tmp = scan.nextLine().trim();
            movesForTwoStars = Integer.parseInt(tmp);

            while (!scan.nextLine().trim().equals("<Map>")) ;
            line = scan.nextLine().trim();
            while (!line.equals("<Map/>"))
            {
                map = map + line + " | ";
                line = scan.nextLine().trim();
            }
            map = map.substring(0, map.length() - 3);

            level.time = time;
            level.expectedMovesForThreeStars = movesForThreeStars;
            level.expectedMovesForTwoStars = movesForTwoStars;
            level.map = map;

            createFile("src/data/levels/level" + i +".json");
            String text = gson.toJson(level);
            writeFile("src/data/levels/level" + i +".json", text);

        }
    }
    */

}