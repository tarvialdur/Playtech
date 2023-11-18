
import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        try {
            FileHandler fileHandler = new FileHandler("error.log");
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<MatchData> matchDataList = new ArrayList<>();
        List<PlayerData> playerDataList = new ArrayList<>();
        List<PlayerData> illegalPlayers = new ArrayList<>();
        List<LegalPlayer> legalPlayers = new ArrayList<>();
        Set<String> playerIds = new TreeSet<>();


        // pdf - Player Data File

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

        // mdf - Match Data File

        BufferedReader mdf = new BufferedReader(new FileReader("match_data.txt"));
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

        double casinoBalance = 0;

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
                                //logger.severe("Match ID not found");
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
                                    //logger.severe("Invalid return rate");
                                    throw new RuntimeException("Invalid return rate");
                                }
                            } else if (!matchData.getMatchResult().equals("DRAW") &&
                                    !matchData.getMatchResult().equals(action.getBetSide())) {
                                playerResult -= action.getCoinNumber();
                            }
                        }
                        default -> logger.severe("Wrong type of operation");
                        //throw new RuntimeException("An error occured");
                    }
                }
                coinBalance += playerResult;
                casinoBalance -= playerResult;
                legalPlayers.add(new LegalPlayer(id, coinBalance, wonGames / allGames));

            } catch (RuntimeException e) {
                logger.severe("Error: " + e.getMessage());
                //System.out.println("Error: " + e.getMessage());
            }
        }


        PrintWriter printWriter = new PrintWriter(new FileWriter("result.txt"));
        legalPlayers.sort(Comparator.comparing(LegalPlayer::getLegalPlayerID));

        for (LegalPlayer player : legalPlayers) {
            printWriter.print(player.getLegalPlayerID());
            printWriter.print(" ");
            printWriter.print((int) player.getFinalBalance());
            printWriter.print(" ");
            printWriter.printf("%.2f", player.getWinRate());
            printWriter.println();

        }
        printWriter.println();
        for (PlayerData l : illegalPlayers) {
            printWriter.print(l.getPlayerID());
            printWriter.print(" ");
            printWriter.print(l.getPlayerOperation());
            printWriter.print(" ");
            printWriter.print(l.getMatchID().isEmpty() ? null : "");
            printWriter.print(" ");
            printWriter.print(l.getCoinNumber());
            printWriter.print(" ");
            printWriter.print(l.getBetSide().isEmpty() ? null : "");
            printWriter.println(" ");
        }
        printWriter.println();
        printWriter.print((int) casinoBalance);

        printWriter.close();
    }
}





