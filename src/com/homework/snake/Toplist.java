package com.homework.snake;

import java.io.Serializable;

class Toplist implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String nev;
    private int pont;

    /*
    * A konstruktor létrehoz egy toplista bejegyzést a paraméterként megadott
    * névvel és pontszámmal
    */
    public Toplist(String nev, int pont) {
        this.pont = pont;
        this.nev = nev;
    }

    /*
    * Visszatér az adott bejegyzés nevével
    */
    public String getnev() {
        return nev;
    }

    /*
    * Visszatér az adott bejegyzés pontszámával (int)
    */
    public int getpont() {
        return pont;
    }

    /*
    * Visszatér az adott bejegyzés pontszámával (String)
    */
    public String getstrpont() {
        return Integer.toString(pont);
    }
}