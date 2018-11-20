package source.controller;

import java.io.FileNotFoundException;

/**
 * Created by ASTOD on 11/19/2018.
 */
public class MapController
{
   public static MapController instance;

   private MapExtractor mapExtractor;
   private Map map;

   public boolean mapFinished;

   public MapController()
   {
      instance = this;
      mapExtractor = new MapExtractor();
   }

   public void loadLevel(int level)
   {
      try
      {
         map = mapExtractor.extractLevel(level);
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }

   public Map getMap()
   {
      return map;
   }
}
