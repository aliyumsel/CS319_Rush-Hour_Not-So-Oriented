package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import interfaces.PlayerDao;
import source.model.BonusLevelInformation;
import source.model.LevelInformation;
import source.model.Player;
import source.model.Settings;

@SuppressWarnings("StatementWithEmptyBody")
class PlayerDaoImpl implements PlayerDao {

    @Override
    public ArrayList<Player> extractPlayers() {
        ArrayList<Player> players = new ArrayList<>();

        Scanner playerInfo = null, levelInfo = null;
        String playerName, tmp, status, mapLine, map = "", activeTheme;
        int starAmount, levelNo, currentStars, currentNumberOfMoves, movesForThreeStars, movesForTwoStars, remainingShrinkPowerup, remainingSpacePowerup, time;
        ArrayList<LevelInformation> levels;
        Settings settings;
        boolean music, sfx, unlocked, bonus;
        HashMap<String, Boolean> themes = new HashMap<String, Boolean>();

        //initiates the players
        File folder = new File("src/data/players");
        File[] list = folder.listFiles();

        if (list.length == 0) {
            System.out.println("no players");
            return null;
            //createPlayer("default");
        }
        //numberOfPlayers = list.length;

        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i].getPath());
        }

        for (int i = 0; i < list.length; i++) {
            levels = new ArrayList<>();
            try {
                playerInfo = new Scanner(new File(list[i].getPath() + "/playerInfo.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (!playerInfo.nextLine().trim().equals("<Name>")) ;
            playerName = playerInfo.nextLine().trim();

            while (!playerInfo.nextLine().trim().equals("<StarAmount>")) ;
            tmp = playerInfo.nextLine().trim();
            starAmount = Integer.parseInt(tmp);

            while (!playerInfo.nextLine().trim().equals("<Levels>")) ;

            while (!playerInfo.nextLine().trim().equals("<Levels/>")) {
                bonus = false;
                time = 0;

                while (!playerInfo.nextLine().trim().equals("<LevelNo>")) ;
                tmp = playerInfo.nextLine().trim();
                levelNo = Integer.parseInt(tmp);

                while (!playerInfo.nextLine().trim().equals("<Stars>")) ;
                tmp = playerInfo.nextLine().trim();
                currentStars = Integer.parseInt(tmp);

                while (!playerInfo.nextLine().trim().equals("<CurrentNumberOfMoves>")) ;
                tmp = playerInfo.nextLine().trim();
                currentNumberOfMoves = Integer.parseInt(tmp);

                while (!playerInfo.nextLine().trim().equals("<Status>")) ;
                status = playerInfo.nextLine().trim();

                while (!playerInfo.nextLine().trim().equals("<Unlocked>")) ;
                tmp = playerInfo.nextLine().trim();

                unlocked = !tmp.equals("false");

                while (!playerInfo.nextLine().trim().equals("<Map>")) ;
                mapLine = playerInfo.nextLine().trim();
                if (!mapLine.equals("<Map/>")) {
                    map = map + mapLine;
                    mapLine = playerInfo.nextLine().trim();
                    while (!mapLine.equals("<Map/>")) {
                        map = map + mapLine;
                        mapLine = playerInfo.nextLine().trim();
                    }
                }

                try {
                    levelInfo = new Scanner(new File("src/data/levels/level" + levelNo + ".txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (levelInfo.nextLine().trim().equals("<IsBonusMap>"))
                {
                    time = Integer.parseInt(levelInfo.nextLine().trim());
                    bonus = true;
                }
                if (bonus) {
                    while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>")) ;
                    tmp = levelInfo.nextLine().trim();
                    movesForThreeStars = Integer.parseInt(tmp);
                }
                else
                {
                    tmp = levelInfo.nextLine().trim();
                    movesForThreeStars = Integer.parseInt(tmp);
                }

                while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>")) ;
                tmp = levelInfo.nextLine().trim();
                movesForTwoStars = Integer.parseInt(tmp);

                levelInfo.close();

                if (bonus)
                {
                    levels.add(new BonusLevelInformation(currentStars, status, levelNo, movesForThreeStars, movesForTwoStars, currentNumberOfMoves, unlocked, map, time));
                }
                else {
                    levels.add(new LevelInformation(currentStars, status, levelNo, movesForThreeStars, movesForTwoStars, currentNumberOfMoves, unlocked, map));
                }

                while (!playerInfo.nextLine().trim().equals("<Level/>")) ;
            }

            while (!playerInfo.nextLine().trim().equals("<Music>")) ;
            tmp = playerInfo.nextLine().trim();

            music = !tmp.equals("false");

            while (!playerInfo.nextLine().trim().equals("<Sfx>")) ;
            tmp = playerInfo.nextLine().trim();

            sfx = !tmp.equals("false");

            while (!playerInfo.nextLine().trim().equals("<Theme>")) ;
            while (!playerInfo.nextLine().trim().equals("<Active>")) ;
            activeTheme = playerInfo.nextLine().trim();

            while (!playerInfo.nextLine().trim().equals("<MinimalisticUnlocked>")) ;
            themes.put("minimalistic", playerInfo.nextLine().trim().equals("true"));

            while (!playerInfo.nextLine().trim().equals("<ClassicUnlocked>")) ;
            themes.put("classic", playerInfo.nextLine().trim().equals("true"));

            while (!playerInfo.nextLine().trim().equals("<SafariUnlocked>")) ;
            themes.put("safari", playerInfo.nextLine().trim().equals("true"));

            while (!playerInfo.nextLine().trim().equals("<SpaceUnlocked>")) ;
            themes.put("space", playerInfo.nextLine().trim().equals("true"));

            settings = new Settings(music, sfx, themes, activeTheme);

            while (!playerInfo.nextLine().trim().equals("<RemainingShrinkPowerups>")) ;
            remainingShrinkPowerup = Integer.parseInt(playerInfo.nextLine().trim());

            while (!playerInfo.nextLine().trim().equals("<RemainingSpacePowerups>")) ;
            remainingSpacePowerup = Integer.parseInt(playerInfo.nextLine().trim());

            Player player = new Player(playerName, starAmount, levels, "src/data/players/" + playerName, settings, remainingShrinkPowerup, remainingSpacePowerup);
            player.configureLastUnlockedLevelNo();

            players.add(player);
            playerInfo.close();
        }

        return players;
    }

    @Override
    public String extractLastPlayerName() {
        Scanner info = null;

        try {
            info = new Scanner(new File("src/data/info.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        while (!info.nextLine().trim().equals("<LastActivePlayer>")) ;
        return info.nextLine().trim();
    }

    @Override
    public Player cratePlayer(String playerName, Settings settings) {
        Scanner scanInfo = null, levelInfo = null;
        int playerAmount, mapAmount, movesForThreeStars, movesForTwoStars, time;
        String tmp, playerPath, playerInfo;
        ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();
        boolean unlocked, bonus;

        try {
            scanInfo = new Scanner(new File("src/data/info.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

			/*
			while(!scanInfo.nextLine().equals("<NumberOfPlayers>"));
			tmp = scanInfo.nextLine().trim();
			playerAmount = Integer.parseInt(tmp);
			*/

        while (!scanInfo.nextLine().equals("<NumberOfMaps>")) ;
        tmp = scanInfo.nextLine().trim();
        mapAmount = Integer.parseInt(tmp);

        playerPath = "src/data/players/" + playerName;

        //creates player folder
        File newFolder = new File(playerPath);
        newFolder.mkdirs();
        newFolder.setWritable(true);

        File newFile = new File(playerPath + "/playerInfo.txt");
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //fills the playerInfo file and LevelInformation
        playerInfo = "<Player>\n" +
                "\t<Name>\n" +
                "\t\t" + playerName + "\n" +
                "\t<Name/>\n" +
                "\t<StarAmount>\n" +
                "\t\t0\n" +
                "\t<StarAmount/>\n" +
                "\t<Levels>\n";

        for (int i = 1; i <= mapAmount; i++) {
            bonus = false;
            time = 0;
            try {
                levelInfo = new Scanner(new File("src/data/levels/level" + i + ".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (levelInfo.nextLine().trim().equals("<IsBonusMap>"))
            {
                time = Integer.parseInt(levelInfo.nextLine().trim());
                bonus = true;
            }
            if (bonus) {
                while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>")) ;
                tmp = levelInfo.nextLine().trim();
                movesForThreeStars = Integer.parseInt(tmp);
            }
            else
            {
                tmp = levelInfo.nextLine().trim();
                movesForThreeStars = Integer.parseInt(tmp);
            }

            while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>")) ;
            tmp = levelInfo.nextLine().trim();
            movesForTwoStars = Integer.parseInt(tmp);

            levelInfo.close();


            LevelInformation level;
            if (i == 1) {
                unlocked = true;

            } else {
                unlocked = false;

            }
            if (bonus)
            {
                level = new BonusLevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, unlocked, "", time);            }
            else {
                level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, unlocked, "");
            }
            playerInfo = playerInfo + level.levelToString();
            levels.add(level);
        }
        playerInfo = playerInfo +
                "\t<Levels/>\n" +
                "\t<Settings>\n" +
                settings.settingsToString() +
                "\t<Settings/>\n" +
                "\t<RemainingShrinkPowerups>\n" +
                "\t\t3\n" +
                "\t<RemainingShrinkPowerups/>\n" +
                "\t<RemainingSpacePowerups>\n" +
                "\t\t3\n" +
                "\t<RemainingSpacePowerups/>\n" +
                "<Player/>\n";

        writeFile(playerPath + "/playerInfo.txt", playerInfo);

        scanInfo.close();

        Player newPlayer = new Player(playerName, 0, levels, playerPath, settings, 3, 3);
        newPlayer.resetLastUnlockedLevelNo();

        return newPlayer;
    }

    @Override
    public boolean deletePlayer(Player player) {
        File file = new File(player.getPath() + "/playerInfo.txt");
        File folder = new File(player.getPath());
        file.delete();
        if (folder.delete()) {
            return true;
        }
        return false;

    }

    @Override
    public void saveLastActivePlayer(String playerName) {
        String text, line;
        Scanner scanInfo = null;

        try {
            scanInfo = new Scanner(new File("src/data/info.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        line = scanInfo.nextLine();
        text = line + "\n";
        while (!line.trim().equals("<LastActivePlayer>")) {
            line = scanInfo.nextLine();
            text = text + line + "\n";
        }

        text = text + "\t" + playerName + "\n";

        scanInfo.nextLine();
        while (scanInfo.hasNext()) {
            line = scanInfo.nextLine();
            text = text + line + "\n";
        }

        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter("src/data/info.txt");
            fileOut.write(text);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveLevel(int levelNo, Player player) {
        Scanner scan = null;
        int totalStars = player.getStarAmount();
        LevelInformation levelToBeSaved = player.getLevels().get(levelNo - 1);
        String status = levelToBeSaved.getStatus();
        try {
            scan = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String line, text;
        int no;

        String levelStr = levelToBeSaved.levelToString();
        int levelCounter = 0;
        boolean checkLevel = true;

        line = scan.nextLine();
        text = line + "\n";

        if (status.equals("finished")) {
            while (!line.trim().equals("<StarAmount>")) {
                line = scan.nextLine();
                text = text + line + "\n";
            }
            text = text + "\t\t" + totalStars + "\n";
            line = scan.nextLine();
        }
        while (!line.trim().equals("<Levels>")) {
            line = scan.nextLine();
            text = text + line + "\n";
        }
        //line = scan.nextLine();
        //text = text + line + "\n";
        while (!line.trim().equals("<Levels/>")) {
            line = scan.nextLine();
            if (line.trim().equals("<Level>") && checkLevel) {
                levelCounter++;
                if (levelCounter == levelNo) {
                    text = text + levelStr;
                    checkLevel = false;
                    while (!scan.nextLine().trim().equals("<Level/>")) ;
                } else {
                    text = text + line + "\n";
                }
            } else {
                text = text + line + "\n";
            }
        }
        while (scan.hasNext()) {
            line = scan.nextLine();
            text = text + line + "\n";
        }

        writeFile(player.getPath() + "/playerInfo.txt", text);

    }

    @Override
    public void saveSettings(Player player) {
        Scanner scan = null;
        String line, text;
        Settings settings = player.getSettings();

        try {
            scan = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        line = scan.nextLine();
        text = line + "\n";

        while (!line.trim().equals("<Settings>")) {
            line = scan.nextLine();
            text = text + line + "\n";
        }
        text = text + settings.settingsToString();
        while (!line.trim().equals("<Settings/>")) {
            line = scan.nextLine();
        }
        text = text + line + "\n";

        while (scan.hasNext()) {
            line = scan.nextLine();
            text = text + line + "\n";
        }
        writeFile(player.getPath() + "/playerInfo.txt", text);
    }

    @Override
    public void saveRemainingPowerupAmount(String powerup, Player player) {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String powerupTag;
        int newAmount;
        String line, text;

        if (powerup.equals("shrink")) {
            powerupTag = "<RemainingShrinkPowerups>";
            newAmount = player.getRemainingShrinkPowerup();
        } else {
            powerupTag = "<RemainingSpacePowerups>";
            newAmount = player.getRemainingSpacePowerup();
        }

        line = scan.nextLine();
        text = line + "\n";

        while (!line.trim().equals(powerupTag)) {
            line = scan.nextLine();
            text = text + line + "\n";
        }

        scan.nextLine();
        text = text + "\t\t" + newAmount + "\n";

        while (scan.hasNext()) {
            line = scan.nextLine();
            text = text + line + "\n";
        }
        writeFile(player.getPath() + "/playerInfo.txt", text);

    }

    private void writeFile(String path, String text) {
        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter(path);
            fileOut.write(text);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
