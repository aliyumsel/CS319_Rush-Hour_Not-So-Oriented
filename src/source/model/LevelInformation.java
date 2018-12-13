package source.model;

public class LevelInformation
{
   private int stars;
   private String status; //notStarted, inProgress, finished
   private int levelNo;
   private int maxNumberOfMovesForThreeStars;
   private int maxNumberOfMovesForTwoStars;
   private int currentNumberOfMoves;
   private boolean unlocked;
   private String map;

   public LevelInformation(int stars, String status, int levelNo, int maxNumberOfMovesForThreeStars, int maxNumberOfMovesForTwoStars, int currentNumberOfMoves, boolean unlocked, String map)
   {
      this.levelNo = levelNo;
      this.maxNumberOfMovesForThreeStars = maxNumberOfMovesForThreeStars;
      this.maxNumberOfMovesForTwoStars = maxNumberOfMovesForTwoStars;
      this.currentNumberOfMoves = currentNumberOfMoves;
      this.stars = stars;
      this.status = status;
      this.unlocked = unlocked;
      this.map = map;
   }

   public int getStars()
   {
      return stars;
   }

   public void setStars(int stars)
   {
      this.stars = stars;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }

   public int getCurrentNumberOfMoves()
   {
      return currentNumberOfMoves;
   }

   public void setCurrentNumberOfMoves(int currentNumberOfMoves)
   {
      this.currentNumberOfMoves = currentNumberOfMoves;
   }

   public boolean isUnlocked()
   {
      return unlocked;
   }

   public void unlock()
   {
      this.unlocked = true;
   }

   public int getMaxNumberOfMovesForThreeStars()
   {
      return maxNumberOfMovesForThreeStars;
   }

   public int getMaxNumberOfMovesForTwoStars()
   {
      return maxNumberOfMovesForTwoStars;
   }

   public String getMap()
   {
      return map;
   }

   public void setMap(String map)
   {
      this.map = map;
   }

   public String levelToString()
   {
      return "\t<Level>\n" +
              "\t\t<LevelNo>\n" +
              "\t\t\t" + levelNo + "\n" +
              "\t\t<LevelNo/>\n" +
              "\t\t<Stars>\n" +
              "\t\t\t" + stars + "\n" +
              "\t\t<Stars/>\n" +
              "\t\t<CurrentNumberOfMoves>\n" +
              "\t\t\t" + currentNumberOfMoves + "\n" +
              "\t\t<CurrentNumberOfMoves>\n" +
              "\t\t<Status>\n" +
              "\t\t\t" + status + "\n" +
              "\t\t<Status/>\n" +
              "\t\t<Unlocked>\n" +
              "\t\t\t" + unlocked + "\n" +
              "\t\t<Unlocked/>\n" +
              "\t\t<Map>\n" +
              map +
              "\t\t<Map/>\n" +
              "\t<Level/>\n";
   }
}
