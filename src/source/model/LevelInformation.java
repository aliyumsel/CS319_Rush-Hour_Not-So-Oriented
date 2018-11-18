package source.model;

public class LevelInformation {
	private int stars; 
	private String status; //notStarted, inProgress, finished
	
	public LevelInformation(int stars, String status)
	{
		this.stars = stars;
		this.status = status;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
