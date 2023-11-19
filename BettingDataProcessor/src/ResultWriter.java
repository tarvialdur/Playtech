import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class ResultWriter {
    public static void writeResultToFile(List<LegalPlayer> legalPlayers, List<PlayerData> illegalPlayers, int casinoBalance, String fileName) throws IOException {
        legalPlayers.sort(Comparator.comparing(LegalPlayer::getLegalPlayerID));
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {
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
            printWriter.print(casinoBalance);
        }
    }
}