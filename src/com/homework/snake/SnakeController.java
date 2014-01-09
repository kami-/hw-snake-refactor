package com.homework.snake;

import com.homework.snake.exceptions.SnakeAteItselfException;

public class SnakeController {
    private SnakeGame game;
    
    public void update() {
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

        jatekter.repaint();
        frame.setVisible(true);
    }

}
