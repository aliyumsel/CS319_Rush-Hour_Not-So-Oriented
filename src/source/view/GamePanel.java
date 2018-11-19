package source.view;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import source.controller.GameEngine;
import source.model.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import interfaces.*;
import source.controller.Input;

import source.controller.Map;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private GameEngine gameEngine;
	private ArrayList<Vehicle> vehicleArray;
	public Map map;

	public GamePanel() throws FileNotFoundException {
		setPreferredSize(new Dimension(450,450));
		updatePanel();
	}

	public void updatePanel() {
		gameEngine = GameEngine.instance;
		map = gameEngine.getMap();
		vehicleArray = map.getVehicleArray();
		//repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		for (Vehicle vehicle : vehicleArray) {
			vehicle.draw(g);
		}
	}
	void setListeners()
	{
		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
	}
}