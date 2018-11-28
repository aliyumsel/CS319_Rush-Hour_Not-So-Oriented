package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.PlayerDao;
import source.model.LevelInformation;
import source.model.Player;
import source.model.Settings;

class PlayerDaoImpl implements PlayerDao {

	@Override
	public ArrayList<Player> extractPlayers() {
		  ArrayList<Player> players = new ArrayList<>();

	      Scanner playerInfo = null, levelInfo = null;
	      String playerName, tmp, status, mapLine, map = "";
	      int starAmount, levelNo, currentStars, currentNumberOfMoves, movesForThreeStars, movesForTwoStars;
	      ArrayList<LevelInformation> levels;
	      Settings settings;
	      boolean music, sfx, unlocked;
	      Settings.Theme theme;

	      //initiates the players
	      File folder = new File("src/data/players");
	      File[] list = folder.listFiles();

	      if (list.length == 0)
	      {
	         System.out.println("no players");
	         return null;
	         //createPlayer("default");
	      }
	      //numberOfPlayers = list.length;

	      for (int i = 0; i < list.length; i++)
	      {
	         System.out.println(list[i].getPath());
	      }

	      for (int i = 0; i < list.length; i++)
	      {
	         levels = new ArrayList<>();
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

	            unlocked = !tmp.equals("false");
	            
	            while (!playerInfo.nextLine().trim().equals("<Map>"));
	            mapLine = playerInfo.nextLine().trim();
	            if (!mapLine.equals("<Map/>"))
	            {
	            	map = map + mapLine;
	            	mapLine = playerInfo.nextLine().trim();
	            	while (!mapLine.equals("<Map/>"))
	            	{
	            		map = map + mapLine;
	                	mapLine = playerInfo.nextLine().trim();
	            	}
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

	            levels.add(new LevelInformation(currentStars, status, levelNo, movesForThreeStars, movesForTwoStars, currentNumberOfMoves, unlocked, map));

	            while (!playerInfo.nextLine().trim().equals("<Level/>"));
	         }

	         while (!playerInfo.nextLine().trim().equals("<Music>"));
	         tmp = playerInfo.nextLine().trim();

	         music = !tmp.equals("false");

	         while (!playerInfo.nextLine().trim().equals("<Sfx>"));
	         tmp = playerInfo.nextLine().trim();

	         sfx = !tmp.equals("false");

	         while (!playerInfo.nextLine().trim().equals("<Theme>"));
	         tmp = playerInfo.nextLine().trim();
	         if (tmp.trim().equals("SPACE"))
	         {
	            theme = Settings.Theme.SPACE;
	         }
	         else if (tmp.trim().equals("SAFARI"))
	         {
	            theme = Settings.Theme.SAFARI;
	         }
	         else if (tmp.trim().equals("SIMPLE"))
	         {
	            theme = Settings.Theme.SIMPLE;
	         }
	         else
	         {
	            theme = Settings.Theme.CLASSIC;
	         }

	         settings = new Settings(music, sfx, theme);

	         Player player = new Player(playerName, starAmount, levels, "src/data/players/" + playerName, settings);
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

	      while (!info.nextLine().trim().equals("<LastActivePlayer>"));
	      return info.nextLine().trim();
	}

	@Override
	public Player cratePlayer(String playerName) {
		  Scanner scanInfo = null, levelInfo = null;
	      int playerAmount, mapAmount, movesForThreeStars, movesForTwoStars;
	      String tmp, playerPath, playerInfo;
	      ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();

	      try
	      {
	         scanInfo = new Scanner(new File("src/data/info.txt"));
	      } catch (FileNotFoundException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }

			/*
			while(!scanInfo.nextLine().equals("<NumberOfPlayers>"));
			tmp = scanInfo.nextLine().trim();
			playerAmount = Integer.parseInt(tmp);
			*/

	      while ( !scanInfo.nextLine().equals("<NumberOfMaps>") )
	      {
	         ;
	      }
	      tmp = scanInfo.nextLine().trim();
	      mapAmount = Integer.parseInt(tmp);

	      playerPath = "src/data/players/" + playerName;

	      //creates player folder
	      File newFolder = new File(playerPath);
	      newFolder.mkdirs();

	      File newFile = new File(playerPath + "/playerInfo.txt");
	      try
	      {
	         newFile.createNewFile();
	      } catch (IOException e)
	      {
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

	      for ( int i = 1; i <= mapAmount; i++ )
	      {
	         try
	         {
	            levelInfo = new Scanner(new File("src/data/levels/level" + i + ".txt"));
	         } catch (FileNotFoundException e)
	         {
	            e.printStackTrace();
	         }

	         while ( !levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>") )
	         {
	            ;
	         }
	         tmp = levelInfo.nextLine().trim();
	         movesForThreeStars = Integer.parseInt(tmp);

	         while ( !levelInfo.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>") )
	         {
	            ;
	         }
	         tmp = levelInfo.nextLine().trim();
	         movesForTwoStars = Integer.parseInt(tmp);

	         levelInfo.close();
	         LevelInformation level;
	         if ( i == 1 )
	         {
	        	level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, true, "");
	            playerInfo = playerInfo + level.levelToString(i, 0, 0, "notStarted", true, "");
	            
	         }
	         else
	         {
	        	level = new LevelInformation(0, "notStarted", i, movesForThreeStars, movesForTwoStars, 0, false, "");
	            playerInfo = playerInfo + level.levelToString(i, 0, 0, "notStarted", false, "");
	            
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
	      try
	      {
	         fileOut = new FileWriter(playerPath + "/playerInfo.txt");
	         fileOut.write(playerInfo);
	         fileOut.flush();
	         fileOut.close();
	      } catch (IOException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }

	      scanInfo.close();
	      
	      Player newPlayer = new Player(playerName, 0, levels, playerPath, new Settings(true, true));
	      newPlayer.setLastUnlockedLevelNo(1);
	      
	      return newPlayer;
		
	}

	@Override
	public boolean deletePlayer(Player player) {
		File file = new File(player.getPath() + "/playerInfo.txt");
        File folder = new File(player.getPath());
        file.delete();
        if ( folder.delete() )
        {
        	return true;
        }
        return false;
		
	}

	@Override
	public void saveLastActivePlayer(String playerName) {
		String text, line;
	      Scanner scanInfo = null;

	      try
	      {
	         scanInfo = new Scanner(new File("src/data/info.txt"));
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }

	      line = scanInfo.nextLine();
	      text = line + "\n";
	      while ( !line.trim().equals("<LastActivePlayer>") )
	      {
	         line = scanInfo.nextLine();
	         text = text + line + "\n";
	      }

	      text = text + "\t" + playerName + "\n";

	      scanInfo.nextLine();
	      while ( scanInfo.hasNext() )
	      {
	         line = scanInfo.nextLine();
	         text = text + line + "\n";
	      }

	      FileWriter fileOut = null;
	      try
	      {
	         fileOut = new FileWriter("src/data/info.txt");
	         fileOut.write(text);
	         fileOut.flush();
	         fileOut.close();
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
		
	}

	@Override
	public void saveLevel(int levelNo, Player player) {
		  Scanner scan = null;
		  LevelInformation levelToBeSaved = player.getLevels().get(levelNo - 1);
		  int stars = levelToBeSaved.getStars();
		  int currentNumberOfMoves =  levelToBeSaved.getCurrentNumberOfMoves();
		  boolean unlocked =  levelToBeSaved.isUnlocked();
		  String status =  levelToBeSaved.getStatus();
		  String map =  levelToBeSaved.getMap();
	      try
	      {
	         scan = new Scanner(new File(player.getPath() + "/playerInfo.txt"));
	      } catch (FileNotFoundException e1)
	      {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      }

	      String line, text;
	      int no;

	      String levelStr = levelToBeSaved.levelToString(levelNo, stars, currentNumberOfMoves, status, unlocked, map);
	      int levelCounter = 0;
	      boolean checkLevel = true;

	      line = scan.nextLine();
	      text = line + "\n";
	      while ( !line.trim().equals("<Levels>") )
	      {
	         line = scan.nextLine();
	         text = text + line + "\n";
	      }
	      //line = scan.nextLine();
	      //text = text + line + "\n";
	      while ( !line.trim().equals("<Levels/>") )
	      {
	         line = scan.nextLine();
	         if ( line.trim().equals("<Level>") && checkLevel )
	         {
	            levelCounter++;
	            if ( levelCounter == levelNo )
	            {
	               text = text + levelStr;
	               checkLevel = false;
	               while ( !scan.nextLine().trim().equals("<Level/>") );
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
	      while ( scan.hasNext() )
	      {
	         line = scan.nextLine();
	         text = text + line + "\n";
	      }

	      FileWriter fileOut = null;
	      try
	      {
	         fileOut = new FileWriter(player.getPath() + "/playerInfo.txt");
	         fileOut.write(text);
	         fileOut.flush();
	         fileOut.close();
	      } catch (IOException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
		
	}

	@Override
	public void saveSettings(Player player) {
		// TODO Auto-generated method stub
		
	}

}
