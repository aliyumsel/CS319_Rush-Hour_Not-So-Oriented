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
public class InnerGamePanel extends JPanel
{

	private ArrayList<Vehicle> vehicleArray;
	public Map map;

	public InnerGamePanel() throws FileNotFoundException
   {
		setPreferredSize(new Dimension(450,450));
	}

	public void updatePanel()
	{
	   if (!isShowing())
      {
         return;
      }
      map = GameEngine.instance.mapController.getMap();
      vehicleArray = map.getVehicleArray();
      repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);

		if (vehicleArray == null)
      {
         return;
      }

		for (Vehicle vehicle : vehicleArray) {
			vehicle.draw(g);
		}
	}
}