import java.util.List;

public class ProcessData {
    public static MatchData findMatchData(List<MatchData> matchDataList, String matchID) {
        return matchDataList.stream()
                .filter(e -> e.getMatchID().equals(matchID))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Match ID not found"));
    }
}