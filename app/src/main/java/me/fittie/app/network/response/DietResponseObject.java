package me.fittie.app.network.response;

import java.util.ArrayList;

/**
 * Created by luke on 27/04/2017.
 */

public class DietResponseObject {
    public String name;
    public int owner;

    public ArrayList<MealDietResponse> meals;

    public class MealDietResponse {
        public int id;
        public int day;
        public int order;
        public boolean completed;
    }

    public String toString() {
        return String.format("Diet '%s' has %d meals", name, meals.size());
    }
}
