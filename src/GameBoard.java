import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private static final int BOARD_SIZE = 10;
    private static final char WATER = '~';
    private static final char SHIP = 'S';
    private static final char HIT = 'X';
    private static final char MISS = 'M';

    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
    private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private List<List<Point>> ships = new ArrayList<>();
    private int remainingShips;
    private GameStats gameStats;
    private BattleshipGame gameInstance;

    public GameBoard(GameStats gameStats, BattleshipGame gameInstance) {
        this.gameStats = gameStats;
        this.gameInstance = gameInstance;
        initializeBoard();
        placeShipsRandomly();
    }

    public JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = new JButton(String.valueOf(WATER));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        return boardPanel;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = WATER;
            }
        }
    }

    private void placeShipsRandomly() { // <-------------------------------------------------------
        Random random = new Random();
        int[] shipSizes = {5, 4, 3, 3, 2}; // Example ship sizes
        remainingShips = shipSizes.length;

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(row, col, size, horizontal)) {
                    placeShip(row, col, size, horizontal);
                    placed = true;
                }
            }
        }
    }

    // ... (other methods remain the same)
    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > BOARD_SIZE) return false;
            for (int i = col; i < col + size; i++) {
                if (board[row][i] == SHIP) return false;
            }
        } else {
            if (row + size > BOARD_SIZE) return false;
            for (int i = row; i < row + size; i++) {
                if (board[i][col] == SHIP) return false;
            }
        }
        return true;
    }

    private void placeShip(int row, int col, int size, boolean horizontal) {
        List<Point> positions = new ArrayList<>();

        if (horizontal) {
            for (int i = col; i < col + size; i++) {
                board[row][i] = SHIP;
                positions.add(new Point(row, i));
            }
        } else {
            for (int i = row; i < row + size; i++) {
                board[i][col] = SHIP;
                positions.add(new Point(i, col));
            }
        }

        ships.add(positions);
    }


    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == SHIP) {
                buttons[row][col].setText(String.valueOf(HIT));
                buttons[row][col].setBackground(Color.RED);  // Set background to red for hits
                buttons[row][col].setOpaque(true);//Needed to show the color.
                buttons[row][col].setBorderPainted(false);//Don't know what this does.
                board[row][col] = HIT;
                gameStats.updateHits();

                if (isShipSunk(row, col)) {
                    JOptionPane.showMessageDialog(null, "You have sunk a ship!");
                    remainingShips--;

                    if (remainingShips == 0) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You have sunk all the ships!");
                        gameInstance.playAgain();
                        return;
                    }
                }

            } else if (board[row][col] == WATER) {
                buttons[row][col].setText(String.valueOf(MISS));
                buttons[row][col].setBackground(Color.YELLOW);  // Set background to yellow for misses
                buttons[row][col].setOpaque(true);
                buttons[row][col].setBorderPainted(false);
                board[row][col] = MISS;
                gameStats.updateMisses();

                if (gameStats.getStrikes() == 3) {
                    JOptionPane.showMessageDialog(null, "You have lost!");
                    gameInstance.playAgain();
                    return;
                }
            }

            buttons[row][col].setEnabled(false);
        }
    }
    private boolean isShipSunk(int hitRow,int hitCol){
        Point hitPoint=new Point(hitRow,hitCol);

        for(List<Point> shipPositions:ships){
            if(shipPositions.contains(hitPoint)){
                shipPositions.remove(hitPoint);

                if(shipPositions.isEmpty()){
                    ships.remove(shipPositions);
                    return true;
                }

                break;
            }
        }

        return false;
    }

    public void resetBoard() {
        initializeBoard();
        placeShipsRandomly();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setText(String.valueOf(WATER));
                buttons[i][j].setEnabled(true);

                buttons[i][j].setBackground(null);  // Reset background to cyan (water)
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(true);

            }
        }
    }
}
