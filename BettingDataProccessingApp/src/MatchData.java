public class MatchData {
    private String matchID;
    private double returnRateA;
    private double returnRateB;
    private String matchResult;

    public MatchData(String matchID, double returnRateA, double returnRateB, String matchResult) {
        this.matchID = matchID;
        this.returnRateA = returnRateA;
        this.returnRateB = returnRateB;
        this.matchResult = matchResult;
    }

    public String getMatchID() {
        return matchID;
    }

    public double getReturnRateA() {
        return returnRateA;
    }

    public double getReturnRateB() {
        return returnRateB;
    }

    public String getMatchResult() {
        return matchResult;
    }
}
