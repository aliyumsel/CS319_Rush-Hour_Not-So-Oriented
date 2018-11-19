package source.view;

import source.controller.GameEngine;
import source.controller.Input;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame
{
	public static GuiPanelManager instance;

	//private GameEngine gameEngine;

	private int currentPanelIndex;
	private GamePanel gamePanel;
	private MainMenuPanel mainMenuPanel;

	public  GuiPanelManager()
	{
		super("Rush Hour");
		instance = this;

		setLayout(new CardLayout());
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenuPanel = new MainMenuPanel(0, this);
		gamePanel = new GamePanel(1, this);
		add(mainMenuPanel);
		add(gamePanel);

		currentPanelIndex = 0;

		setListeners();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pack();

		mainMenuPanel.setVisible(true);
		gamePanel.setVisible(false);

      setVisible(true);
	}

	public JPanel getCurrentPanel()
	{
	   return (JPanel)getComponent(currentPanelIndex);
	}

	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public MainMenuPanel getMainMenuPanel()
   {
      return mainMenuPanel;
   }

	public void setPanelVisible(String panelName)
	{
	   if (panelName == "MainMenu")
      {
         mainMenuPanel.setVisible(true);
         setContentPane(mainMenuPanel);
      }
      else if (panelName == "Game")
      {
         gamePanel.setVisible(true);
         setContentPane(gamePanel);
      }
      else
      {
         return;
      }
	}

	public void updatePanels()
   {
      gamePanel.updatePanel();
   }

	private void setListeners()
	{
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		gamePanel.getInnerGamePanel().addMouseListener(mouseListener);
	}
}