package com.homework.snake;

import java.awt.Point;

import com.homework.snake.domain.Snake;

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



}
