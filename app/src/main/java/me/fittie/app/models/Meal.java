package me.fittie.app.models;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by luke on 27/04/2017.
 */

@DatabaseTable(tableName = "meal")
public class Meal extends Item {
    public Meal() { }

    public Meal(int id, String name, String description, int day, int order) {
        super(id, name, description, day, order);
    }
}
