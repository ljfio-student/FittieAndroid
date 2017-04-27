package me.fittie.app.network.response;

/**
 * Created by Luke on 27/04/2017.
 */

public class UserResponseObject {
    public String name;
    public String image;
    public int age;

    public int type;
    public int level;
    public int goal;

    public int points;

    public UserPreferenceResponseObject prefer;

    public class UserPreferenceResponseObject {
        public int diet;
        public int routine;
    }
}
