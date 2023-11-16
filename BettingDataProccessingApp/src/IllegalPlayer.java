public class IllegalPlayer {

    private String illegalPlayerID;
    private String illegalPlayerOperation;
    private String matchID;
    private int coinNumber;
    private String betSide;

    public IllegalPlayer(String illegalPlayerID, String illegalPlayerOperation, String matchID, int coinNumber, String betSide) {
        this.illegalPlayerID = illegalPlayerID;
        this.illegalPlayerOperation = illegalPlayerOperation;
        this.matchID = matchID;
        this.coinNumber = coinNumber;
        this.betSide = betSide;
    }

    public String getIllegalPlayerID() {
        return illegalPlayerID;
    }

    public String getIllegalPlayerOperation() {
        return illegalPlayerOperation;
    }

    public String getMatchID() {
        return matchID;
    }

    public int getCoinNumber() {
        return coinNumber;
    }

    public String getBetSide() {
        return betSide;
    }
}
