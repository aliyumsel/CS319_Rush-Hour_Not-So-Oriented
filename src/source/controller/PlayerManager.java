package source.controller;

import java.util.ArrayList;

import interfaces.PlayerDao;
import source.model.LevelInformation;
import source.model.Player;
import source.model.Settings;

public class PlayerManager extends Controller {
   public static PlayerManager instance;

   private Player currentPlayer;
   private ArrayList<Player> players;
   public int numberOfPlayers;

   private PlayerDao playerDao;

   public PlayerManager() {
      instance = this;
      playerDao = new PlayerDaoImpl();
      extractPlayers();
   }

   private void extractPlayers() {
      players = playerDao.extractPlayers();
      String lastPlayerName = playerDao.extractLastPlayerName();
      if (players == null) {
         createPlayer("default");
      }

      if (players.size() == 1) {
         currentPlayer = players.get(0);
      } else {
         for (Player player : players) {
            if (player.getPlayerName().equals(lastPlayerName)) {
               currentPlayer = player;
               break;
            }
         }
      }
   }

   public Player getCurrentPlayer() {
      return currentPlayer;
   }

   public void setCurrentPlayer(Player currentPlayer) {
      this.currentPlayer = currentPlayer;
   }

   public ArrayList<Player> getPlayers() {
      return players;
   }

   public void setPlayers(ArrayList<Player> players) {
      for (int i = 0; i < players.size(); i++) {
         this.players.set(i, players.get(i));
      }
   }

   /* returns 0 if creation successful
    * returns 1 if the name already exists
    */
   public int createPlayer(String playerName) {
      if (players == null) {
         players = new ArrayList<>();
      }

      //checks if a player with the same name exists
      for (int i = 0; i < players.size(); i++) {
         if (players.get(i).getPlayerName().equals(playerName)) {
            return 1;
         }
      }
      //adds the new player to players and sets it as current player
      boolean initialMusic;
      boolean initialSfx;

      if (players.size() == 0)
      {
         initialMusic = true;
         initialSfx = true;
      }
      else
      {
         initialMusic = GameEngine.instance.soundManager.isThemeSongEnabled();
         initialSfx = GameEngine.instance.soundManager.isEffectsEnabled();
      }

      Settings settings = new Settings(initialMusic, initialSfx);
      Player newPlayer = playerDao.cratePlayer(playerName, settings);
      playerDao.saveLastActivePlayer(playerName);
      players.add(newPlayer);
      currentPlayer = newPlayer;

      GameEngine.instance.themeManager.setTheme("minimalistic");

      return 0;
   }

   public int deletePlayer(String name) {
      if (players.size() == 1) {
         return -1;
      }
      boolean deleted = false;
      int deleteIndex = 0;
      for (int i = 0; i < players.size(); i++) {
         if (players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i)) {
            if (playerDao.deletePlayer(players.get(i))) {
               players.remove(i);
               deleted = true;
            }
            deleteIndex = i;
            break;
         }
      }
      if (deleted) {
         //decrementPlayerNumber();

         return deleteIndex;

      }
      return -1;
   }

   public boolean selectPlayer(String name) {
      boolean selected = false;
      for (int i = 0; i < players.size(); i++) {
         if (players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i)) {
            currentPlayer = players.get(i);
            selected = true;
            break;
         }
      }
      if (selected) {
         GameEngine.instance.soundManager.setThemeSong(currentPlayer.getSettings().getMusic());
         GameEngine.instance.soundManager.setEffects(currentPlayer.getSettings().getSfx());
         GameEngine.instance.themeManager.setTheme(currentPlayer.getSettings().getActiveTheme());
         playerDao.saveLastActivePlayer(name);
         return true;
      }
      return false;

   }


   void updateLevelAtTheEnd(int levelNo, int starAmount) {
      LevelInformation currentLevel = currentPlayer.getLevels().get(levelNo - 1);
      setLevelStatus(levelNo, "finished");
      currentLevel.setMap("");
      if (starAmount > currentLevel.getStars()) {
         currentPlayer.setStarAmount(currentPlayer.getStarAmount() + (starAmount - currentLevel.getStars()));
         currentLevel.setStars(starAmount);
      }
      playerDao.saveLevel(levelNo, currentPlayer);
      //playerDao.saveStarAmount(currentPlayer); will be added

   }

   //saveMape kadar oln k�sm� MapController da bir methodla �a�r�labilir
   void updateLevelDuringGame(int levelNo, int moveAmount) {
      setLevelStatus(levelNo, "inProgress");

      String map = MapController.instance.mapToString();
      currentPlayer.getLevels().get(levelNo - 1).setCurrentNumberOfMoves(moveAmount);
      currentPlayer.getLevels().get(levelNo - 1).setMap(map);

      playerDao.saveLevel(levelNo, currentPlayer);
   }

   private void setLevelStatus(int levelNo, String status) {
      if (!currentPlayer.getLevels().get(levelNo - 1).getStatus().equals(status)) {
         currentPlayer.getLevels().get(levelNo - 1).setStatus(status);
      }
   }

   void setLevelStatusFinished(int levelNo) {
      setLevelStatus(levelNo, "finished");
      playerDao.saveLevel(levelNo, currentPlayer);

   }

   void unlockLevel(int levelNo) {
      currentPlayer.getLevels().get(levelNo - 1).unlock();
      playerDao.saveLevel(levelNo, currentPlayer);
   }

   void incrementLastUnlockedLevelNo() {
      currentPlayer.incrementLastUnlockedLevelNo();
   }

   public boolean isLevelLocked(int levelNo) {
      return !currentPlayer.getLevels().get(levelNo - 1).isUnlocked();
   }

   public void toggleMusic() {
      currentPlayer.getSettings().toggleMusic();
      playerDao.saveSettings(currentPlayer);
   }

   public void toggleSfx() {
      currentPlayer.getSettings().toggleSfx();
      playerDao.saveSettings(currentPlayer);
   }

    void changeTheme(String theme) {
        //commented case will be added after testing is done
        if (theme != currentPlayer.getSettings().getActiveTheme() /* && (boolean) currentPlayer.getSettings().getThemes().get(theme) */)
        {
            currentPlayer.getSettings().setActiveTheme(theme);
            playerDao.saveSettings(currentPlayer);
        }
    }

    void unlockTheme(String themeName)
    {
        currentPlayer.getSettings().getThemes().put(themeName, true);
        changeTheme(themeName);
    }

    void decrementRemainingShrinkPowerup() {
        currentPlayer.decrementRemainingShrinkPowerup();
        playerDao.saveRemainingPowerupAmount("shrink", currentPlayer);
    }

   void decrementRemainingSpacePowerup() {
      currentPlayer.decrementRemainingSpacePowerup();
      playerDao.saveRemainingPowerupAmount("space", currentPlayer);
   }

   public void addShrinkPowerup(int amountToBeAdded) {
      currentPlayer.addShrinkPowerup(amountToBeAdded);
   }

   public void addSpacePowerup(int amountToBeAdded) {
      currentPlayer.addSpacePowerup(amountToBeAdded);
   }
}
