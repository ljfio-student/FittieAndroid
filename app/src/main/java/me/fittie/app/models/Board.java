package me.fittie.app.models;

/**
 * Created by Luke on 27/04/2017.
 */

public class Board {
    private int id;
    private String name;

    public Board(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
