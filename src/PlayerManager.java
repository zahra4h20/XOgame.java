public class PlayerManager {
    private HumanPlayer[] players;
    private int currentPlayerIndex;

    public PlayerManager() {
        players = new HumanPlayer[] {
                new HumanPlayer("X"),
                new HumanPlayer("O")
        };
        currentPlayerIndex = 0;
    }

    public HumanPlayer getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public void switchPlayer() {
        currentPlayerIndex = 1 - currentPlayerIndex;
    }

    public void reset() {
        for (HumanPlayer player : players) {
            player.reset();
        }
        currentPlayerIndex = 0;
    }
}
