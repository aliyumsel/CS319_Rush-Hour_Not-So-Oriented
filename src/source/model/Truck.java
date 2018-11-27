package source.model;

public class Truck extends Vehicle
{
   public Truck(int x, int y, String direction, boolean player, boolean special, String theme)
   {
      super(x, y, 3, direction, player, special, theme);
      if ( player )
      {
         super.setType("Player");
      }
      else
      {
         super.setType("Truck");
      }
   }
}
