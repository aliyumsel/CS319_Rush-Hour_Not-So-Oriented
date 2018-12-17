package interfaces;

import java.util.ArrayList;

import source.model.Player;
import source.model.Settings;


public interface PlayerDao {
	
	ArrayList<Player> extractPlayers();
	
	String extractLastPlayerName();
	
	Player cratePlayer(String playerName, Settings settings);
	
	boolean deletePlayer(Player player);
	
	void saveLastActivePlayer(String playerName);
	
	void saveLevel(int levelNo, Player player);
	
	void saveSettings(Player player);
	
	void saveRemainingPowerupAmount(Player player);

	void savePlayerInfo(Player player);

	void changePlayerName(Player player);
}
