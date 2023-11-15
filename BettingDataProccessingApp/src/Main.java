import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Player> playerDataList = new ArrayList<>();
        Set<String> playerIds = new TreeSet<>();

        BufferedReader buffRead = new BufferedReader(new FileReader("player_data.txt"));
        String playerLine = buffRead.readLine();
        while(playerLine != null){
            String[] lines = playerLine.split(",");
            String playerID = lines[0];
            String action = lines[1];
            String matchID = lines[2];
            double amount = Double.parseDouble(lines[3]);
            String side = lines[2].isEmpty() ? "" : lines[4];

            Player player = new Player(playerID, action, matchID, amount, side);
            playerDataList.add(player);
            playerIds.add(playerID);
            playerLine = buffRead.readLine();
        }
        buffRead.close();



    }
}





