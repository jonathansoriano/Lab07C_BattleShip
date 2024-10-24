import javax.swing.*;

public class GameStats {
    private JTextField hitsField, missesField, totalMissesField, strikesField;
    private int hits = 0, misses = 0, totalMisses = 0, strikes = 0;

    public JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel();
        hitsField = createStatField("Hits: " + hits);
        missesField = createStatField("Misses: " + misses);
        totalMissesField = createStatField("Total Misses: " + totalMisses);
        strikesField = createStatField("Strikes: " + strikes);

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
