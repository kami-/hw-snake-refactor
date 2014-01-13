package com.homework.snake.domain;

import java.util.Comparator;

public class TopListEntryComparator implements Comparator<TopListEntry> {

    @Override
    public int compare(TopListEntry entry1, TopListEntry entry2) {
        return entry2.getScore() - entry1.getScore();
    }

}
