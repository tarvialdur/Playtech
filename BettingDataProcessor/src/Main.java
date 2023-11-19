import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {


        List<MatchData> matchDataList = MatchDataReader.readMatchData("match_data.txt");
        List<PlayerData> playerDataList = PlayerDataReader.readPlayerData("player_data.txt");
        List<PlayerData> illegalPlayers = new ArrayList<>();
        List<LegalPlayer> legalPlayers = new ArrayList<>();
        Set<String> playerIds = playerDataList.stream().map(PlayerData::getPlayerID).collect(Collectors.toSet());

        int casinoBalance = 0;

        for (String id : playerIds) {
            try {
                List<PlayerData> playerActions = playerDataList.stream()
                        .filter(e -> e.getPlayerID().equals(id))
                        .toList();

                double coinBalance = 0;
                double playerResult = 0;
                double wonGames = 0;
                double allGames = 0;

                for (PlayerData action : playerActions) {
                    switch (action.getPlayerOperation()) {
                        case "DEPOSIT" -> coinBalance += action.getCoinNumber();
                        case "WITHDRAW" -> {
                            coinBalance -= action.getCoinNumber();
                            if (coinBalance < 0) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Trying to withdraw more than the current balance");
                            }
                        }
                        case "BET" -> {
                            if (action.getCoinNumber() > coinBalance) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Bet too high! Coin balance: " + coinBalance);
                            }

                            MatchData matchData = ProcessData.findMatchData(matchDataList, action.getMatchID());

                            allGames++;

                            if (matchData.getMatchResult().equals(action.getBetSide())) {
                                wonGames++;
                                if (matchData.getMatchResult().equals("A")) {
                                    playerResult += action.getCoinNumber() * matchData.getReturnRateA();
                                } else if (matchData.getMatchResult().equals("B")) {
                                    playerResult += action.getCoinNumber() * matchData.getReturnRateB();
                                } else {
                                    throw new RuntimeException("Invalid return rate");
                                }
                            } else if (!matchData.getMatchResult().equals("DRAW") &&
                                    !matchData.getMatchResult().equals(action.getBetSide())) {
                                playerResult -= action.getCoinNumber();
                            }
                        }
                        default -> throw new RuntimeException("An error occurred");
                    }
                }

                coinBalance += playerResult;
                casinoBalance -= playerResult;
                legalPlayers.add(new LegalPlayer(id, coinBalance, wonGames / allGames));

            } catch (RuntimeException e) {
                logger.severe("Error: " + e.getMessage());
            }
        }

        ResultWriter.writeResultToFile(legalPlayers, illegalPlayers, casinoBalance, "src/result.txt");
    }
}
