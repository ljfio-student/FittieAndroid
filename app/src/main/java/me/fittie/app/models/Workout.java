package me.fittie.app.models;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by luke on 27/04/2017.
 */

@DatabaseTable(tableName = "workout")
public class Workout extends Item {
    public Workout() { }

    public Workout(int id, String name, String description, int day, int order) {
        super(id, name, description, day, order);
    }
}
