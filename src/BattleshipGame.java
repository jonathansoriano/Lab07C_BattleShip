import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattleshipGame {
    private final GameBoard gameBoard;
    private final GameStats gameStats;

    public BattleshipGame() {
        //Creating a JFrame for the game.
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(false);//User can't adjust the size of the game.
        frame.setLocationRelativeTo(null);//Centers the game to computer screen
        frame.setLayout(new BorderLayout());

        gameStats = new GameStats();
        gameBoard = new GameBoard(gameStats,this);

        //Creating 3 Panels. 1. Game Board Panel 2. Stats Panel 3. Control Button Panel

        JPanel boardPanel = gameBoard.createBoardPanel();//Using the createBoardPanel() method from the GameBoard class.
        JPanel statsPanel = gameStats.createStatsPanel();//Using the createStatsPanel() method from the GameStats class
        JPanel controlPanel = createControlPanel();//Creating the Control button Panel within this class (BattleshipGame)

        //Adding all the Panels to the Frame using the BorderLayout manager.
        frame.add(statsPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);//Setting the frame to be seen for the user to play.
    }

    /**
     * This method creates the Control Button panel, along with its functionalities
     * @return- returns a JPanel to which we can assign a JPanel for the controlPanel (line 25).
     */
    private JPanel createControlPanel() {
        //Creating the Control Button Panel
        JPanel controlPanel = new JPanel();

        //Creating the Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setFocusable(false);//Gets rid of the annoying circle inside of button when selected
        quitButton.addActionListener((ActionEvent ae) -> quitGame());

        //Creating the Play Again Button
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFocusable(false);
        playAgainButton.addActionListener((ActionEvent ae) -> playAgain());

        //Adding the Quit and Play Again Button to the
        controlPanel.add(quitButton);
        controlPanel.add(playAgainButton);

        return controlPanel;
    }

    /**
     * This method just pops up a message dialog asking if you are sure you want to quit the game. If yes, then the game closes
     */
    private void quitGame() {
        //Message Dialog (JOptionPane) asking if you are sure you want to quit.
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {//If Yes, then---->
            System.exit(0);//Program terminates. Otherwise, the game will go back to the game.
        }
    }

    public void playAgain() {
        //Message Dialog asking if you want to play again.
        int response = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {// If Yes, then----->
            gameBoard.resetBoard();//We use GameBoard's method to reset the board and
            gameStats.resetStats();//GameStat's method to reset the stats
        } else {
            System.exit(0);// if No, then the program terminates.
        }
    }

}
