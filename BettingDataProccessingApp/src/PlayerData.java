public class PlayerData {

    private String playerID;
    private String playerOperation;
    private String matchID;
    private int coinNumber;
    private String betSide;

    public PlayerData(String playerID, String playerOperation, String matchID, int coinNumber, String betSide) {
        this.playerID = playerID;
        this.playerOperation = playerOperation;
        this.matchID = matchID;
        this.coinNumber = coinNumber;
        this.betSide = betSide;
    }

//    public PlayerData(String playerID, String playerOperation, int coinNumber) {
//
//        this.playerID = playerID;
//        this.playerOperation = playerOperation;
//        this.coinNumber = coinNumber;
//    }


    public String getPlayerID() {
        return playerID;
    }

    public String getPlayerOperation() {
        return playerOperation;
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