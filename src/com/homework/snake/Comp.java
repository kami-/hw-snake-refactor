package com.homework.snake;

import java.util.Comparator;

public class Comp implements Comparator<Toplist> {
    /*
     * A Comparator bels� f�ggv�nye, melynek seg�ts�g�vel sorba rendezi a
     * Toplist�t
     */
    public int compare(Toplist pont1, Toplist pont2) {
        int p1 = pont1.getpont();
        int p2 = pont2.getpont();

        if (p1 > p2) {
            return -1;
        } else if (p1 < p2) {
            return +1;
        } else {
            return 0;
        }
    }

}
