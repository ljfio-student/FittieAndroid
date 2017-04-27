package me.fittie.app.network.response;

import java.util.ArrayList;

/**
 * Created by luke on 27/04/2017.
 */

public class RoutineResponseObject {
    public String name;
    public int owner;

    public ArrayList<WorkoutRoutineResponseObject> workouts;

    public class WorkoutRoutineResponseObject {
        public int id;
        public int day;
        public int order;
        public boolean completed;
    }

    public String toString() {
        return String.format("Routine '%s' has %d workout", name, workouts.size());
    }
}
