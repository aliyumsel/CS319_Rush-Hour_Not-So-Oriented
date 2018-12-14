package source.model;

public class BonusLevelInformation extends LevelInformation {

    private int time;
    public BonusLevelInformation(int stars, String status, int levelNo, int maxNumberOfMovesForThreeStars, int maxNumberOfMovesForTwoStars, int currentNumberOfMoves, boolean unlocked, String map, int time)
    {
        super(stars, status, levelNo, maxNumberOfMovesForThreeStars, maxNumberOfMovesForTwoStars, currentNumberOfMoves, unlocked, map);
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
