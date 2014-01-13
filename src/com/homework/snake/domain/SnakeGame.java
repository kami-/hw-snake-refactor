package com.homework.snake.domain;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.homework.snake.domain.Snake;
import com.homework.snake.exceptions.SnakeAteItselfException;

public class SnakeGame {
    private static final int FOOD_SCORE = 5;
    private int width;
    private int height;
    private int score;
    private Snake snake;
    private Point food;
    private boolean gameOver;
    
    public boolean isGameOver() {
        return gameOver;
    }

    private Random random = new Random();

    public SnakeGame(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }
    
    public int getScore() {
        return this.score;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void init(int snakeLength, Point snakeHead, Point snakeDirection) {
        score = 0;
        snake = new Snake(snakeLength, snakeHead, snakeDirection);
        food = createFood();
        gameOver = false;
    }
    
    public List<Point> getGameEntityPositions() {
        List<Point> points = Collections.<Point>emptyList();
        if (snake != null && food != null) {
            points = snake.getParts();
            points.add(food);
        }
        return points;
    }

    public void move() {
        try {
            doMove();
        } catch (SnakeAteItselfException e) {
            gameOver = true;
        }
    }

    private Point createFood() {
        return new Point(random.nextInt(width), random.nextInt(height));
    }


    private void doMove() {
        snake.move();
        checkBoundry();
        checkFood();
    }  

    private void checkBoundry() {
        if (hasLeftBorad()) {
            gameOver = true;
        }
    }
    
    private boolean hasLeftBorad() {
        return !gameOver
 && (snake.getHead().x >= width || snake.getHead().x < 0 || snake.getHead().y >= height || snake.getHead().y < 0);
    }
    
    private void checkFood() {
        if (hasFoundFood()) {
            snake.eat();
            score += FOOD_SCORE;
            food = createFood();
        }
    }
    
    private boolean hasFoundFood() {
        return !gameOver && food.equals(snake.getHead());
    }

    public boolean isOppositeDirection(int x, int y) {
        return snake.getDirection().x == -x && snake.getDirection().y == -y;
    }

    public void setSnakeDirection(int x, int y) {
        snake.setDirection(new Point(x, y));
    }
}
