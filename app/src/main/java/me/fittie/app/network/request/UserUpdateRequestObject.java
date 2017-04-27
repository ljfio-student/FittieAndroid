package me.fittie.app.network.request;

/**
 * Created by Luke on 27/04/2017.
 */

public class UserUpdateRequestObject extends UserBaseRequestObject {
    public int type;
    public int goal;
    public int level;

    public UserPreferenceRequestObject prefer;

    public class UserPreferenceRequestObject {
        public int diet;
        public int routine;
    }
}
