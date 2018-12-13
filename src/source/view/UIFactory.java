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
   private static Dimension movesCarDimension = new Dimension(121, 41);
   private static Dimension starAmountDimension = new Dimension(101, 37);


   static LevelButton createLevelButton(ActionListener actionListener)
   {
      LevelButton _button = new LevelButton(GuiPanelManager.instance);
      _button.addActionListener(actionListener);
      return _button;
   }

   static JButton createPlayerButton(BufferedImage normalImage, BufferedImage highlightedImage, String playerName,
                                     ActionListener actionListener)
   {
      JButton _button = createButton(normalImage, highlightedImage, "player", actionListener);
      _button.setText(playerName);
      _button.setVerticalTextPosition(SwingConstants.CENTER);
      _button.setHorizontalTextPosition(SwingConstants.CENTER);
      _button.setFont(new Font("Odin Rounded", Font.PLAIN, 25));
      _button.revalidate();
      return _button;
   }

   static JButton createButton(BufferedImage normalImage, BufferedImage highlightedImage, String buttonType,
                               ActionListener actionListener)
   {
      JButton _button = new JButton();
      setupButton(_button, normalImage, highlightedImage, buttonType, actionListener);
      return _button;
   }

   static JLabel createLabelIcon(BufferedImage image, String labelType)
   {
      JLabel _label = new JLabel();
      setupLabelIcon(_label, image, labelType);
      return _label;
   }

   private static void setupButton(JButton button, BufferedImage normalImage, BufferedImage highlightedImage, String buttonType,
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
         System.out.println("Error: Enter valid String for button dimension");
      }

      button.setIcon(new ImageIcon(normalImage));
      button.setRolloverIcon(new ImageIcon(highlightedImage));
      button.setPressedIcon(new ImageIcon(highlightedImage));
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setFocusable(false);
   }

   static void disableButton(JButton button, BufferedImage highlightedImage)
   {
      button.setIcon(new ImageIcon(highlightedImage));
      button.setEnabled(false);
   }

   static void enableButton(JButton button, BufferedImage normalImage)
   {
      button.setIcon(new ImageIcon(normalImage));
      button.setEnabled(true);
   }

   private static void setupLabelIcon(JLabel label, BufferedImage image, String labelType)
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
      else if ( labelType.equals("movesCar") )
      {
         label.setPreferredSize(movesCarDimension);
      }
      else if ( labelType.equals("starAmount") )
      {
         label.setPreferredSize(starAmountDimension);
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
