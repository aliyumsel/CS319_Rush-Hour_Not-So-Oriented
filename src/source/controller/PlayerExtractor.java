package source.controller;

import source.model.LevelInformation;
import source.model.Player;
import source.model.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class PlayerExtractor
{
   String extractLastPlayerName()
   {
      Scanner info = null;

      try {
         info = new Scanner(new File("src/data/info.txt"));
      } catch (FileNotFoundException e1) {
         e1.printStackTrace();
      }

      while (!info.nextLine().trim().equals("<LastActivePlayer>"));
      return info.nextLine().trim();
   }

   ArrayList<Player> extractPlayers()
   {
      ArrayList<Player> players = new ArrayList<>();

      Scanner info = null, playerInfo = null, levelInfo = null;
      String playerName, tmp, status, lastPlayerName;
      int starAmount, numberOfPlayers, levelNo, currentStars, currentNumberOfMoves, movesForThreeStars, movesForTwoStars;
      ArrayList<LevelInformation> levels;
      Settings settings;
      boolean music, sfx, unlocked;
      Settings.Theme theme;

//      try {
//         info = new Scanner(new File("src/data/info.txt"));
//      } catch (FileNotFoundException e1) {
//         e1.printStackTrace();
//      }

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
//      while (!info.nextLine().trim().equals("<LastActivePlayer>"));
//      lastPlayerName = info.nextLine().trim();

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

         //System.out.println(numberOfPlayers);
//         if (playerName.equals(lastPlayerName))
//         {
//            currentPlayer = player;
//         }
         players.add(player);
         playerInfo.close();
      }
//      this.numberOfPlayers = players.size();
//      if(this.numberOfPlayers == 1)
//         currentPlayer = players.get(0);

//      info.close();

      return players;
   }
}
