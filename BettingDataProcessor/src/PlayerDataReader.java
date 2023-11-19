import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayerDataReader {
    public static List<PlayerData> readPlayerData(String fileName) throws IOException {
        List<PlayerData> playerDataList = new ArrayList<>();
        try (BufferedReader pdf = new BufferedReader(new FileReader(fileName))) {
            String pdfLine = pdf.readLine();
            while (pdfLine != null) {
                String[] spot = pdfLine.split(",");
                String playerID = spot[0];
                String playerOperation = spot[1];
                String matchID = spot[2];
                int coinNumber = Integer.parseInt(spot[3]);
                String betSide = spot[2].isEmpty() ? "" : spot[4]; // if spot[2] in the line is empty, then spot[4] is also empty

                PlayerData playerData = new PlayerData(playerID, playerOperation, matchID, coinNumber, betSide);
                playerDataList.add(playerData);
                pdfLine = pdf.readLine();
            }
        }
        return playerDataList;
    }
}
