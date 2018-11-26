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
import source.model.Settings;
import source.model.Settings.Theme;
import source.model.Vehicle;

//TODO info dosyas�ndaki last active player her method i�in d�zenle
public class PlayerManager {
	
	public static PlayerManager instance;
	
	private Player currentPlayer;
	private ArrayList<Player> players;
	public int numberOfPlayers;
	
	public PlayerManager()
	{
		players = new ArrayList<Player>();
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
		players.clear();
		Scanner info = null, playerInfo = null, levelInfo = null;
		String playerName, tmp, status, lastPlayerName;
		int starAmount, numberOfPlayers, levelNo, currentStars, currentNumberOfMoves, movesForThreeStars, movesForTwoStars;
		ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();
		Settings settings;
		boolean music, sfx, unlocked;
		Theme theme;
		
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

		if (list.length == 0)
      {
         System.out.println("no players");
         createPlayer("default");
      }

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
				
				while (!playerInfo.nextLine().trim().equals("<Unlocked>"));
				tmp = playerInfo.nextLine().trim();
				if(tmp.equals("false"))
				{
					unlocked = false;
				}
				else 
				{
					unlocked = true;
				}
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
				
				levels.add(new LevelInformation(currentStars, status, levelNo, movesForThreeStars, movesForTwoStars, currentNumberOfMoves, unlocked));
				
				while (!playerInfo.nextLine().trim().equals("<Level/>"));
			}
			
			while (!playerInfo.nextLine().trim().equals("<Music>"));
			tmp = playerInfo.nextLine().trim();
			if(tmp.equals("false"))
			{
				music = false;
			}
			else
			{
				music = true;
			}
			
			while (!playerInfo.nextLine().trim().equals("<Sfx>"));
			tmp = playerInfo.nextLine().trim();
			if(tmp.equals("false"))
			{
				sfx = false;
			}
			else
			{
				sfx = true;
			}
			
			while (!playerInfo.nextLine().trim().equals("<Theme>"));
			tmp = playerInfo.nextLine().trim();
			if (tmp.trim().equals("SPACE"))
			{
				theme = Theme.SPACE;
			}			
			else if (tmp.trim().equals("SAFARI"))
			{
				theme = Theme.SAFARI;
			}
			else if (tmp.trim().equals("SIMPLE"))
			{
				theme = Theme.SIMPLE;
			}
			else
			{
				theme = Theme.CLASSIC;
			}
			
			settings = new Settings(music, sfx, theme);
			
			Player player = new Player(playerName, starAmount, levels, "src/data/players/" + playerName, settings);
			player.configureLastUnlockedLevelNo();
			
			//System.out.println(numberOfPlayers);
			if (playerName.equals(lastPlayerName))
			{
				currentPlayer = player;
			}
			players.add(player);
			playerInfo.close();
			
			
		} 
		this.numberOfPlayers = players.size();
		if(this.numberOfPlayers == 1)
			currentPlayer = players.get(0);
		
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
			LevelInformation level; 		
			if (i == 1)
			{
				playerInfo = playerInfo + levelToString(i, 0, 0, "notStarted", true, "");
				level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, true);
			}
			else
			{
				playerInfo = playerInfo + levelToString(i, 0, 0, "notStarted", false, "");
				level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, false);
			}
			levels.add(level);
		}
		playerInfo = playerInfo +
				"\t<Levels/>\n" +
				"\t<Settings>\n" +
				"\t\t<Music>\n" +
				"\t\t\ttrue\n" +
				"\t\t<Music/>\n" +
				"\t\t<Sfx>\n" +
				"\t\t\ttrue\n" +
				"\t\t<Sfx/>\n" +
				"\t\t<Theme>\n" +
				"\t\t\tCLASSIC\n" +
				"\t\t<Theme/>\n" +
				"\t<Settings>\n" +
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
        Player newPlayer = new Player(playerName, 0, levels, playerPath, new Settings(true, true));
        newPlayer.setLastUnlockedLevelNo(1);
        players.add(newPlayer);
        currentPlayer = newPlayer;
        setLastActivePlayer(playerName);
      //  incrementPlayerNumber();
        scanInfo.close();
        
		return 0;
	}
	
	public int deletePlayer(String name)
	{
		if (players.size() == 1)
		{
			return - 1;
		}
		boolean deleted = false;
		int deleteIndex = 0;
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
				deleteIndex = i;
				break;
			}
		}
		if (deleted)
		{
			//decrementPlayerNumber();
			
	        return deleteIndex;
					
		}
		return -1;
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
	/*
	//moveCount will be added as well
	public void updateLevelForPlayer(int levelNo, String status)
	{
		if (!currentPlayer.getLevels().get(levelNo -1).getStatus().equals(status))
		{
			currentPlayer.getLevels().get(levelNo -1).setStatus(status);
		}
	}
	*/
	
	private String levelToString(int levelNo, int stars, int currentNumberOfMoves, String status, boolean unlocked, String map)
	{
		String level = "\t\t<Level>\n" +
				"\t\t\t<LevelNo>\n" +
				"\t\t\t\t" + levelNo + "\n" +
				"\t\t\t<LevelNo/>\n" +
				"\t\t\t<Stars>\n" +
				"\t\t\t\t" + stars + "\n" +
				"\t\t\t<Stars/>\n" +
				"\t\t\t<CurrentNumberOfMoves>\n" +
				"\t\t\t\t" + currentNumberOfMoves + "\n" +
				"\t\t\t<CurrentNumberOfMoves>\n" +
				"\t\t\t<Status>\n" +
				"\t\t\t\t" + status + "\n" +
				"\t\t\t<Status/>\n" +
				"\t\t\t<Unlocked>\n" +
				"\t\t\t\t" + unlocked + "\n" +
				"\t\t\t<Unlocked/>\n" +
				"\t\t\t<Map>\n" +
				map +
				"\t\t\t<Map/>\n" +
				"\t\t<Level/>\n";
		return level;
	}
	
	//saveMape kadar oln k�sm� MapController da bir methodla �a�r�labilir
	public void updateLevel(int levelNo, int moveAmount, ArrayList<Vehicle> vehicleList)
	{
		setLevelStatus(levelNo, "inProgress");
		currentPlayer.getLevels().get(levelNo - 1).setCurrentNumberOfMoves(moveAmount);
		
		String map = "";
		boolean found;
		int mapSize = MapController.instance.getMap().getMapSize();
		for (int i = 0; i < mapSize; i++)
		{
			for (int j = 0; j < mapSize; j++)
			{
				found = false;
				for (Vehicle vehicle : vehicleList)
				{
					if (vehicle.transform.getPosition().y == i && vehicle.transform.getPosition().x == j)
					{
						if (vehicle.isPlayer())
						{
							map = map + "PC ";
						}
						else
						{
							map = map + vehicle.getType().substring(0,1).toUpperCase() + vehicle.transform.getDirection().substring(0,1).toUpperCase() + " ";
						}
						found = true;
						break;
					}
				}
				if (!found)
				{
					map = map + "SS ";
				}
			}
			map = map.substring(0, map.length() -1);
			map = map + "\n";
		}
		
		saveLevel(levelNo, currentPlayer.getLevels().get(levelNo -1).getStars(), moveAmount, currentPlayer.getLevels().get(levelNo -1).getStatus(), true, map);
	}
	
	public void updateLevelAtTheEnd(int levelNo, int stars, int moveAmount)
	{
		
	}
	private void saveLevel(int levelNo, int stars, int currentNumberOfMoves, String status, boolean unlocked, String map)
	{
		Scanner scan = null;
		try {
			scan = new Scanner( new File(currentPlayer.getPath() + "/playerInfo.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String line, text;
		int no;
		
		String levelStr = levelToString(levelNo, stars, currentNumberOfMoves, status, unlocked, map);
		int levelCounter = 0;
		boolean checkLevel = true;
		
		line = scan.nextLine();
		text = line + "\n";
		while (!line.trim().equals("<Levels>"))
		{
			line = scan.nextLine();
			text = text + line + "\n";	
		}
		//line = scan.nextLine();
		//text = text + line + "\n";
		while (!line.trim().equals("<Levels/>"))
		{
			line = scan.nextLine();
			if (line.trim().equals("<Level>") && checkLevel)
			{
				levelCounter++;
				if (levelCounter == levelNo)
				{
					text = text + levelStr;
					checkLevel = false;
					while(!scan.nextLine().trim().equals("<Level/>"));
				}
				else
				{
					text = text + line + "\n";
				}
				
			}
			else
			{
				text = text + line + "\n";
			}
			
		}
		while (scan.hasNext())
		{
			line = scan.nextLine();
			text = text + line + "\n";
		}
		
		FileWriter fileOut = null;
        try {
        	fileOut = new FileWriter(currentPlayer.getPath() + "/playerInfo.txt");
			fileOut.write(text);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLevelStatus(int levelNo, String status)
	{
		if (!currentPlayer.getLevels().get(levelNo - 1).getStatus().equals(status))
		{
			int stars, currentNumberOfMoves;
			stars = currentPlayer.getLevels().get(levelNo - 1).getStars();
			currentNumberOfMoves = currentPlayer.getLevels().get(levelNo - 1).getCurrentNumberOfMoves();
			currentPlayer.getLevels().get(levelNo -1).setStatus(status);
			//saveLevel(levelNo, stars, currentNumberOfMoves, status, "");
		}
	}
	
	public void setLevelStatusFinished(int levelNo)
	{
		setLevelStatus(levelNo, "finished");
		int stars, currentNumberOfMoves;
		stars = currentPlayer.getLevels().get(levelNo - 1).getStars();
		currentNumberOfMoves = currentPlayer.getLevels().get(levelNo - 1).getCurrentNumberOfMoves();
		saveLevel(levelNo, stars, currentNumberOfMoves, "finished", true, "");
		
	}
	
	public void unlockLevel(int levelNo)
	{
		currentPlayer.getLevels().get(levelNo - 1).setUnlocked(true);
		saveLevel(levelNo, 0, 0, "notStarted", true, "");
	}
	public void incrementLastUnlockedLevelNoOfCurrentPlayer()
	{
		currentPlayer.incrementLastUnlockedLevelNo();
	}
	
	public boolean isLevelLocked( int levelNo)
	{
		return !currentPlayer.getLevels().get(levelNo - 1).isUnlocked();
	}
}
