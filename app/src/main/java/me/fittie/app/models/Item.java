package me.fittie.app.models;

/**
 * Created by Luke on 28/04/2017.
 */

public class Item {
    private int id;
    private String name;
    private String description;
    private int order;

    public Item(int id, String name, String description, int order) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }
}
