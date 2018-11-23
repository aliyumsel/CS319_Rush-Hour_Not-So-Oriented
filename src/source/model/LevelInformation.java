package source.model;

public class LevelInformation {
	private int stars; 
	private String status; //notStarted, inProgress, finished
	int levelNo;
	int maxNumberOfMovesForThreeStars;
	int maxNumberOfMovesForTwoStars;
	int currentNumberOfMoves;
	
	public LevelInformation(int stars, String status, int levelNo, int maxNumberOfMovesForThreeStars, int maxNumberOfMovesForTwoStars, int currentNumberOfMoves)
	{
		this.levelNo = levelNo;
		this.maxNumberOfMovesForThreeStars = maxNumberOfMovesForThreeStars;
		this.maxNumberOfMovesForTwoStars = maxNumberOfMovesForTwoStars;
		this.currentNumberOfMoves = currentNumberOfMoves;
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

	public int getCurrentNumberOfMoves()
	{
		return currentNumberOfMoves;
	}
	

}
