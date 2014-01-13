package com.homework.snake.domain;

import java.io.Serializable;

public class TopListEntry implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String playerName;
    private int score;

    public TopListEntry(String playerName, int score) {
        this.score = score;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}