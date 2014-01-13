package com.homework.snake.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.homework.snake.domain.TopListEntry;
import com.homework.snake.domain.TopListRepository;

public class TopList extends JPanel {

    private final class PlayerNameEntered implements ActionListener {
        private final int score;
        private final JTextField playerName;

        private PlayerNameEntered(int score, JTextField playerName) {
            this.score = score;
            this.playerName = playerName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            topListRepo.addNewEntry(new TopListEntry(playerName.getText(), score));
            topListRepo.writeFile();
            createScoreBoard();
            showTopListScrollPane();
            mainWindow.setVisible(true);
            mainWindow.repaint();
        }
    }

    private TopListRepository topListRepo;
    private JScrollPane scoreBoard;
    private JFrame mainWindow;

    public TopList(JFrame mainWindow, Point position, int width, int height) {
        this.mainWindow = mainWindow;
        topListRepo = new TopListRepository();
        setBounds(position.x, position.y, width, height);
        setBackground(Color.LIGHT_GRAY);
        setVisible(false);
    }

    public JScrollPane getScoreBoard() {
        return this.scoreBoard;
    }

    public void createScoreBoard() {
        DefaultTableModel entryTable = new DefaultTableModel(getTopListScoreBoardHeaders(), 0);
        JTable table = new JTable(entryTable);
        scoreBoard = new JScrollPane(table);

        for (TopListEntry entry : topListRepo.getTopList()) {
            String[] row = {entry.getPlayerName(), Integer.toString(entry.getScore())};
            entryTable.addRow(row);
        }
    }

    private Vector<String> getTopListScoreBoardHeaders() {
        Vector<String> columnHeaders = new Vector<>();
        columnHeaders.add("Név");
        columnHeaders.add("Pont");
        return columnHeaders;
    }

    public void addScore(final int score) {
        if (topListRepo.canAddScore(score)) {
            addLabelsToToplist("A játéknak vége!", "Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):");
            final JTextField playerName = new JTextField(10);
            add(playerName);
            playerName.addActionListener(new PlayerNameEntered(score, playerName));
        } else {
            addLabelsToToplist("A játéknak vége!", "Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2).");
            createScoreBoard();
        }
    }

    private void showTopListScrollPane() {
        removeAll();
        add(scoreBoard);
    }

    private void addLabelsToToplist(String title, String description) {
        JLabel titleLabel = new JLabel(title);
        JLabel descriptionLabel = new JLabel(description);
        titleLabel.setForeground(Color.BLACK);
        descriptionLabel.setForeground(Color.BLACK);
        removeAll();
        add(titleLabel);
        add(descriptionLabel);
    }
}
