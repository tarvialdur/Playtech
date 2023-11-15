public class Match {
    private String matchID;
    private String result;
    private double aRating;
    private double bRating;

    public Match(String matchID, String result, double aRating, double bRating) {
        this.matchID = matchID;
        this.result = result;
        this.aRating = aRating;
        this.bRating = bRating;
    }

    public String getMatchID() {
        return matchID;
    }

    public String getResult() {
        return result;
    }

    public double getaRating() {
        return aRating;
    }

    public double getbRating() {
        return bRating;
    }
}