public class Board {
    private String[][] grid;

    public Board() {
        grid = new String[3][3];
        reset();
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col].isEmpty();
    }

    public void setCell(int row, int col, String symbol) {
        grid[row][col] = symbol;
    }

    public String getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j].isEmpty())
                    return false;
        return true;
    }

    public boolean checkWinner(String symbol) {
        for (int i = 0; i < 3; i++)
            if (grid[i][0].equals(symbol) && grid[i][1].equals(symbol) && grid[i][2].equals(symbol))
                return true;

        for (int j = 0; j < 3; j++)
            if (grid[0][j].equals(symbol) && grid[1][j].equals(symbol) && grid[2][j].equals(symbol))
                return true;

        if (grid[0][0].equals(symbol) && grid[1][1].equals(symbol) && grid[2][2].equals(symbol))
            return true;

        if (grid[0][2].equals(symbol) && grid[1][1].equals(symbol) && grid[2][0].equals(symbol))
            return true;

        return false;
    }

    public void reset() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = "";
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol, String symbol) {
        grid[fromRow][fromCol] = "";
        grid[toRow][toCol] = symbol;
    }

    public boolean isAdjacent(int fromRow, int fromCol, int toRow, int toCol) {
        int dr = Math.abs(fromRow - toRow);
        int dc = Math.abs(fromCol - toCol);
        return (dr <= 1 && dc <= 1) && !(dr == 0 && dc == 0);
    }
}
