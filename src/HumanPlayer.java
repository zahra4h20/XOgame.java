public class HumanPlayer implements Playable {
    private String symbol;
    private int placedPieces;

    public HumanPlayer(String symbol) {
        this.symbol = symbol;
        this.placedPieces = 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public void incrementPlaced() {
        placedPieces++;
    }

    public boolean hasPlacedAll() {
        return placedPieces >= 3;
    }

    public void reset() {
        placedPieces = 0;
    }

    @Override
    public void makeMove(Board board) {
        // کاری نمیکنه اینجا چون تو GUI کنترل میشه
    }
}
