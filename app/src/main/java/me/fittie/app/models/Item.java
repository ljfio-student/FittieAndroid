package me.fittie.app.models;

/**
 * Created by Luke on 28/04/2017.
 */

public class Item {
    private int id;
    private String name;
    private int order;

    public Item(int id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

}
