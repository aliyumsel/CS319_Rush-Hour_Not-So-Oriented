package source.view;

import source.controller.Input;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame
{
   public static GuiPanelManager instance;

//   private int currentPanelIndex;
   private ArrayList<JPanel> panels;
   private GamePanel gamePanel;
   private MainMenuPanel mainMenuPanel;
   private CreditsPanel creditsPanel;
   private SettingsPanel settingsPanel;
   private LevelSelectionPanel levelSelectionPanel;
   private HelpPanel helpPanel;
   private ChangePlayerPanel changePlayerPanel;
   private JPanel targetPanel;

   int panelWidth;
   int panelHeight;

//   private BufferedImage cursorImage;

   private Font odinRounded;

   public GuiPanelManager()
   {
      super("Rush Hour");
      setUndecorated(true);
      instance = this;

      panels = new ArrayList<>();

      // setShape(new RoundRectangle2D.Double(0, 0, 764, 492, 51, 51));

      // Toolkit toolkit = Toolkit.getDefaultToolkit();
      // cursorImage = LoadImage("src/image/icons/cursor1.png");
      // Cursor cursor = toolkit.createCustomCursor(cursorImage, new
      // Point(getX(),getY()),"custom");
      Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
      setCursor(cursor);

      File fontFile = new File("src/fonts/odin.ttf");
      try
      {
         odinRounded = Font.createFont(Font.TRUETYPE_FONT, fontFile);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(odinRounded);
      } catch (FontFormatException | IOException e)
      {
         e.printStackTrace();
      }

      panelWidth = 764;
      panelHeight = 468;

      setLayout(new CardLayout());
      setResizable(false);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      addPanels();
      add(new JLabel()); // do not delete this very IMPORTANT!
//      currentPanelIndex = 0;

      setListeners();
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      pack();
      setLocationRelativeTo(null);

      setPanelVisible("MainMenu");

      setVisible(true);
      pack();

   }

   public void addPanels()
   {
      mainMenuPanel = new MainMenuPanel(this);
      gamePanel = new GamePanel(this);
      creditsPanel = new CreditsPanel(this);
      settingsPanel = new SettingsPanel(this);
      helpPanel = new HelpPanel(this);
      levelSelectionPanel = new LevelSelectionPanel(this);
      changePlayerPanel = new ChangePlayerPanel(this);
      add(mainMenuPanel);
      add(gamePanel);
      add(creditsPanel);
      add(levelSelectionPanel);
      add(settingsPanel);
      add(helpPanel);
      add(changePlayerPanel);
      panels.add(mainMenuPanel);
      panels.add(gamePanel);
      panels.add(creditsPanel);
      panels.add(levelSelectionPanel);
      panels.add(settingsPanel);
      panels.add(helpPanel);
      panels.add(changePlayerPanel);
   }

   public GamePanel getGamePanel()
   {
      return gamePanel;
   }

   void setPanelVisible(String panelName)
   {
      for ( JPanel panel: panels )
      {
         panel.setVisible(false);
      }

      if ( panelName.equals("MainMenu") )
      {
    	   mainMenuPanel.updatePlayerName();
         targetPanel = mainMenuPanel;
      }
      else if ( panelName.equals("Game") )
      {
         targetPanel = gamePanel;
      }
      else if ( panelName.equals("Credits") )
      {
         targetPanel = creditsPanel;
      }
      else if ( panelName.equals("LevelSelection") )
      {
    	   levelSelectionPanel.updatePanel();
         targetPanel = levelSelectionPanel;
      }
      else if ( panelName.equals("Settings") )
      {
         targetPanel = settingsPanel;
      }
      else if ( panelName.equals("Help") )
      {
         targetPanel = helpPanel;
      }
      else if ( panelName.equals("ChangePlayer") )
      {
         changePlayerPanel.reset();
         targetPanel = changePlayerPanel;
      }
      else
      {
         System.out.println("Error: Enter valid name");
      }
      targetPanel.setVisible(true);
      setContentPane(targetPanel);
   }

   void updatePanels()
   {
      gamePanel.updatePanel(); // look into updating other panels
   }

   private void setListeners()
   {
      KeyListener keyListener = Input.getKeyListener();
      MouseListener mouseListener = Input.getMouseListener();
      addKeyListener(keyListener);
      gamePanel.getInnerGamePanel().addMouseListener(mouseListener);
   }

   BufferedImage LoadImage(String FileName)
   {
      BufferedImage image = null;
      try
      {
         image = ImageIO.read(new File(FileName));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return image;
   }

   int findCenter(int _panelWidth, Component _component)
   {
      return ( _panelWidth - _component.getPreferredSize().width ) / 2;
   }

}