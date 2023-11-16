import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {



        Set<String> playerIds = new TreeSet<>();
        List<LegalPlayer> legalPlayers = new ArrayList<>();
        List<IllegalPlayer> illegalPlayerData = new ArrayList<>();



        // Loen faili ja lisan info listi...

        List<PlayerData> playerDataList = new ArrayList<>();

        BufferedReader pdf = new BufferedReader(new FileReader("player_data.txt"));  // pdf - PlayerDataFile
        String pdfLine = pdf.readLine();

        while(pdfLine != null){
            String[] spot = pdfLine.split(",");
            String playerID = spot[0];
            String playerOperation = spot[1];
            String matchID = spot[2];
            int coinNumber = Integer.parseInt(spot[3]);
            String betSide = spot[2].isEmpty() ? "" : spot[4]; // if spot[2] in the line is empty, then spot[4] will be also marked as empty. Ternary conditional operator

            PlayerData playerData = new PlayerData(playerID, playerOperation, matchID, coinNumber, betSide);
            playerDataList.add(playerData);
            pdfLine = pdf.readLine();
        }
        pdf.close();


// Loen faili ja lisan info listi...

        List<MatchData> matchDataList = new ArrayList<>();

        BufferedReader mdf = new BufferedReader(new FileReader("match_data.txt"));    // mdf - Match Data File
        String mdfLine = mdf.readLine();

        while(mdfLine != null){
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

        for(PlayerData player: playerDataList){
            player.getPlayerID();
            legalPlayers.add(new LegalPlayer(player.getPlayerID(), 4000, 69));
        }





        PrintWriter printWriter = new PrintWriter(new FileWriter("result.txt"));
        for(LegalPlayer player : legalPlayers){
            printWriter.print(player.getLegalPlayerID());
            printWriter.println();
        }
        printWriter.println();
        printWriter.print("New line");
        printWriter.close();


    }
}





