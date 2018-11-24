package source.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import source.model.Map;
import source.model.Player;
import source.model.Vehicle;

/**
 * Created by ASTOD on 11/19/2018.
 */
public class MapController {
	public static MapController instance;

	private MapExtractor mapExtractor;
	private MapSaver mapSaver;
	private Map map;

	public boolean mapFinished;

	public MapController() {
		instance = this;
		mapExtractor = new MapExtractor();
		mapSaver = new MapSaver();
		// map = new Map();
	}

	public void loadLevel(int level) {
		try {
			// For testing
			//pm.deletePlayer("Ahmet");
			//pm.createPlayer("Ahmet");
			Player currentPlayer = PlayerManager.instance.getCurrentPlayer();
			System.out.println(currentPlayer.getPlayerName() + " " + level);
			System.out.println(currentPlayer.getLevels().get(level -1).getStatus());
			System.out.println(currentPlayer.getPath());
			map = mapExtractor.extractLevel(level, currentPlayer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (map != null) {
			System.out.println("Map Loaded");
			// map.printMap();
		}
	}
	
	public void loadOriginalLevel(int level)
	{
		try {
			map = mapExtractor.extractLevel(level, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Map getMap() {
		return map;
	}

	public void updateMap(ArrayList<Vehicle> vehicleArray) {
		map.formMap(vehicleArray);
	}

	public Vehicle getVehicleBySelectedCell(int x, int y) {
		int[] occupiedCells;
		int cellNumber = (map.getMapSize() * y) + x;
		for (Vehicle vehicle : map.getVehicleArray()) {
			occupiedCells = vehicle.getOccupiedCells();
			for (int i = 0; i < occupiedCells.length; i++) {
				if (cellNumber == occupiedCells[i]) {
					return vehicle;
				}
			}
		}
		return null;
	}

	// This method checks if the player is at the last cell he can go
	// One more move will make him get out of the grid and finish the game
	public boolean isPlayerAtExit() {
		Vehicle player = null;

		for (Vehicle v : map.getVehicleArray()) {
			if (v.isPlayer()) {
				player = v;
				break;
			}
		}
		if (player == null) {
			return false;
		}
		if (player.transform.position.x + player.transform.length == map.getMapSize()) {
			return true;
		}
		return false;
	}
	
	public void autosave(ArrayList<Vehicle> vehicleList)
	{
		mapSaver.saveMap(vehicleList, map.getMapSize(), GameManager.instance.getLevel(), PlayerManager.instance.getCurrentPlayer());
	}

}
