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
	public static GuiPanelManager instance; // extremely simple singleton to access gameEngine with ease
	private GameEngine gameEngine;
	private int currentPanelIndex;
	private PlayGamePanel playGame;
	private MainMenuPanel mainMenu;

	public  GuiPanelManager()
	{
		
		super("Rush Hour");
		instance = this;
		setLayout(new CardLayout());
		currentPanelIndex = 0;
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenu = new MainMenuPanel(0, this);
		playGame = new PlayGamePanel(1, this);
		add(mainMenu);
		add(playGame);

		
		this.setVisible(true);
		setListeners();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pack();
		
	}

	
	public JPanel getCurrentPanel()
	{
		return (JPanel)getComponent(currentPanelIndex);
	}

	public PlayGamePanel getPlayGamePanel()
	{
		return playGame;
	}

	public void setPanelVisible()
	{
		playGame.setVisible(true); //panel de�i�irken false yap�lmal�
		setContentPane(playGame);
		
	}

	public void setMainMenuVisible()
	{
		playGame.setVisible(false);
		mainMenu.setVisible(true);
		setContentPane(mainMenu);
	}
	
	public void updatePlayGamePanel() {
		//content pane zaten playgame oldu�u i�in ve visible oldu�u i�in alttaki ikisine gerek yok ama ba�ka bir panelden ge�erken bunlarla oynamak laz�m
		playGame.updateGamePanel();
		setContentPane(playGame); //ama koymazsak paneldeki hata d�zelmiyo
		//playGame.setVisible(true);
	}

	void setListeners()
	{
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		playGame.getGamePanel().addMouseListener(mouseListener);
	}
}