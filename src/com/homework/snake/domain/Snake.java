package com.homework.snake.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.homework.snake.exceptions.SnakeAteItselfException;

public class Snake {
    private Point direction;
    private Deque<Point> parts = new LinkedList<>();
    private boolean hasEaten;

    public Snake(int initLength, Point head, Point direction) {
        this.direction = direction;
        parts.add(head);
        for (int i = 1; i < initLength; i++) {
            parts.add(new Point(head.x - direction.x * i, head.y - direction.y * i));
        }
    }

    public Point getHead() {
        return parts.peekFirst();
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }
    
    public Point getDirection() {
        return direction;
    }

    public void move() {
        addDirectionPoint();
        if (hasEaten) {
            hasEaten = false;
        } else {
            parts.removeLast();
        }
    }

    public void eat() {
        hasEaten = true;
    }

    private void addDirectionPoint() {
        Point head = parts.peekFirst();
        Point newHead = new Point(head.x + direction.x, head.y + direction.y);
        if (parts.contains(newHead)) {
            throw new SnakeAteItselfException();
        }
        parts.addFirst(new Point(head.x + direction.x, head.y + direction.y));
    }
    
    public List<Point> getParts() {
        return new ArrayList<>(parts);
    }
}
