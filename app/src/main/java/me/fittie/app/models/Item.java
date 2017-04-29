package me.fittie.app.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Luke on 28/04/2017.
 */

public class Item {
    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField(canBeNull = true)
    private String description;

    private int day;

    private int order;

    public Item() { }

    public Item(int id, String name, String description, int day, int order) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.day = day;
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

    public int getDay() {
        return day;
    }

    public int getOrder() {
        return order;
    }
}
