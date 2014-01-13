package com.homework.snake.view;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBar extends JPanel {
    private JLabel scoreBarLabel;

    public ScoreBar(Point position, int width, int height) {
        setBounds(position.x, position.y, width, height);
        setBackground(Color.GRAY);
        scoreBarLabel = new JLabel();
        scoreBarLabel.setForeground(Color.BLACK);
        scoreBarLabel.setText("Pontszám: 0");
        add(scoreBarLabel);
    }

    public void setScore(int score) {
        scoreBarLabel.setText("Pontszám: " + score);
    }
}
