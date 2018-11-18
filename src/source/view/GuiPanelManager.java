package source.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import source.controller.Controller;
//import source.controller.Sound;
//import source.model.GameEngine;

@SuppressWarnings("serial")
public class GuiPanelManager extends JFrame {
	
	private JPanel currentPanel = null;
	private GamePanel newPanel;
	public  GuiPanelManager() {		
		super("Rush Hour");
		setGamePanelVisible(2);
		setSize(1000,700);
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void setGamePanelVisible(int level) {

		try {
			newPanel = new GamePanel(level);
			setContentPane(newPanel);
			currentPanel = newPanel;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setVisible(true);
	}
	
	public GamePanel getCurrentPanel() {
		return newPanel;
	}
	
}