package com.homework.snake.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.homework.snake.exceptions.SnakeAteItselfException;

public class Snake {
    private Point heading;
    private Deque<Point> parts = new LinkedList<>();
    private boolean hasEaten;

    public Snake(int initLength, Point head, Point heading) {
        this.heading = heading;
        parts.add(head);
        for (int i = 1; i < initLength; i++) {
            parts.add(new Point(head.x - heading.x * i, head.y - heading.y * i));
        }
    }

    public Point getHead() {
        return parts.peekFirst();
    }

    public void setHeading(Point heading) {
        this.heading = heading;
    }
    
    public void move() {
        addHeadingPoint();
        if (hasEaten) {
            hasEaten = false;
        } else {
            parts.removeLast();
        }
    }

    public void eat() {
        hasEaten = true;
    }

    private void addHeadingPoint() {
        Point head = parts.peekFirst();
        Point newHead = new Point(head.x + heading.x, head.y + heading.y);
        if (parts.contains(newHead)) {
            throw new SnakeAteItselfException();
        }
        parts.addFirst(new Point(head.x + heading.x, head.y + heading.y));
    }
    
    public List<Point> getParts() {
        return new ArrayList<>(parts);
    }
}
