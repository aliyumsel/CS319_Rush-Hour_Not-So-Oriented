package source.view;

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

	private int currentPanelIndex;
	private PlayGamePanel playGame;
	private MainMenuPanel mainMenu;
	public  GuiPanelManager() {		
		super("Rush Hour");
		setLayout(new CardLayout());
		currentPanelIndex = 0;
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenu = new MainMenuPanel(0);
		playGame = new PlayGamePanel(1,2);
		add(mainMenu);
		add(playGame);

		pack();
		this.setVisible(true);
	}

	
	public JPanel getCurrentPanel() {
		return (JPanel)getComponent(currentPanelIndex);
	}

	public GamePanel getGamePanel() {
		return playGame.getGamePanel();
	}

	void setListeners()
	{
		KeyListener keyListener = Input.getKeyListener();
		addKeyListener(keyListener);
		playGame.setListeners();
	}
}