package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import source.model.LevelInformation;
import source.model.Player;

public class PlayerSaver {
	
	public void saveLevel(int levelNo, Player player)
	{
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
	
	public void saveSettings(Player player)
	{
		
	}
	
	public void saveStarAmount(Player player)
	{
		
	}

}
