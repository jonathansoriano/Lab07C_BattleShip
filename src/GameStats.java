import javax.swing.*;

public class GameStats {
    private JTextField hitsField, missesField, totalMissesField, strikesField;
    private int hits, misses, totalMisses, strikes;

    public GameStats() {
        hits = 0;
        misses = 0;
        totalMisses = 0;
        strikes = 0;
    }


    public JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel();//This panel just has the default FlowLayout Manager so we need to add components
                                        // to the panel in the order we want them in.
        //JTextFields for the Game Counters
        hitsField = createStatField("Hits: " + hits);//Instead of using a Label, we just use the TF's text to show
                                                        //title of the counter and the number for each counter.
        missesField = createStatField("Misses: " + misses);
        totalMissesField = createStatField("Total Misses: " + totalMisses);
        strikesField = createStatField("Strikes: " + strikes);
        //Adding TF components to the StatsPanel in the order we want from left to right.
        statsPanel.add(hitsField);
        statsPanel.add(missesField);
        statsPanel.add(totalMissesField);
        statsPanel.add(strikesField);

        return statsPanel;
    }

    private JTextField createStatField(String text) {
        JTextField field = new JTextField(text);
        field.setEditable(false);
        field.setColumns(10);
        return field;
    }

    public void updateHits() {
        hits++;
        misses = 0; // Reset consecutive misses
        updateStats();
    }

    public void updateMisses() {
        misses++;
        totalMisses++;

        if (misses == 5) {
            strikes++;
            misses = 0;
            JOptionPane.showMessageDialog(null, "Strike! You have missed 5 times in a row.");
        }

        updateStats();
    }

    private void updateStats() {
        hitsField.setText("Hits: " + hits);
        missesField.setText("Misses: " + misses);
        totalMissesField.setText("Total Misses: " + totalMisses);
        strikesField.setText("Strikes: " + strikes);
    }

    public void resetStats() {
        hits = 0;
        misses = 0;
        totalMisses = 0;
        strikes = 0;
        updateStats();
    }

    public int getStrikes() {
        return strikes;
    }
}
