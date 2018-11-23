package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import source.model.LevelInformation;
import source.model.Player;

//TODO info dosyasýndaki last active player her method için düzenle
public class PlayerManager {
	
	private Player currentPlayer;
	private ArrayList<Player> players = new ArrayList<Player>();
	public int numberOfPlayers;
	public static PlayerManager instance;
	
	public PlayerManager()
	{
		instance = this;
		extractPlayers();
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

	public void extractPlayers()
	{
		Scanner info = null, playerInfo = null, levelInfo = null;
		String playerName, tmp, status, lastPlayerName;
		int starAmount, numberOfPlayers, levelNo, currentStars, currentNumberOfMoves, movesForThreeStars, movesForTwoStars;
		ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();
		
		try {
			info = new Scanner(new File("src/data/info.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
//		while (!info.nextLine().trim().equals("<NumberOfPlayers>"));
//		tmp = info.nextLine().trim();
//		System.out.println(tmp);
		//numberOfPlayers = Integer.parseInt(tmp);
		
//		if ( numberOfPlayers == 0)
//		{
//			info.close();
//			return;
//		}

		//extracts last active player
		while (!info.nextLine().trim().equals("<LastActivePlayer>"));
		lastPlayerName = info.nextLine().trim();
		
		//initiates the players
		File folder = new File("src/data/players");
		File[] list = folder.listFiles();
		for (int i = 0; i < list.length; i++)
		{
			System.out.println(list[i].getPath());
		}
		
		for (int i = 0; i < list.length; i++)
		{
			try {
				playerInfo = new Scanner(new File (list[i].getPath() + "/playerInfo.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
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
				
				try {
					levelInfo = new Scanner(new File("src/data/levels/level" + levelNo + ".txt"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
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
			Player player = new Player(playerName, starAmount, levels, "src/data/players/" + playerName);
			
			//System.out.println(numberOfPlayers);
			if (playerName.equals(lastPlayerName))
			{
				currentPlayer = player;
			}
			players.add(player);
			playerInfo.close();
			this.numberOfPlayers = players.size();
			if(this.numberOfPlayers == 1)
				currentPlayer = player;
		} 
		
		info.close();
		
	}
	
	/* returns 0 if creation successful
	 * returns 1 if the name already exists
	 */
	public int createPlayer(String playerName)
	{
		//checks if a player with the same name exists
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerName().equals(playerName))
			{
				return 1;
			}
		}
		
		Scanner scanInfo = null, levelInfo = null;
		int playerAmount, mapAmount, movesForThreeStars, movesForTwoStars;
		String tmp, playerPath, playerInfo;
		ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();
		
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
		
		while(!scanInfo.nextLine().equals("<NumberOfMaps>"));
		tmp = scanInfo.nextLine().trim();
		mapAmount = Integer.parseInt(tmp);
		
		playerPath = "src/data/players/" + playerName;
		
		//creates player folder
		File newFolder = new File(playerPath);
		newFolder.mkdirs();
		
		File newFile = new File(playerPath +"/playerInfo.txt");
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
		
		for (int i = 1; i <= mapAmount; i++)
		{
			try {
				levelInfo = new Scanner(new File("src/data/levels/level" + i + ".txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>"));
			tmp = levelInfo.nextLine().trim();
			movesForThreeStars = Integer.parseInt(tmp);
			
			while (!levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>"));
			tmp = levelInfo.nextLine().trim();
			movesForTwoStars = Integer.parseInt(tmp);
			
			levelInfo.close();
			
			LevelInformation level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0);
			levels.add(level);
			
			playerInfo = playerInfo +
					"\t\t<Level>\n" +
					"\t\t\t<LevelNo>\n" +
					"\t\t\t\t" + i + "\n" +
					"\t\t\t<LevelNo/>\n" +
					"\t\t\t<Stars>\n" +
					"\t\t\t\t0\n" +
					"\t\t\t<Stars/>\n" +
					"\t\t\t<CurrentNumberOfMoves>\n" +
					"\t\t\t\t0\n" +
					"\t\t\t<CurrentNumberOfMoves>\n" +
					"\t\t\t<Status>\n" +
					"\t\t\t\tnotStarted\n" +
					"\t\t\t<Status/>\n" +
					"\t\t\t<Map>\n" +
					"\t\t\t<Map/>\n" +
					"\t\t<Level/>\n";
		}
		playerInfo = playerInfo +
				"\t<Levels/>\n" +
				"<Player/>\n";
		
		FileWriter fileOut = null;
        try {
        	fileOut = new FileWriter(playerPath +"/playerInfo.txt");
			fileOut.write(playerInfo);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //adds the new player to players and sets it as curent player
        Player newPlayer = new Player(playerName, 0, levels, playerPath);
        players.add(newPlayer);
        currentPlayer = newPlayer;
        setLastActivePlayer(playerName);
      //  incrementPlayerNumber();
        scanInfo.close();
        
		return 0;
	}
	
	public boolean deletePlayer(String name)
	{
		if (players.size() == 1)
		{
			return false;
		}
		boolean deleted = false;
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i))
			{
				File file = new File(players.get(i).getPath() + "/playerInfo.txt");
				File folder = new File(players.get(i).getPath());
				file.delete();
				if (folder.delete())
				{
					players.remove(i);
					deleted = true;
				}
				break;
			}
		}
		if (deleted)
		{
			decrementPlayerNumber();
			
	        return true;
					
		}
		return false;
	}
	
	public boolean selectPlayer(String name)
	{
		boolean selected = false;
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerName().equals(name) &&  currentPlayer != players.get(i))
			{
				currentPlayer = players.get(i);
				selected = true;
				break;
			}
		}
		if (selected)
		{
			setLastActivePlayer(name);
	        return true;
		}
		return false;
		
	}
	
	private void setLastActivePlayer(String name)
	{
		String text, line;
		Scanner scanInfo = null;
		
		try {
			scanInfo = new Scanner (new File("src/data/info.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		line = scanInfo.nextLine();
		text = line + "\n";
		while(!line.trim().equals("<LastActivePlayer>"))
		{
			line = scanInfo.nextLine();
			text = text + line + "\n";				
		}
		
		text = text + "\t" + name + "\n";
		
		scanInfo.nextLine();
		while(scanInfo.hasNext())
		{
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
	
	private void decrementPlayerNumber()
	{
		String text, line, tmp;
		int newNumber, oldNumber;
		Scanner scanInfo = null;
		
		try {
			scanInfo = new Scanner (new File("src/data/info.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		line = scanInfo.nextLine();
		text = line + "\n";
		while(!line.trim().equals("<NumberOfPlayers>"))
		{
			line = scanInfo.nextLine();
			text = text + line + "\n";				
		}
		
		tmp = scanInfo.nextLine().trim();
		oldNumber = Integer.parseInt(tmp);
		newNumber = oldNumber - 1;
		text = text + "\t" + newNumber + "\n";
		
		while(scanInfo.hasNext())
		{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementPlayerNumber()
	{
		String text, line, tmp;
		int newNumber, oldNumber;
		Scanner scanInfo = null;
		
		try {
			scanInfo = new Scanner (new File("src/data/info.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		line = scanInfo.nextLine();
		text = line + "\n";
		while(!line.trim().equals("<NumberOfPlayers>"))
		{
			line = scanInfo.nextLine();
			text = text + line + "\n";				
		}
		
		tmp = scanInfo.nextLine().trim();
		oldNumber = Integer.parseInt(tmp);
		newNumber = oldNumber + 1;
		text = text + "\t" + newNumber + "\n";
		
		while(scanInfo.hasNext())
		{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
