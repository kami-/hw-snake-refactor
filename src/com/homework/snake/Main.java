package com.homework.snake;

import com.homework.snake.domain.SnakeController;


public class Main {

    public static void main(String[] args) {
        new Thread(new SnakeController()).run();
    }

}
