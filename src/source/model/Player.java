package source.model;

import java.util.ArrayList;

public class Player {
	
	private String playerName;
	private ArrayList<LevelInformation> levels;
	private int starAmount;
	private String path;
	
	public Player(String playerName, int starAmount, ArrayList<LevelInformation> levels, String path) {
		this.playerName = playerName;
		this.starAmount = starAmount;
		this.path = path;
		this.levels = new ArrayList<LevelInformation>();
		for (int i = 0; i < levels.size(); i++)
		{
			this.levels.add(levels.get(i));
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getStarAmount() {
		return starAmount;
	}

	public void setStarAmount(int starAmount) {
		this.starAmount = starAmount;
	}

	public ArrayList<LevelInformation> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<LevelInformation> levels) {
		for (int i = 0; i < levels.size(); i++)
		{
			this.levels.set(i, levels.get(i));
		}
	}
	
	
	

}
