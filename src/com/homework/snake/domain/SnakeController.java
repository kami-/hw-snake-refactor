package com.homework.snake.domain;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.homework.snake.domain.SnakeGame;
import com.homework.snake.view.SnakeWindow;


public class SnakeController implements Runnable {
    private static final int BOARD_WIDTH = 48;
    private static final int BOARD_HEIGHT = 28;

    private static final int SNAKE_LENGTH = 3;
    private static final Point SNAKE_HEAD = new Point(24, 14);
    private static final Point SNAKE_DIRECTION = new Point(1, 0);

    private SnakeGame game;
    private SnakeWindow view;
    private boolean canChangeDirection = true;
    private int updateDelay = 70;
    private boolean closed;
    private boolean paused;

    public SnakeController() {
        super();
        this.game = new SnakeGame(BOARD_WIDTH, BOARD_HEIGHT);
        this.view = new SnakeWindow(game, this);
        registerViewListeners();
    }

    private void registerViewListeners() {
        view.addListenerToNewGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });
        view.addListenerToSetHardMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpdateDelay(50);
            }
        });
        view.addListenerToSetNormalMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpdateDelay(70);
            }
        });
        view.addListenerToSetEasyMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpdateDelay(90);
            }
        });
    }

    private void setUpdateDelay(int updateDelay) {
        this.updateDelay = updateDelay;
    }

    @Override
    public void run() {
        while (!closed) {
            if (!paused) {
                runGame();
            }
        }
    }

    private void runGame() {
        init();
        while (!game.isGameOver() || closed) {
            update();
            draw();
            sleep();
        }
        endGame();
    }

    private void endGame() {
        paused = true;
        view.showScoreBoard();
    }

    private void sleep() {
        try {
            Thread.sleep(updateDelay);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void init() {
        game.init(SNAKE_LENGTH, SNAKE_HEAD, SNAKE_DIRECTION);
        view.init();
        canChangeDirection = true;
        paused = false;
    }

    private void update() {
        game.move();
        view.setScore(game.getScore());
        canChangeDirection = true;
    }

    private void draw() {
        view.drawGameBoard();
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == 113) {
            init();
            return;
        }
        if (canChangeDirection) {
            int x = 0;
            int y = 0;
            if (keyCode == 37) {
                x = -1;
            } else if (keyCode == 38) {
                y = -1;
            } else if (keyCode == 39) {
                x = 1;
            } else if (keyCode == 40) {
                y = 1;
            }
            if (!game.isOppositeDirection(x, y)) {
                game.setSnakeDirection(x, y);
            }
            canChangeDirection = false;
        }
    }
}
