package source.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import source.controller.GameEngine;
import source.controller.Input;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame {
	
	private JPanel currentPanel = null; //gonna be used later
	private GamePanel newPanel;
	
	public  GuiPanelManager()
	{
		super("Rush Hour");
		setGamePanelVisible(2);
		setSize(500,500);
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void setGamePanelVisible(int level)
	{
		newPanel = new GamePanel(level);
		setContentPane(newPanel);
		currentPanel = newPanel;

		this.setVisible(true);

//		try {
//
//			newPanel = new GamePanel(level);
//			setContentPane(newPanel);
//			currentPanel = newPanel;
//
//			gameEngine = new GameEngine(newPanel, this);
//			System.out.println("1");
//			Timer timer = new Timer();
//			timer.schedule(gameEngine, 0, 1000 / 60);
//			System.out.println("2");
//
//		} catch (FileNotFoundException e)
//		{
//			 TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		this.setVisible(true);
	}
	
	public GamePanel getCurrentPanel()
	{
		return newPanel;
	}

	void setListeners()
	{
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		getCurrentPanel().addMouseListener(mouseListener);
	}
}