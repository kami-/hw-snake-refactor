package com.homework.snake;

import java.awt.Point;

import com.homework.snake.domain.Snake;
import com.homework.snake.exceptions.SnakeAteItselfException;

public class SnakeGame {
    private static final int BOARD_WIDTH = 48;
    private static final int BOARD_HEIGHT = 28;

    private int boardWidth = BOARD_WIDTH;
    private int boardHeight = BOARD_HEIGHT;

    private Snake snake;
    private Point food;

    public SnakeGame(int boardWidth, int boardHeight) {
        super();
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
    
    public void move() {
        try {
            snake.move();
            if (hasLeftBorad()) {
                endGame();
            } else {
                if (hasFoundFood()) {
                    snake.eat();
                    updatePoints();
                    addFood();
                }
                drawSnake();
            }
            canChangeDirection = true;
        } catch (SnakeAteItselfException e) {
            endGame();
        }
    }

    private boolean hasFoundFood() {
        return foodPoint.equals(snake.getHead());
    }

    private void updatePoints() {
        pontok = pontok + 5;
        pontkiiras.setText("Pontszám: " + pontok);
    }

    private void endGame() {
        fut = false;
        gameover = true;
        toplistabatesz();
    }

    private boolean hasLeftBorad() {
        return snake.getHead().x >= BOARD_WIDTH || snake.getHead().x < 0 || snake.getHead().y >= BOARD_HEIGHT || snake.getHead().y < 0;
    }


}
