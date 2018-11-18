package source.model;

public class Player {
	
	private String name;
	private LevelInformation[] levels;
	
	public Player(String name, LevelInformation[] levels) {
		this.name = name;
		for (int i = 0; i < levels.length; i++)
		{
			this.levels[i] = levels[i];
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LevelInformation[] getLevels() {
		return levels;
	}

	public void setLevels(LevelInformation[] levels) {
		for (int i = 0; i < levels.length; i++)
		{
			this.levels[i] = levels[i];
		}
	}
	
	
	

}
