import java.math.BigDecimal;
public class LegalPlayer {

    private String legalPlayerID;
    private double finalBalance;
    private BigDecimal winRate;

    public LegalPlayer(String legalPlayerID, double finalBalance, double winRate) {
        this.legalPlayerID = legalPlayerID;
        this.finalBalance = finalBalance;
        this.winRate = BigDecimal.valueOf(winRate);
    }

    public String getLegalPlayerID() {
        return legalPlayerID;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public BigDecimal getWinRate() {
        return winRate;
    }
}