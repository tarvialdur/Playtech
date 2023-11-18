
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        List<MatchData> matchDataList = new ArrayList<>();
        List<PlayerData> playerDataList = new ArrayList<>();
        Set<String> playerIds = new TreeSet<>();
        List<PlayerData> illegalPlayers = new ArrayList<>();
        List<LegalPlayer> legalPlayers = new ArrayList<>();

        // Loen faili ja lisan info listi...

        BufferedReader pdf = new BufferedReader(new FileReader("player_data.txt"));  // pdf - PlayerDataFile
        String pdfLine = pdf.readLine();

        while (pdfLine != null) {
            String[] spot = pdfLine.split(",");
            String playerID = spot[0];
            String playerOperation = spot[1];
            String matchID = spot[2];
            int coinNumber = Integer.parseInt(spot[3]);
            String betSide = spot[2].isEmpty() ? "" : spot[4]; // if spot[2] in the line is empty, then spot[4] will be also marked as empty.

            PlayerData playerData = new PlayerData(playerID, playerOperation, matchID, coinNumber, betSide);
            playerDataList.add(playerData);
            playerIds.add(playerID);
            pdfLine = pdf.readLine();
        }
        pdf.close();

        // Loen faili ja lisan info listi...

        BufferedReader mdf = new BufferedReader(new FileReader("match_data.txt"));    // mdf - Match Data File
        String mdfLine = mdf.readLine();

        while (mdfLine != null) {
            String[] spot = mdfLine.split(",");
            String matchID = spot[0];
            double returnRateA = Double.parseDouble(spot[1]);
            double returnRateB = Double.parseDouble(spot[2]);
            String matchResult = spot[3];

            MatchData matchData = new MatchData(matchID, returnRateA, returnRateB, matchResult);
            matchDataList.add(matchData);
            mdfLine = mdf.readLine();
        }
        mdf.close();

        int casinoBalance = 0;

        for (String id : playerIds) {
            try {
                List<PlayerData> playerActions = playerDataList.stream()
                        .filter(e -> e.getPlayerID().equals(id))
                        .toList();

                int coinBalance = 0;
                int playerResult = 0;
                int wonGames = 0;
                int allGames = 0;


                for (PlayerData action : playerActions) {
                    switch (action.getPlayerOperation()) {
                        case "DEPOSIT" -> coinBalance += action.getCoinNumber();
                        case "WITHDRAW" -> {
                            coinBalance -= action.getCoinNumber();
                            if (coinBalance < 0) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Trying to withdraw more than current balance");
                            }
                        }
                        case "BET" -> {
                            if (action.getCoinNumber() > coinBalance) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Bet too high! Coin balance: " + coinBalance);
                            }

                            MatchData matchData;
                            try {
                                matchData = matchDataList.stream()
                                        .filter(e -> e.getMatchID().equals(action.getMatchID()))
                                        .findFirst()
                                        .get();

                            } catch (Exception e) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Match ID not found");
                            }
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
                        default -> throw new RuntimeException("An error occured");
                    }
                }
                coinBalance += playerResult;
                casinoBalance += playerResult;
                legalPlayers.add(new LegalPlayer(id, coinBalance, wonGames / allGames));

            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }


        // OUTPUT FILE

        legalPlayers.sort(Comparator.comparing(LegalPlayer::getLegalPlayerID));

        PrintWriter printWriter = new PrintWriter(new FileWriter("result.txt"));
        for (LegalPlayer player : legalPlayers) {
            printWriter.print(player.getLegalPlayerID());
            printWriter.print(" ");
            printWriter.print(player.getFinalBalance());
            printWriter.print(" ");
            printWriter.print(player.getWinRate());
            printWriter.println();
        }

        for (PlayerData l : illegalPlayers) {
            printWriter.print(l.getPlayerID());
            printWriter.print(" ");
            printWriter.print(l.getPlayerOperation());
            printWriter.print(" ");
            printWriter.print(l.getMatchID());
            printWriter.print(" ");
            printWriter.print(l.getCoinNumber());
            printWriter.print(" ");
            printWriter.print(l.getBetSide());
            printWriter.println(" ");
        }
        printWriter.println();
        printWriter.print(casinoBalance);

        printWriter.close();
    }
}





