package com.homework.snake;

public class SnakeApp implements Runnable {
    private SnakeGame game;
    private SnakeWindow window;

    public SnakeApp(SnakeGame game, SnakeWindow window) {
        super();
        this.game = game;
        this.window = window;
    }

    @Override
    public void run() {

    }

}
