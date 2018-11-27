package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import source.model.LevelInformation;
import source.model.Player;
import source.model.Settings;
import source.model.Vehicle;

//TODO info dosyas�ndaki last active player her method i�in d�zenle
public class PlayerManager
{

   public static PlayerManager instance;

   private PlayerExtractor playerExtractor;
   
   private PlayerSaver playerSaver;

   private Player currentPlayer;
   private ArrayList<Player> players;
   public int numberOfPlayers;

   public PlayerManager()
   {
      instance = this;

      playerExtractor = new PlayerExtractor();
      playerSaver = new PlayerSaver();
      extractPlayers();
   }

   private void extractPlayers()
   {
      players = playerExtractor.extractPlayers();
      String lastPlayerName = playerExtractor.extractLastPlayerName();
      if ( players == null )
      {
         createPlayer("default");
      }

      if ( players.size() == 1 )
      {
         currentPlayer = players.get(0);
      }
      else
      {
         for ( Player player : players )
         {
            if ( player.getPlayerName().equals(lastPlayerName) )
            {
               currentPlayer = player;
               break;
            }
         }
      }
   }

   public Player getCurrentPlayer()
   {
      return currentPlayer;
   }

   public void setCurrentPlayer(Player currentPlayer)
   {
      this.currentPlayer = currentPlayer;
   }

   public ArrayList<Player> getPlayers()
   {
      return players;
   }

   public void setPlayers(ArrayList<Player> players)
   {
      for ( int i = 0; i < players.size(); i++ )
      {
         this.players.set(i, players.get(i));
      }
   }

   /* returns 0 if creation successful
    * returns 1 if the name already exists
    */
   public int createPlayer(String playerName)
   {
      if ( players == null )
      {
         players = new ArrayList<>();
      }

      //checks if a player with the same name exists
      for ( int i = 0; i < players.size(); i++ )
      {
         if ( players.get(i).getPlayerName().equals(playerName) )
         {
            return 1;
         }
      }

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
      if ( players.size() == 1 )
      {
         return -1;
      }
      boolean deleted = false;
      int deleteIndex = 0;
      for ( int i = 0; i < players.size(); i++ )
      {
         if ( players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i) )
         {
            File file = new File(players.get(i).getPath() + "/playerInfo.txt");
            File folder = new File(players.get(i).getPath());
            file.delete();
            if ( folder.delete() )
            {
               players.remove(i);
               deleted = true;
            }
            deleteIndex = i;
            break;
         }
      }
      if ( deleted )
      {
         //decrementPlayerNumber();

         return deleteIndex;

      }
      return -1;
   }

   public boolean selectPlayer(String name)
   {
      boolean selected = false;
      for ( int i = 0; i < players.size(); i++ )
      {
         if ( players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i) )
         {
            currentPlayer = players.get(i);
            selected = true;
            break;
         }
      }
      if ( selected )
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

      text = text + "\t" + name + "\n";

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
   //not used
   /*
   private void decrementPlayerNumber()
   {
      String text, line, tmp;
      int newNumber, oldNumber;
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
      while ( !line.trim().equals("<NumberOfPlayers>") )
      {
         line = scanInfo.nextLine();
         text = text + line + "\n";
      }

      tmp = scanInfo.nextLine().trim();
      oldNumber = Integer.parseInt(tmp);
      newNumber = oldNumber - 1;
      text = text + "\t" + newNumber + "\n";

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
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   private void incrementPlayerNumber()
   {
      String text, line, tmp;
      int newNumber, oldNumber;
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
      while ( !line.trim().equals("<NumberOfPlayers>") )
      {
         line = scanInfo.nextLine();
         text = text + line + "\n";
      }

      tmp = scanInfo.nextLine().trim();
      oldNumber = Integer.parseInt(tmp);
      newNumber = oldNumber + 1;
      text = text + "\t" + newNumber + "\n";

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
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
	*/
   
   public void updateLevelAtTheEnd(int levelNo, int starAmount)
   {
	   LevelInformation currentLevel = currentPlayer.getLevels().get(levelNo - 1);
	   setLevelStatus(levelNo, "finished");
	   currentLevel.setMap("");
	   currentLevel.setStars(starAmount);
	   currentPlayer.setStarAmount(currentPlayer.getStarAmount() + starAmount);
	   playerSaver.saveLevel(levelNo, currentPlayer);
	   playerSaver.saveStarAmount(currentPlayer);
	   
   }
   //saveMape kadar oln k�sm� MapController da bir methodla �a�r�labilir
   void updateLevel(int levelNo, int moveAmount)
   {
      setLevelStatus(levelNo, "inProgress");
      
      String map = MapController.instance.mapStrToString();
      currentPlayer.getLevels().get(levelNo - 1).setCurrentNumberOfMoves(moveAmount);
      currentPlayer.getLevels().get(levelNo - 1).setMap(map);

      playerSaver.saveLevel(levelNo, currentPlayer);
   }

   private void setLevelStatus(int levelNo, String status)
   {
      if ( !currentPlayer.getLevels().get(levelNo - 1).getStatus().equals(status) )
      {
         currentPlayer.getLevels().get(levelNo - 1).setStatus(status);
      }
   }

   void setLevelStatusFinished(int levelNo)
   {
      setLevelStatus(levelNo, "finished");
      playerSaver.saveLevel(levelNo, currentPlayer);

   }

   void unlockLevel(int levelNo)
   {
      currentPlayer.getLevels().get(levelNo - 1).unlock();
      playerSaver.saveLevel(levelNo, currentPlayer);
   }

   void incrementLastUnlockedLevelNo()
   {
      currentPlayer.incrementLastUnlockedLevelNo();
   }

   public boolean isLevelLocked(int levelNo)
   {
      return !currentPlayer.getLevels().get(levelNo - 1).isUnlocked();
   }
}
