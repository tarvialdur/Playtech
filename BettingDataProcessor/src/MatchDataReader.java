import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public
class MatchDataReader {
    public static List<MatchData> readMatchData(String fileName) throws IOException {
        List<MatchData> matchDataList = new ArrayList<>();
        try (BufferedReader mdf = new BufferedReader(new FileReader(fileName))) {
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
        }
        return matchDataList;
    }
}
