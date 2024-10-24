import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattleshipGame {
    private final GameBoard gameBoard;
    private final GameStats gameStats;

    public BattleshipGame() {
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        gameStats = new GameStats();
        gameBoard = new GameBoard(gameStats,this);


        JPanel boardPanel = gameBoard.createBoardPanel();
        JPanel statsPanel = gameStats.createStatsPanel();
        JPanel controlPanel = createControlPanel();

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statsPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent ae) -> quitGame());

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener((ActionEvent ae) -> playAgain());

        controlPanel.add(quitButton);
        controlPanel.add(playAgainButton);

        return controlPanel;
    }

    private void quitGame() {
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void playAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            gameBoard.resetBoard();
            gameStats.resetStats();
        } else {
            System.exit(0);
        }
    }

}
