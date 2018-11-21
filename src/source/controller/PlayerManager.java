package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import source.model.LevelInformation;
import source.model.Player;

public class PlayerManager {
	
	private Player currentPlayer;
	private ArrayList<Player> players;
	
	public static PlayerManager instance;
	
	public PlayerManager()
	{
		instance = this;
		players = new ArrayList<Player>();
		currentPlayer = null;
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
		for (int i = 0; i < players.size(); i++)
		{
			this.players.set(i, players.get(i));
		}
	}

	public void extractPlayers() throws FileNotFoundException
	{
		Scanner info, playerInfo, levelInfo;
		String playerName, tmp, status, lastPlayerName;
		int starAmount, numberOfPlayers, levelNo, currentStars, currentNumberOfMoves, movesForThreeStars, movesForTwoStars;
		ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();
		
		info = new Scanner(new File("src/data/info.txt"));
		
		while (!info.nextLine().trim().equals("<NumberOfPlayers>"));
		tmp = info.nextLine().trim();
		numberOfPlayers = Integer.parseInt(tmp);
		
		if ( numberOfPlayers == 0)
		{
			info.close();
			return;
		}
		
		while (!info.nextLine().trim().equals("<LastActivePlayer>"));
		lastPlayerName = info.nextLine().trim();
		
		for (int i = 0; i < numberOfPlayers; i++)
		{
			playerInfo = new Scanner(new File ("src/data/players/player" + i + "/playerInfo.txt"));
			
			while (!playerInfo.nextLine().trim().equals("<Name>"));
			playerName = playerInfo.nextLine().trim();
			
			while (!playerInfo.nextLine().trim().equals("<StarAmount>"));
			tmp = playerInfo.nextLine().trim();
			starAmount = Integer.parseInt(tmp);
			
			while (!playerInfo.nextLine().trim().equals("<Levels>"));
			
			while (!playerInfo.nextLine().trim().equals("<Levels/>"))
			{
				while (!playerInfo.nextLine().trim().equals("<LevelNo>"));
				tmp = playerInfo.nextLine().trim();
				levelNo = Integer.parseInt(tmp);
				
				while (!playerInfo.nextLine().trim().equals("<Stars>"));
				tmp = playerInfo.nextLine().trim();
				currentStars = Integer.parseInt(tmp);
				
				while (!playerInfo.nextLine().trim().equals("<CurrentNumberOfMoves>"));
				tmp = playerInfo.nextLine().trim();
				currentNumberOfMoves = Integer.parseInt(tmp);
				
				while (!playerInfo.nextLine().trim().equals("<Status>"));
				status = playerInfo.nextLine().trim();
				
				levelInfo = new Scanner(new File("src/data/levels/level" + levelNo + ".txt"));
				
				while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>"));
				tmp = levelInfo.nextLine().trim();
				movesForThreeStars = Integer.parseInt(tmp);
				
				while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>"));
				tmp = levelInfo.nextLine().trim();
				movesForTwoStars = Integer.parseInt(tmp);
				
				levelInfo.close();
				
				levels.add(new LevelInformation(currentStars, status, levelNo, movesForThreeStars, movesForTwoStars, currentNumberOfMoves));
				
				while (!playerInfo.nextLine().trim().equals("<Level/>"));
			}
			Player player = new Player(playerName, starAmount, levels, "src/data/players/player" + i);
			
			if (playerName.equals(lastPlayerName))
			{
				currentPlayer = player;
			}
			players.add(player);
			playerInfo.close();
		}
		info.close();
	}
	/* returns 1 if creation successful
	 * returns 0 if the name already exists
	 * returns -1 if an unknown error occurs
	 */
	public int createPlayer(String playerName)
	{
		return -1;
	}
	
	public void deletePlayer(String name)
	{
		
	}
	
	public void selectPlayer(String name)
	{
		
	}
	

}
