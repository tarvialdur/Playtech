public class Player {
    private String playerID;
    private String action;
    private String matchID;
    private double amount;
    private String side;

    public Player(String playerID, String action, String matchID, double amount, String side) {
        this.playerID = playerID;
        this.action = action;
        this.matchID = matchID;
        this.amount = amount;
        this.side = side;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getAction() {
        return action;
    }

    public String getMatchID() {
        return matchID;
    }

    public double getAmount() {
        return amount;
    }

    public String getSide() {
        return side;
    }
}