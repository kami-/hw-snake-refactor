package com.homework.snake.view;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.homework.snake.domain.SnakeGame;

public class GameBoard extends JPanel {
    private static final int BLOCK_SIZE = 10;

    private SnakeGame game;
    private List<JButton> gameEntityPoints = new ArrayList<>();

    public GameBoard(SnakeGame game, Point position, int width, int height) {
        this.game = game;
        setLayout(null);
        setBounds(position.x, position.y, width, height);
        setBackground(Color.LIGHT_GRAY);
    }

    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    public void drawPoints() {
        List<Point> enityPoints = game.getGameEntityPositions();
        extendEntityPoints(enityPoints.size());
        drawEntities(enityPoints);
    }

    private void extendEntityPoints(int size) {
        while (gameEntityPoints.size() < size) {
            JButton button = new JButton();
            gameEntityPoints.add(button);
            add(button);
        }
    }

    private void drawEntities(List<Point> enityPoints) {
        for (int i = 0; i < enityPoints.size(); i++) {
            updateButton(gameEntityPoints.get(i), enityPoints.get(i), Color.BLACK);
        }
    }

    private void updateButton(JButton button, Point point, Color color) {
        button.setEnabled(false);
        button.setVisible(true);
        button.setBounds(point.x * BLOCK_SIZE, point.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        button.setBackground(color);
    }
    
    private void clearEntities() {
        for (JButton snakeButton : gameEntityPoints) {
            snakeButton.setVisible(false);
        }
        gameEntityPoints.clear();
    }
    
    public void init() {
        clearEntities();
    }
}
