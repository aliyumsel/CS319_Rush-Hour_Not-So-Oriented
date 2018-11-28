package interfaces;

import java.util.ArrayList;

import source.model.Player;

public interface PlayerDao {
	
	ArrayList<Player> extractPlayers();
	
	String extractLastPlayerName();
	
	Player cratePlayer(String playerName);
	
	boolean deletePlayer(Player player);
	
	void saveLastActivePlayer(String playerName);
	
	void saveLevel(int levelNo, Player player);
	
	void saveSettings(Player player);
	
	
}
