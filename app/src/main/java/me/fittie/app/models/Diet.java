package me.fittie.app.models;

/**
 * Created by luke on 27/04/2017.
 */

public class Diet {
    private int id;
    private String name;

    public Diet(int id, String name) {
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
