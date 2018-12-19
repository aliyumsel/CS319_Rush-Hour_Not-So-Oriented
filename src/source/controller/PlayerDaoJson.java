//
//package source.controller;
//
//import com.google.gson.*;
//
//import interfaces.PlayerDao;
//import source.model.BonusLevelInformation;
//import source.model.LevelInformation;
//import source.model.Player;
//import source.model.Settings;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class PlayerDaoJson  implements PlayerDao{
//
//    private Gson gson = new Gson();
//
//    @Override
//    public ArrayList<Player> extractPlayers(){ // Not Implemented Yet, Hope the others are working.
//        return null;
//    }
//
//    @Override
//    public String extractLastPlayerName(){
//        try {
//            JsonObject obj = new JsonObject();
//            obj = gson.fromJson(new FileReader("src/data/info.json"), JsonObject.class);
//            return obj.getAsJsonPrimitive("LastActivePlayer").getAsString();
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Player cratePlayer(String playerName, Settings settings){
//        try {
//            ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();
//            boolean unlocked, bonus;
//            int mapAmount, time, movesForThreeStars, movesForTwoStars;
//            String playerPath;
//            JsonObject playerInfo, levelsObj, mapInfo, settingsInfo, powerUpInfo;
//            JsonObject info;
//            levelsObj = new JsonObject();
//            settingsInfo = new JsonObject();
//
//            // Read info.json
//            info = gson.fromJson(new FileReader("src/data/info.json"), JsonObject.class);
//
//            mapAmount = info.getAsJsonPrimitive("NumberOfMaps").getAsInt();
//            playerPath = "src/data/players/" + playerName;
//
//            // Create Folders
//            File newFolder = new File(playerPath);
//            newFolder.mkdirs();
//            newFolder.setWritable(true);
//
//            //Create and Write PlayerInfo.json
//            playerInfo = new JsonObject();
//            playerInfo.addProperty("Name", playerName);
//            playerInfo.addProperty("StarAmount", playerName);
//            gson.toJson(playerInfo, new FileWriter(playerPath + "/PlayerInfo.json"));
//
//            // Create and write LevelsInfo.json
//            for(int i = 1; i <= mapAmount; i++){
//                bonus = false;
//                time = 0;
//
//                mapInfo = gson.fromJson(new FileReader("src/data/levels/level" + i + ".json"), JsonObject.class);
//                if(mapInfo.has("IsBonusMap")){
//                    bonus = true;
//                    time = mapInfo.getAsJsonPrimitive("IsBonusMap").getAsInt();
//                }
//                movesForThreeStars = mapInfo.getAsJsonPrimitive("ExpectedNumberOfMovesForThreeStars").getAsInt();
//                movesForTwoStars = mapInfo.getAsJsonPrimitive("ExpectedNumberOfMovesForTwoStars").getAsInt();
//
//                LevelInformation level;
//                unlocked = i == 1;
//
//                if (bonus) {
//                    level = new BonusLevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, unlocked, "", time);
//                }
//                else {
//                    level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, unlocked, "");
//                }
//
//                JsonObject levelInfo = new JsonObject();
//
//                levelInfo.addProperty("LevelNo",level.getLevelNo());
//                levelInfo.addProperty("Stars",level.getStars());
//                levelInfo.addProperty("CurrentNumberOfMoves",level.getCurrentNumberOfMoves());
//                levelInfo.addProperty("Status",level.getStatus());
//                levelInfo.addProperty("Unlocked",level.isUnlocked());
//                levelInfo.addProperty("Map",level.getMap());
//
//
//                levelsObj.add("Levels", new JsonArray());
//                levelsObj.getAsJsonArray("Levels").add(levelInfo);
//
//                levels.add(level);
//
//            }
//            gson.toJson(levelsObj, new FileWriter(playerPath + "/LevelsInfo.json"));
//
//            //Create and Write SettingsInfo.json
//            JsonObject themeObj = new JsonObject();
//            themeObj.addProperty("Active", settings.getActiveTheme());
//            for (String t:settings.getThemes().keySet()) {
//                themeObj.addProperty(t.substring(0,1).toUpperCase() + t.substring(1) + "Unlocked", settings.getThemes().get(t));
//            }
//            settingsInfo.addProperty("Music", settings.getMusic());
//            settingsInfo.addProperty("Sfx", settings.getSfx());
//            settingsInfo.addProperty("ControlPreference", settings.getControlPrefrence());
//            settingsInfo.add("Theme",themeObj);
//
//            //Create and Write PowerUpInfo.json
//            powerUpInfo = new JsonObject();
//            powerUpInfo.addProperty("RemainingShrinkPowerups", 3);
//            powerUpInfo.addProperty("RemainingSpacePowerups", 3);
//            gson.toJson(powerUpInfo, new FileWriter(playerPath + "/PowerUpInfo.json"));
//
//            // Create new player
//            Player newPlayer = new Player(playerName, 0, levels, playerPath, settings, 3, 3);
//            newPlayer.resetLastUnlockedLevelNo();
//
//            return newPlayer;
//
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    @Override
//    public boolean deletePlayer(Player player){
//        File[] files = {new File(player.getPath() + "/PlayerInfo.txt"), new File(player.getPath() + "/LevelsInfo.txt"), new File(player.getPath() + "/SettingsInfo.txt"), new File(player.getPath() + "/PowerUpInfo.txt")};
//        for (int i = 0; i < 4; i++)
//        {
//            files[i].delete();
//        }
//
//        File folder = new File(player.getPath());
//        if (folder.delete()) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void saveLastActivePlayer(String playerName){
//        try {
//            JsonObject obj = new JsonObject();
//            obj = gson.fromJson(new FileReader("src/data/info.json"), JsonObject.class);
//            obj.addProperty("LastActivePlayer", playerName);
//            gson.toJson(obj, new FileWriter("src/data/info.json"));
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void saveLevel(int levelNo, Player player){
//        try {
//            JsonObject levels = gson.fromJson(new FileReader(player.getPath() + "/LevelsInfo.json"), JsonObject.class);
//            LevelInformation levelInfo = player.getLevels().get(levelNo - 1);
//
//            JsonObject levelInfoObj = new JsonObject();
//            levelInfoObj.addProperty("LevelNo",levelInfo.getLevelNo());
//            levelInfoObj.addProperty("Stars",levelInfo.getStars());
//            levelInfoObj.addProperty("CurrentNumberOfMoves",levelInfo.getCurrentNumberOfMoves());
//            levelInfoObj.addProperty("Status",levelInfo.getStatus());
//            levelInfoObj.addProperty("Unlocked",levelInfo.isUnlocked());
//            levelInfoObj.addProperty("Map",levelInfo.getMap());
//
//            levels.getAsJsonArray("Levels").set(levelNo-1,levelInfoObj);
//            gson.toJson(levels, new FileWriter(player.getPath() + "/LevelsInfo.json"));
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void saveSettings(Player player){
//        try {
//            Settings settings = player.getSettings();
//            HashMap<String,Boolean> themes = settings.getThemes();
//            JsonObject themeObj = new JsonObject();
//            themeObj.addProperty("Active", settings.getActiveTheme());
//            for (String t:themes.keySet()) {
//                themeObj.addProperty(t.substring(0,1).toUpperCase() + t.substring(1) + "Unlocked", themes.get(t));
//            }
//            JsonObject settingsInfo = new JsonObject();
//            settingsInfo.addProperty("Music", settings.getMusic());
//            settingsInfo.addProperty("Sfx", settings.getSfx());
//            settingsInfo.addProperty("ControlPreference", settings.getControlPrefrence());
//            settingsInfo.add("Theme",themeObj);
//            gson.toJson(settingsInfo, new FileWriter(player.getPath() + "/PlayerInfo.json"));
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void saveRemainingPowerupAmount(Player player){
//        try {
//            JsonObject powerUpInfo = new JsonObject();
//            powerUpInfo.addProperty("RemainingShrinkPowerups", player.getRemainingShrinkPowerup());
//            powerUpInfo.addProperty("RemainingSpacePowerups", player.getRemainingSpacePowerup());
//            gson.toJson(powerUpInfo, new FileWriter(player.getPath() + "/PowerUpInfo.json"));
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void savePlayerInfo(Player player){
//        try {
//            JsonObject playerInfo = new JsonObject();
//            playerInfo.addProperty("Name", player.getPlayerName());
//            playerInfo.addProperty("StarAmount", player.getStarAmount());
//            gson.toJson(playerInfo, new FileWriter(player.getPath() + "/PlayerInfo.json"));
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void changePlayerName(Player player){
//        File folder = new File(player.getPath());
//        folder.renameTo(new File(folder.getParent() + "/" + player.getPlayerName()));
//        player.setPath("src/data/players/" +  player.getPlayerName());
//        savePlayerInfo(player);
//    }
//
//}