package source.view;

import source.controller.Input;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings({"serial", "Duplicates"})
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
   private TutorialPanel tutorialPanel;
   private ChangePlayerPanel changePlayerPanel;
   private JPanel targetPanel;
   //private BufferedImage cursorImage;
   int panelWidth;
   int panelHeight;

//   private BufferedImage cursorImage;

   public GuiPanelManager()
   {
      super("Rush Hour");
      setUndecorated(true);
      instance = this;
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      //Image image = toolkit.getImage("src/image/icons/cursor.png");
      Image image = null;
      try {
         image = ImageIO.read(GuiPanelManager.class.getClassLoader().getResourceAsStream("image/icons/cursor.png"));
      } catch (IOException e) {
         e.printStackTrace();
      }
      Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
      this.setCursor(c);

      panels = new ArrayList<>();

      //File fontFile = new File("src/fonts/odin.ttf");
      InputStream input = GuiPanelManager.class.getClassLoader().getResourceAsStream("fonts/odin.ttf");
      try
      {
         Font odinRounded = Font.createFont(Font.TRUETYPE_FONT, input);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(odinRounded);
      } catch (FontFormatException | IOException e)
      {
         e.printStackTrace();
      }

      panelWidth = 800; //764
      panelHeight = 520; //468

      setLayout(new CardLayout());
      setResizable(false);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      addPanels();
      add(new JLabel()); // do not delete this very IMPORTANT!

      setListeners();
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      pack();
      setLocationRelativeTo(null);

      //gamepanel managerdan çekilen bilgiye göre ya tutorial ya da main menu açılacak
      setPanelVisible("Tutorial");
      //setPanelVisible("MainMenu");

      setVisible(true);
      pack();

   }

   private void addPanels()
   {
      mainMenuPanel = new MainMenuPanel(this);
      tutorialPanel = new TutorialPanel(true,this);
      gamePanel = new GamePanel(this);
      creditsPanel = new CreditsPanel(this);
      settingsPanel = new SettingsPanel(this);
      helpPanel = new HelpPanel(this);
      levelSelectionPanel = new LevelSelectionPanel(this);
      changePlayerPanel = new ChangePlayerPanel(this);
      this.add(mainMenuPanel);
      this.add(tutorialPanel);
      this.add(gamePanel);
      this.add(creditsPanel);
      this.add(levelSelectionPanel);
      this.add(settingsPanel);
      this.add(helpPanel);
      this.add(changePlayerPanel);

      panels.add(mainMenuPanel);
      panels.add(tutorialPanel);
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
      if ( panelName.equals("MainMenu") )
      {
         mainMenuPanel.updatePanel();
         targetPanel = mainMenuPanel;
      }
      else if (panelName.equals("Tutorial")){
         tutorialPanel.update();
         targetPanel = tutorialPanel;
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
         if ( mainMenuPanel.isVisible() )
         {
            settingsPanel.updatePanel("MainMenu");
         }
         else if ( gamePanel.isVisible() )
         {
            settingsPanel.updatePanel("Game");
         }

      }
      else if ( panelName.equals("Help") )
      {
         targetPanel = helpPanel;
      }
      else if ( panelName.equals("ChangePlayer") )
      {
         changePlayerPanel.updatePanel();
         targetPanel = changePlayerPanel;
      }
      else
      {
         System.out.println("Error: Enter valid name");
      }
      for ( JPanel panel : panels )
      {
         panel.setVisible(false);
      }
      targetPanel.setVisible(true);
      setContentPane(targetPanel);
   }

   void updatePanels()
   {
      gamePanel.updatePanel(); // look into updating other panels
   }

   void updateImages()
   {
      settingsPanel.loadImages();
      changePlayerPanel.loadImages();
      helpPanel.loadImages();
      levelSelectionPanel.loadImages();
      creditsPanel.loadImages();
      gamePanel.loadImages();
      gamePanel.getEndOfLevelPanel().loadImages();
      mainMenuPanel.loadImages();
   }

   private void setListeners()
   {
      KeyListener keyListener = Input.getKeyListener();
      MouseListener mouseListener = Input.getMouseListener();
      addKeyListener(keyListener);
      gamePanel.getInnerGamePanel().addMouseListener(mouseListener);
      Input.setGamePanel(gamePanel.getInnerGamePanel());
   }

   public BufferedImage LoadImage(String fileName)
   {
      //fileName = fileName.substring(fileName.indexOf('/') + 1);
      BufferedImage image = null;
      try
      {
         image = ImageIO.read(GuiPanelManager.class.getClassLoader().getResourceAsStream(fileName));
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      return image;
   }


   int findCenter(int _panelWidth, Component _component)
   {
      return ( _panelWidth - _component.getPreferredSize().width ) / 2;
   }

   int findCenterVertical(int _panelHeight, Component _component)
   {
      return ( _panelHeight - _component.getPreferredSize().height ) / 2;
   }

}