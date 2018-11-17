package source.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import source.model.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.CORBA.PRIVATE_MEMBER;

import source.controller.Map;
import source.controller.MapExtractor;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private GuiPanelManager guiPanelManager;
	private MapExtractor mapExtractor;
	private ArrayList<Vehicle> vehicleArray;
	private Map map;

	public GamePanel(int level) throws FileNotFoundException {
		this.guiPanelManager = guiPanelManager;
		mapExtractor = new MapExtractor(level);
		map = mapExtractor.getMap();
		vehicleArray = map.getVehicleArray();

	}

	public void updatePanel(ArrayList<Vehicle> vehicleArray) {
		this.vehicleArray = vehicleArray;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		for (Vehicle vehicle : vehicleArray) {
			vehicle.draw(g);
		}
		System.out.println("\n");
		try {

		} catch (Exception e) {
		}
	}
}