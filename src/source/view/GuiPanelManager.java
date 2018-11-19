package source.view;

import source.controller.GameEngine;
import source.controller.Input;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame {

   public static GuiPanelManager instance;

	private int currentPanelIndex;
	private PlayGamePanel playGame;
	private MainMenuPanel mainMenu;

	public  GuiPanelManager()
	{
		super("Rush Hour");
		setLayout(new CardLayout());
		currentPanelIndex = 0;
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenu = new MainMenuPanel(0, this);
		playGame = new PlayGamePanel(1,1, this);
		add(mainMenu);
		add(playGame);

		pack();
		this.setVisible(true);
		setListeners();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		instance = this;
	}

	
	public JPanel getCurrentPanel()
	{
		return (JPanel)getComponent(currentPanelIndex);
	}

	public PlayGamePanel getGamePanel()
	{
		return playGame;
	}

	public void setPanelVisible(int level)
	{
	   System.out.println("SetPanelVisible: " + level);
      GameEngine.instance.gameManager.currentLevel = level;
      playGame.setGamePanelVisible(level);
		setContentPane(playGame);
		playGame.setVisible(true);
	}

	void setListeners()
	{
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		playGame.getGamePanel().addMouseListener(mouseListener);
	}
}