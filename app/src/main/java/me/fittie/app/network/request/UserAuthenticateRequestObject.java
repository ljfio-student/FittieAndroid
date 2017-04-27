package me.fittie.app.network.request;

/**
 * Created by Luke on 27/04/2017.
 */

public class UserAuthenticateRequestObject {
    public String email;
    public String password;

    public UserAuthenticateRequestObject(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
