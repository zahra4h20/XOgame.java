import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameGUI extends JFrame implements Resettable, Displayable {
    private Board board;
    private PlayerManager playerManager;
    private JButton[][] buttons;
    private int clickCount = 0;
    private int fromRow, fromCol;
    private ArrayList<String> results = new ArrayList<>();
    private int gameCount = 0;

    public GameGUI() {
        board = new Board();
        playerManager = new PlayerManager();
        initializeUI();
    }

    public void initializeUI() {
        setTitle("XO Game");
        setSize(400, 400);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);

        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleMove(row, col);
                    }
                });
                add(buttons[i][j]);
            }
        }

        setVisible(true);
    }

    public void handleMove(int row, int col) {
        HumanPlayer currentPlayer = playerManager.getCurrentPlayer();
        String symbol = currentPlayer.getSymbol();

        if (!currentPlayer.hasPlacedAll()) { // فاز گذاشتن مهره
            if (board.isCellEmpty(row, col)) {
                board.setCell(row, col, symbol);
                buttons[row][col].setText(symbol);

                if (symbol.equals("X")) {
                    buttons[row][col].setForeground(Color.RED);
                } else if (symbol.equals("O")) {
                    buttons[row][col].setForeground(Color.BLUE);
                }

                currentPlayer.incrementPlaced();
                if (board.checkWinner(symbol)) {
                    results.add(symbol);
                    displayMessage(symbol + " wins!");
                    resetGame();
                    return;
                }
                if (board.isFull()) {
                    results.add("Draw");
                    displayMessage("Draw!");
                    resetGame();
                    return;
                }
                playerManager.switchPlayer();
            }
        } else { // فاز حرکت دادن مهره
            clickCount++;
            if (clickCount == 1) { // انتخاب مبدأ
                if (board.getCell(row, col).equals(symbol)) {
                    fromRow = row;
                    fromCol = col;
                } else {
                    clickCount = 0;
                    displayMessage("Select your own piece.");
                }
            } else if (clickCount == 2) { // انتخاب مقصد
                if (board.isCellEmpty(row, col) && board.isAdjacent(fromRow, fromCol, row, col)) {
                    board.movePiece(fromRow, fromCol, row, col, symbol);
                    buttons[fromRow][fromCol].setText("");
                    buttons[row][col].setText(symbol);

                    if (symbol.equals("X")) {
                        buttons[row][col].setForeground(Color.RED);
                    } else if (symbol.equals("O")) {
                        buttons[row][col].setForeground(Color.BLUE);
                    }

                    if (board.checkWinner(symbol)) {
                        results.add(symbol);
                        displayMessage(symbol + " wins!");
                        resetGame();
                        return;
                    }
                    playerManager.switchPlayer();
                } else {
                    displayMessage("Invalid move!");
                }
                clickCount = 0;
            }
        }
    }

    public void resetGame() {
        board.reset();
        playerManager.reset();
        clickCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.BLACK);
            }
        }

        gameCount++;

        if (gameCount == 5) {
            showFinalResult();
        }
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showFinalResult() {
        int xWins = 0;
        int oWins = 0;
        int draws = 0;

        for (String result : results) {
            if (result.equals("X")) {
                xWins++;
            } else if (result.equals("O")) {
                oWins++;
            } else if (result.equals("Draw")) {
                draws++;
            }
        }

        String winner;
        if (xWins > oWins) {
            winner = "X is the overall winner!";
        } else if (oWins > xWins) {
            winner = "O is the overall winner!";
        } else {
            winner = "It's a tie!";
        }

        JOptionPane.showMessageDialog(this,
                "Game Over!\n\nX wins: " + xWins + "\nO wins: " + oWins + "\nDraws: " + draws + "\n\n" + winner);

        System.exit(0);
    }
}
