package source.model;

import java.util.ArrayList;

public class Player
{
   //private String theme;
   private String playerName;
   private ArrayList<LevelInformation> levels;
   private int starAmount;
   private String path;
   private int lastUnlockedLevelNo;
   private int remainingShrinkPowerup;
   private int remainingSpacePowerup;

   public Settings settings;

   public Player(String playerName, int starAmount, ArrayList<LevelInformation> levels, String path, Settings settings, int remainingShrinkPowerup, int remainingSpacePowerup)
   {
      this.playerName = playerName;
      this.starAmount = starAmount;
      this.path = path;
      this.levels = levels;
      this.remainingShrinkPowerup = remainingShrinkPowerup;
      this.remainingSpacePowerup = remainingSpacePowerup;
      this.settings = settings;
   }

   public String getPath()
   {
      return path;
   }

   public void setPath(String path)
   {
      this.path = path;
   }

   public String getPlayerName()
   {
      return playerName;
   }

   public void setPlayerName(String playerName)
   {
      this.playerName = playerName;
   }

   public int getStarAmount()
   {
      return starAmount;
   }

   public void setStarAmount(int starAmount)
   {
      this.starAmount = starAmount;
   }

   public Settings getSettings()
   {
      return settings;
   }

   public ArrayList<LevelInformation> getLevels()
   {
      return levels;
   }

//	public void setLevels(ArrayList<LevelInformation> levels) {
//		for (int i = 0; i < levels.size(); i++)
//		{
//			this.levels.set(i, levels.get(i));
//		}
//	}

//	public void setSettings(Settings settings) {
//		this.settings = settings;
//	}

   public int getLastUnlockedLevelNo()
   {
      return lastUnlockedLevelNo;
   }

   public void resetLastUnlockedLevelNo()
   {
      this.lastUnlockedLevelNo = 1;
   }

   public void incrementLastUnlockedLevelNo()
   {
      lastUnlockedLevelNo++;
   }

   public void configureLastUnlockedLevelNo()
   {
      for ( int i = levels.size() - 1; i >= 0; i-- )
      {
         if ( levels.get(i).isUnlocked() )
         {
            lastUnlockedLevelNo = i + 1;
            break;
         }
      }
   }

   public int getRemainingShrinkPowerup()
   {
      return remainingShrinkPowerup;
   }

   public int getRemainingSpacePowerup()
   {
      return remainingSpacePowerup;
   }

   public void decrementRemainingShrinkPowerup()
   {
      remainingShrinkPowerup--;
   }

   public void decrementRemainingSpacePowerup()
   {
      remainingSpacePowerup--;
   }

   public void addShrinkPowerup(int amountToBeAdded)
   {
      remainingShrinkPowerup += amountToBeAdded;
   }

   public void addSpacePowerup(int amountToBeAdded)
   {
      remainingSpacePowerup += amountToBeAdded;
   }
}
