package com.homework.snake.domain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopListRepository {

    private static final int TOPLIST_SIZE = 10;
    private ArrayList<TopListEntry> topList = new ArrayList<>();
    
    public List<TopListEntry> getTopList() {
        return new ArrayList<>(topList);
    }

    public TopListRepository() {
        readFile();
    }

    private void readFile() {
        File file = new File("topList.ser");
        if (file.exists()) {
            try (InputStream inStrem = new FileInputStream(file);
                    InputStream buffer = new BufferedInputStream(inStrem);
                    ObjectInput in = new ObjectInputStream(buffer)) {
                topList = (ArrayList<TopListEntry>) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile() {
        try(OutputStream file = new FileOutputStream("topList.ser");
                 OutputStream buffer = new BufferedOutputStream(file);
                 ObjectOutput out = new ObjectOutputStream(buffer)) {
            out.writeObject(topList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addNewEntry(TopListEntry entry) {
        if (topList.size() == 10) {
            topList.remove(topList.size() - 1);
        }
        topList.add(entry);
        Collections.sort(topList, new TopListEntryComparator());
    }
    
    public boolean canAddScore(int score) {
        return topList.size() < TOPLIST_SIZE || score > topList.get(topList.size() - 1).getScore();
    }
}
