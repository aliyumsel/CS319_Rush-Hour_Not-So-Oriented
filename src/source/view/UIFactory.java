package source.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class UIFactory
{
   private static Dimension longButtonDimension = new Dimension(171, 37);
   private static Dimension squareButtonDimension = new Dimension(49, 55); // evet kare degil biliyom ellemeyin
   private static Dimension playButtonDimension = new Dimension(131, 147);
   private static Dimension arrowButtonDimension = new Dimension(130, 150);
   private static Dimension levelButtonDimension = new Dimension(105, 120);
   private static Dimension playerButtonDimension = new Dimension(300, 120);
   private static Dimension miniStarDimension = new Dimension(26, 26);

   public static JButton createButton(BufferedImage normalImage, BufferedImage highlightedImage, String buttonType,
                                      ActionListener actionListener)
   {
      JButton _button = new JButton();
      setupButton(_button,normalImage,highlightedImage,buttonType,actionListener);
      return _button;
   }

   public static JLabel createLabelIcon(BufferedImage image, String labelType)
   {
      JLabel _label = new JLabel();
      setupLabelIcon(_label,image,labelType);
      return _label;
   }

   static void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage, String buttonType,
                    ActionListener actionListener)
   {
      button.addActionListener(actionListener);
      if ( buttonType.equals("long") )
      {
         button.setPreferredSize(longButtonDimension);
      }
      else if ( buttonType.equals("square") )
      {
         button.setPreferredSize(squareButtonDimension);
      }
      else if ( buttonType.equals("play") )
      {
         button.setPreferredSize(playButtonDimension);
      }
      else if ( buttonType.equals("arrow") )
      {
         button.setPreferredSize(arrowButtonDimension);
      }
      else if ( buttonType.equals("level") )
      {
         button.setPreferredSize(levelButtonDimension);
      }
      else if ( buttonType.equals("player") )
      {
         button.setPreferredSize(playerButtonDimension);
      }
      else if ( buttonType.equals("miniStar") )
      {
         button.setPreferredSize(miniStarDimension);
      }
      else
      {
         System.out.println("Error: Enter valid String");
      }

      button.setIcon(new ImageIcon(normalImage));
      button.setRolloverIcon(new ImageIcon(highlightedImage));
      button.setPressedIcon(new ImageIcon(highlightedImage));
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setFocusable(false);
   }

   static void setupLabelIcon(JLabel label, BufferedImage image, String labelType)
   {
      if ( labelType.equals("long") )
      {
         label.setPreferredSize(longButtonDimension);
      }
      else if ( labelType.equals("square") )
      {
         label.setPreferredSize(squareButtonDimension);
      }
      else if ( labelType.equals("play") )
      {
         label.setPreferredSize(playButtonDimension);
      }
      else if ( labelType.equals("arrow") )
      {
         label.setPreferredSize(arrowButtonDimension);
      }
      else if ( labelType.equals("level") )
      {
         label.setPreferredSize(levelButtonDimension);
      }
      else if ( labelType.equals("player") )
      {
         label.setPreferredSize(playerButtonDimension);
      }
      else if ( labelType.equals("miniStar") )
      {
         label.setPreferredSize(miniStarDimension);
      }
      else
      {
         System.out.println("Error: Enter valid String For Label");
      }

      label.setIcon(new ImageIcon(image));
      label.setOpaque(false);
      label.setFocusable(false);
   }
}
